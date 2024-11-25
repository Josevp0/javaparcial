package com.example.javaparcial.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tatuaje {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] imagen;

    private String nombre;
    private String descripcion;
    private int precio; // Precio como entero


    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private Tatuador tatuador;

    @OneToMany(mappedBy = "tatuaje", cascade = CascadeType.ALL)
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Tatuador getTatuador() {
        return tatuador;
    }

    public void setTatuador(Tatuador tatuador) {
        this.tatuador = tatuador;
    }

    public List<AgendarCita> getCitas() {
        return citas;
    }

    public void setCitas(List<AgendarCita> citas) {
        this.citas = citas;
    }
}

