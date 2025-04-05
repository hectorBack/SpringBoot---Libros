package com.example.Libreria.Repository;

import com.example.Libreria.Entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
