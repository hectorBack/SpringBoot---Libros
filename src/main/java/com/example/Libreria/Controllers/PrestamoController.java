package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Prestamo;
import com.example.Libreria.Services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoService.obtenerTodos();
    }

    @PostMapping("/nuevo")
   public Prestamo guardar (@RequestBody Prestamo prestamo){
        return prestamoService.guardar(prestamo);
    }

    @PostMapping("/prestar")
    public ResponseEntity<Prestamo> prestarLibro(@RequestBody Prestamo prestamo) {
        return new ResponseEntity<>(prestamoService.realizarPrestamo(prestamo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }
}
