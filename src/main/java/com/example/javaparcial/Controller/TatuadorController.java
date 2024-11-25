package com.example.javaparcial.Controller;

import com.example.javaparcial.Entity.Cliente;
import com.example.javaparcial.Entity.Tatuador;
import com.example.javaparcial.Repository.TatuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tatuadores")
public class TatuadorController {

    @Autowired
    private TatuadorRepository tatuadorRepository;

    // Página principal de tatuadores
    @GetMapping("/vertatuadores")
    public String showTatuadoresView() {
        return "vertatuador"; // Archivo HTML para listar tatuadores
    }
    // Mostrar formulario de login
    @GetMapping("/loginta")
    public String showLoginForm() {
        return "logintatuador"; // Nombre del archivo HTML para el formulario de login
    }

    @GetMapping("/vertatuajes")
    public String showtatuajeView() {
        return "vertatuajes"; // Nombre del archivo HTML sin la extensión
    }

    @PostMapping("/loginta")
    public String loginTatuador(@RequestParam String email, @RequestParam String password) {
        // Buscar el tatuador por email
        Optional<Tatuador> tatuador = tatuadorRepository.findByEmail(email);

        // Verificar si el tatuador existe y la contraseña es correcta
        if (tatuador.isPresent() && tatuador.get().getContraseña().equals(password)) {
            // Redirige a la página principal de los tatuadores si las credenciales son correctas
            return "redirect:/tatuadores/vertatuajes";
        }

        // Si las credenciales no son válidas, regresa al formulario con un mensaje de error
        return "redirect:/tatuadores/loginta?error=true";
    }

    // Mostrar todos los tatuadores
    @GetMapping("/ver")
    public String verTatuadores(Model model) {
        List<Tatuador> tatuadores = tatuadorRepository.findAll();
        model.addAttribute("tatuadores", tatuadores);
        return "vertatuador"; // Archivo HTML para listar tatuadores
    }

    // Mostrar formulario para crear un tatuador
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("tatuador", new Tatuador());
        return "formtatuador"; // Archivo HTML para el formulario
    }

    // Guardar un nuevo tatuador
    @PostMapping("/guardar")
    public String guardarTatuador(@ModelAttribute Tatuador tatuador) {
        tatuadorRepository.save(tatuador);
        return "redirect:/tatuadores/ver";
    }

    // Mostrar formulario para editar un tatuador existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Tatuador tatuador = tatuadorRepository.findById(id).orElse(null);
        model.addAttribute("tatuador", tatuador);
        return "formtatuador"; // Reutilizar el mismo formulario
    }

    // Eliminar un tatuador
    @GetMapping("/eliminar/{id}")
    public String eliminarTatuador(@PathVariable Long id) {
        tatuadorRepository.deleteById(id);
        return "redirect:/tatuadores/ver";
    }
}


