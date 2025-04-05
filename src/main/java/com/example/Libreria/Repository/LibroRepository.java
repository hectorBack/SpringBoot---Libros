package com.example.Libreria.Repository;

import com.example.Libreria.Entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {


}
