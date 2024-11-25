package com.example.javaparcial.Controller;

import com.example.javaparcial.Entity.Administrador;
import com.example.javaparcial.Repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "loginadmin"; // Nombre del archivo HTML para el formulario de login
    }

    // Mostrar formulario de registro de administrador
    @GetMapping("/form")
    public String showAdminForm() {
        return "formadmin"; // Nombre del archivo HTML para el formulario de registro
    }

    @GetMapping("/admin-menu")
    public String showAdminMenu() {
        return "admin-Menu"; // Nombre del archivo HTML correspondiente
    }

    // Método para manejar el login
    @PostMapping("/login")
    public String loginAdministrador(@RequestParam String email, @RequestParam String password) {
        Optional<Administrador> admin = administradorRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getContraseña().equals(password)) {
            return "redirect:/admin/admin-menu"; // Redirige a la página principal si las credenciales son correctas
        }
        return "redirect:/admin/login?error=true"; // Vuelve al login con un mensaje de error
    }

    // Crear un nuevo Administrador
    @PostMapping
    public String createAdministrador(@ModelAttribute Administrador administrador) {
        administradorRepository.save(administrador);
        return "redirect:/admin/login"; // Redirige a la página de login tras guardar
    }

    // Obtener todos los Administradores
    @GetMapping
    public ResponseEntity<List<Administrador>> getAllAdministradores() {
        List<Administrador> administradores = administradorRepository.findAll();
        return ResponseEntity.ok(administradores);
    }
    @PostMapping("/save")
    public String saveAdministrador(@ModelAttribute Administrador administrador) {
        administradorRepository.save(administrador);
        return "redirect:/admin/form";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Administrador> listAdministradores() {
        return administradorRepository.findAll();
    }

    @GetMapping("/detalle/{id}")
    @ResponseBody
    public ResponseEntity<Administrador> getAdministradorById(@PathVariable Long id) {
        Optional<Administrador> administrador = administradorRepository.findById(id);
        return administrador.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteAdministrador(@PathVariable Long id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}





