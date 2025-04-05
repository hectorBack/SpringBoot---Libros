package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Categoria;
import com.example.Libreria.Repository.CategoriaRepository;
import com.example.Libreria.Services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.obtenerTodos();
    }

    @PostMapping("/nuevo")
    public Categoria guardar(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
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
