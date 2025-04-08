package com.example.Libreria.Services;

import com.example.Libreria.Entity.Categoria;
import com.example.Libreria.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodos() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerPorId(Long id){
        return categoriaRepository.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria categoriaActualizado){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con ID " + id + " no encontrado"));

        categoriaExistente.setNombre(categoriaActualizado.getNombre());

        return categoriaRepository.save(categoriaExistente);

    }

    public void eliminar(Long id){
        categoriaRepository.deleteById(id);
    }


}
