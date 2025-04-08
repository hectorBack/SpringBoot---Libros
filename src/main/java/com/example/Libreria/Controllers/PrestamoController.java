package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Entity.Libro;
import com.example.Libreria.Entity.Prestamo;
import com.example.Libreria.Services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Prestamo> prestamo = prestamoService.obtenerPorId(id);

        if (prestamo.isPresent()) {
            return ResponseEntity.ok(prestamo.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Prestamo con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Prestamo prestamo) {
        Prestamo prestamoGuardado = prestamoService.guardar(prestamo);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Prestamo registrado con éxito");
        response.put("prestamo", prestamoGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrestamo(@PathVariable Long id, @RequestBody Prestamo prestamoActualizado) {
        try {
            Prestamo prestamo = prestamoService.actualizar(id, prestamoActualizado);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/prestar")
    public ResponseEntity<Prestamo> prestarLibro(@RequestBody Prestamo prestamo) {
        return new ResponseEntity<>(prestamoService.realizarPrestamo(prestamo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            prestamoService.eliminar(id);
            return ResponseEntity.ok("Prestamo eliminado correctamente");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prestamo con ID " + id + "no encontrado");
        }
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }
}
