package com.example.Libreria.Services;

import com.example.Libreria.Entity.Libro;
import com.example.Libreria.Entity.Prestamo;
import com.example.Libreria.Exception.ResourceNotFoundException;
import com.example.Libreria.Repository.LibroRepository;
import com.example.Libreria.Repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    public List<Prestamo> obtenerTodos(){
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> obtenerPorId(Long id){
        return prestamoRepository.findById(id);
    }

    public Prestamo guardar(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    public void eliminar (Long id){
        prestamoRepository.deleteById(id);
    }

    public Prestamo realizarPrestamo(Prestamo prestamo) {
        Libro libro = libroRepository.findById(prestamo.getLibro().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if (libro.getCantidadDisponible() <= 0) {
            throw new IllegalStateException("No hay copias disponibles del libro");
        }

        List<Prestamo> prestamosActivos = prestamoRepository.findByUsuarioIdAndEstado(prestamo.getUsuario().getId(), "Activo");
        if (!prestamosActivos.isEmpty()) {
            throw new IllegalStateException("El usuario ya tiene un préstamo activo");
        }

        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        libroRepository.save(libro);

        prestamo.setFechaPrestamo(new Date());
        prestamo.setEstado("Activo");
        return prestamoRepository.save(prestamo);
    }

    public Prestamo devolverPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));

        if (!"Activo".equals(prestamo.getEstado())) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado("Devuelto");
        prestamo.setFechaDevolucion(new Date());

        Libro libro = prestamo.getLibro();
        libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

}
