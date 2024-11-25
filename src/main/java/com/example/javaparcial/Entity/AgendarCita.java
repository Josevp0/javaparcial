package com.example.javaparcial.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AgendarCita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCita;

    @ManyToOne
    @JoinColumn(name = "tatuaje_id")
    private Tatuaje tatuaje;

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private Tatuador tatuador;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Tatuaje getTatuaje() {
        return tatuaje;
    }

    public void setTatuaje(Tatuaje tatuaje) {
        this.tatuaje = tatuaje;
    }

    public Tatuador getTatuador() {
        return tatuador;
    }

    public void setTatuador(Tatuador tatuador) {
        this.tatuador = tatuador;
    }
}
