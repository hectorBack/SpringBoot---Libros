package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Repository.AutorRepository;
import com.example.Libreria.Services.AutorService;
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
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> listar() {
        return autorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Autor> autor = autorService.obtenerPorId(id);

        if (autor.isPresent()) {
            return ResponseEntity.ok(autor.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Autor con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Autor autor) {
        Autor autorGuardado = autorService.guardar(autor);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Autor registrado con éxito");
        response.put("autor", autorGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAutor(@PathVariable Long id, @RequestBody Autor autorActualizado) {
        try {
            Autor autor = autorService.actualizar(id, autorActualizado);
            return ResponseEntity.ok(autor);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            autorService.eliminar(id);
            return ResponseEntity.ok("Autor eliminado correctamente");
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Autor con ID " + id + " no encontrado");
        }
    }

}
