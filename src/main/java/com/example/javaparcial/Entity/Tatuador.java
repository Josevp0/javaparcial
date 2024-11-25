package com.example.javaparcial.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tatuador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String email;
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

    @OneToMany(mappedBy = "tatuador", cascade = CascadeType.ALL)
    private List<Tatuaje> tatuajes;

    @OneToMany(mappedBy = "tatuador", cascade = CascadeType.ALL)
    private List<AgendarCita> citas;

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

    public String getEmail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<Tatuaje> getTatuajes() {
        return tatuajes;
    }

    public void setTatuajes(List<Tatuaje> tatuajes) {
        this.tatuajes = tatuajes;
    }

    public List<AgendarCita> getCitas() {
        return citas;
    }

    public void setCitas(List<AgendarCita> citas) {
        this.citas = citas;
    }
}
