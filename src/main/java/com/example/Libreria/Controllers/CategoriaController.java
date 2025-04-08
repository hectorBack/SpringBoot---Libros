package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Categoria;
import com.example.Libreria.Repository.CategoriaRepository;
import com.example.Libreria.Services.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Categoria> categoria = categoriaService.obtenerPorId(id);

        if (categoria.isPresent()){
            return ResponseEntity.ok(categoria.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Categoria con ID " + id + "no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Categoria categoria) {
        Categoria categoriaGuardado = categoriaService.guardar(categoria);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Categoria registrada con éxito");
        response.put("categoria", categoriaGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaActualizada) {
        try {
            Categoria categoria = categoriaService.actualizar(id, categoriaActualizada);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok("Categoría eliminada correctamente");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría con ID " + id + " no encontrada");
        }
    }

}
