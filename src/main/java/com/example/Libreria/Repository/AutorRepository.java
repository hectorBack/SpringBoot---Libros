package com.example.Libreria.Repository;

import com.example.Libreria.Entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
