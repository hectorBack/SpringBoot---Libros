package com.example.Libreria.Controllers;

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
    public Optional<Libro> obtenerPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Libro libro) {
        Libro libroGuardado = libroService.guardar(libro);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro registradp con éxito");
        response.put("libro", libroGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
