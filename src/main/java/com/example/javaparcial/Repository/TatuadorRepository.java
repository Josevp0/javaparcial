package com.example.javaparcial.Repository;


import com.example.javaparcial.Entity.Tatuador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TatuadorRepository extends JpaRepository<Tatuador, Long> {
    Optional<Tatuador> findByEmail(String email);
}
