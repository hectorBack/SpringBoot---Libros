package com.example.Libreria.Services;

import com.example.Libreria.Entity.Usuario;
import com.example.Libreria.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        usuarioExistente.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminar (Long id){
        usuarioRepository.deleteById(id);
    }

}
