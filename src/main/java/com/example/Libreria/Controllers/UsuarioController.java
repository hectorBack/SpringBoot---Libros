package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Entity.Libro;
import com.example.Libreria.Entity.Usuario;
import com.example.Libreria.Repository.UsuarioRepository;
import com.example.Libreria.Services.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Usuario con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> guardar(@RequestBody Usuario usuario) {
        Usuario usuarioGuardado = usuarioService.guardar(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario registradp con éxito");
        response.put("usuario", usuarioGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.actualizar(id, usuarioActualizado);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + "no encontrado");
        }
    }
}
