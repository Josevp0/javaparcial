package com.example.javaparcial.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email; // Cambio realizado: de "correo" a "email"
    private String contraseña;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private List<Tatuador> tatuadores;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private List<Cliente> clientes;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() { // Cambio realizado
        return email;
    }

    public void setEmail(String email) { // Cambio realizado
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Tatuador> getTatuadores() {
        return tatuadores;
    }

    public void setTatuadores(List<Tatuador> tatuadores) {
        this.tatuadores = tatuadores;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}

