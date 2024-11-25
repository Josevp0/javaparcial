package com.example.javaparcial.Controller;

import com.example.javaparcial.Entity.AgendarCita;
import com.example.javaparcial.Entity.Tatuaje;
import com.example.javaparcial.Entity.Tatuador;
import com.example.javaparcial.Repository.AgendarCitaRepository;
import com.example.javaparcial.Repository.TatuajeRepository;
import com.example.javaparcial.Repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/citas")
public class AgendarCitaController {

    @Autowired
    private AgendarCitaRepository agendarCitaRepository;

    @Autowired
    private TatuajeRepository tatuajeRepository;

    @Autowired
    private TatuadorRepository tatuadorRepository;

    @GetMapping("/lista")
    public String showCitaList(Model model) {
        List<AgendarCita> citas = agendarCitaRepository.findAll();
        model.addAttribute("citas", citas);
        return "citalista"; // Nombre del archivo HTML
    }

    // Mostrar formulario para crear una cita
    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        List<Tatuaje> tatuajes = tatuajeRepository.findAll();
        List<Tatuador> tatuadores = tatuadorRepository.findAll();
        model.addAttribute("tatuajes", tatuajes);
        model.addAttribute("tatuadores", tatuadores);
        return "formcitas"; // Nombre del archivo HTML
    }

    @PostMapping
    public ResponseEntity<String> createCita(
            @RequestParam String descripcion,
            @RequestParam String fechaCita, // Recibir fecha como String
            @RequestParam(required = false) Long tatuajeId,
            @RequestParam(required = false) Long tatuadorId) {

        try {
            AgendarCita cita = new AgendarCita();
            cita.setDescripcion(descripcion.isEmpty() ? "Sin descripción" : descripcion);

            // Convertir String a java.util.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); // Formato para datetime-local
            Date fechaCitaConvertida = dateFormat.parse(fechaCita);
            cita.setFechaCita(fechaCitaConvertida);

            // Validar y asignar tatuaje si existe
            if (tatuajeId != null) {
                tatuajeRepository.findById(tatuajeId).ifPresentOrElse(
                        cita::setTatuaje,
                        () -> {
                            throw new IllegalArgumentException("Tatuaje no encontrado");
                        }
                );
            }

            // Validar y asignar tatuador si existe
            if (tatuadorId != null) {
                tatuadorRepository.findById(tatuadorId).ifPresentOrElse(
                        cita::setTatuador,
                        () -> {
                            throw new IllegalArgumentException("Tatuador no encontrado");
                        }
                );
            }

            agendarCitaRepository.save(cita);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cita creada exitosamente");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha inválido");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la cita");
        }
    }
}