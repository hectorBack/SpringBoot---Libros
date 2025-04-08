package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Entity.Categoria;
import com.example.Libreria.Entity.Libro;
import com.example.Libreria.Services.LibroService;
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
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> listar(){
        return libroService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Libro> libro = libroService.obtenerPorId(id);

        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Libro con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Libro libro) {
        Libro libroGuardado = libroService.guardar(libro);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro registradp con éxito");
        response.put("libro", libroGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        try {
            Libro libro = libroService.actualizar(id, libroActualizado);
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            libroService.eliminar(id);
            return ResponseEntity.ok("Libro eliminado correctamente");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro con ID " + id + " no encontrado");
        }
    }
}
