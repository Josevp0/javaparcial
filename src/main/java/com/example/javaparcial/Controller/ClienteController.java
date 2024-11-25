package com.example.javaparcial.Controller;


import com.example.javaparcial.Entity.Cliente;
import com.example.javaparcial.Repository.ClienteRepository;
import com.example.javaparcial.Repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller // Cambiar de @RestController a @Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Mostrar formulario de login
    @GetMapping("/logincli")
    public String showLoginForm() {
        return "logincliente"; // Nombre del archivo HTML sin extensión
    }

    @GetMapping("/clienteindex")
    public String showoclienteinView() {
        return "clienteindex"; // Nombre del archivo HTML sin la extensión
    }

    @GetMapping("/formcliente")
    public String formcliente() {
        return "formcliente"; // Nombre del archivo HTML sin la extensión
    }

    // Procesar login
    @PostMapping("/logincli")
    public String loginCliente(@RequestParam String email, @RequestParam String password) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if (cliente.isPresent() && cliente.get().getContraseña().equals(password)) {
            return "redirect:/clientes/clienteindex";
        }
        return "redirect:/clientes/logincli?error=true";
    }

    // Mostrar todos los clientes
    @GetMapping("/ver")
    public String verClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "vercliente"; // Archivo HTML para ver clientes
    }

    // Mostrar formulario para crear cliente
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new Cliente()); // Cliente vacío para el formulario
        return "formcliente"; // Archivo HTML para el formulario
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes/ver";
    }

    // Mostrar formulario para actualizar cliente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElse(null); // Obtener cliente por ID
        model.addAttribute("cliente", cliente); // Pasar el cliente al modelo
        return "formcliente"; // Reutilizar el mismo formulario
    }

    // Eliminar un cliente
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes/ver";
    }
}


