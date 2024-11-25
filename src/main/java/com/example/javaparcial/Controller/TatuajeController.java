package com.example.javaparcial.Controller;

import com.example.javaparcial.Entity.Tatuaje;
import com.example.javaparcial.Entity.Tatuador;
import com.example.javaparcial.Repository.TatuajeRepository;
import com.example.javaparcial.Repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tatuajes")
public class TatuajeController {

    @Autowired
    private TatuajeRepository tatuajeRepository;

    @Autowired
    private TatuadorRepository tatuadorRepository;

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        List<Tatuador> tatuadores = tatuadorRepository.findAll(); // Obtén todos los tatuadores
        model.addAttribute("tatuadores", tatuadores); // Pásalos al modelo
        return "formtatuajes"; // Nombre del archivo HTML
    }

    @GetMapping
    @ResponseBody // Para devolver directamente JSON
    public ResponseEntity<List<Map<String, Object>>> getAllTatuajes() {
        List<Map<String, Object>> tatuajes = tatuajeRepository.findAll().stream().map(tatuaje -> {
            Map<String, Object> tatuajeMap = new HashMap<>();
            tatuajeMap.put("id", tatuaje.getId());
            tatuajeMap.put("nombre", tatuaje.getNombre() != null ? tatuaje.getNombre() : "Sin nombre");
            tatuajeMap.put("descripcion", tatuaje.getDescripcion() != null ? tatuaje.getDescripcion() : "Sin descripción");
            tatuajeMap.put("imagen", tatuaje.getImagen() != null ? Base64.getEncoder().encodeToString(tatuaje.getImagen()) : null);
            tatuajeMap.put("precio", tatuaje.getPrecio());
            tatuajeMap.put("tatuador", tatuaje.getTatuador() != null ? tatuaje.getTatuador().getNombre() : "Sin tatuador");
            return tatuajeMap;
        }).collect(Collectors.toList());

        if (tatuajes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.ok(tatuajes);
    }


    // Obtener un tatuaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTatuajeById(@PathVariable Long id) {
        Optional<Tatuaje> tatuaje = tatuajeRepository.findById(id);
        if (tatuaje.isPresent()) {
            Tatuaje t = tatuaje.get();
            Map<String, Object> tatuajeMap = new HashMap<>();
            tatuajeMap.put("id", t.getId());
            tatuajeMap.put("nombre", t.getNombre());
            tatuajeMap.put("descripcion", t.getDescripcion());
            tatuajeMap.put("imagen", t.getImagen() != null ? Base64.getEncoder().encodeToString(t.getImagen()) : null);
            tatuajeMap.put("precio", t.getPrecio());
            tatuajeMap.put("tatuador", t.getTatuador() != null ? t.getTatuador().getNombre() : "Sin tatuador");
            return ResponseEntity.ok(tatuajeMap);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Tatuaje no encontrado"));
    }

    // Crear un nuevo tatuaje
    @PostMapping
    public ResponseEntity<String> createTatuaje(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam MultipartFile imagen,
            @RequestParam int precio,
            @RequestParam(required = false) Long tatuadorId) {
        try {
            Tatuaje tatuaje = new Tatuaje();
            tatuaje.setNombre(nombre.isEmpty() ? "Sin nombre" : nombre);
            tatuaje.setDescripcion(descripcion.isEmpty() ? "Sin descripción" : descripcion);

            if (!imagen.isEmpty()) {
                tatuaje.setImagen(imagen.getBytes());
            }

            tatuaje.setPrecio(precio >= 0 ? precio : 0);

            if (tatuadorId != null) {
                Optional<Tatuador> tatuador = tatuadorRepository.findById(tatuadorId);
                if (tatuador.isPresent()) {
                    tatuaje.setTatuador(tatuador.get());
                } else {
                    return ResponseEntity.badRequest().body("Tatuador no encontrado");
                }
            }

            tatuajeRepository.save(tatuaje);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tatuaje creado exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el tatuaje");
        }
    }

    // Eliminar un tatuaje
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTatuaje(@PathVariable Long id) {
        if (tatuajeRepository.existsById(id)) {
            tatuajeRepository.deleteById(id);
            return ResponseEntity.ok("Tatuaje eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tatuaje no encontrado");
        }
    }
}

