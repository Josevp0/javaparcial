package com.example.javaparcial.Repository;

import com.example.javaparcial.Entity.Administrador;
import com.example.javaparcial.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}
