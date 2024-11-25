package com.example.javaparcial.Repository;

import com.example.javaparcial.Entity.AgendarCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendarCitaRepository extends JpaRepository<AgendarCita, Long> {
    // Métodos personalizados si los necesitas
}
