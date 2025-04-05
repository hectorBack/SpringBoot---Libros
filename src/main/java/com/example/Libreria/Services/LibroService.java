package com.example.Libreria.Services;

import com.example.Libreria.Entity.Libro;
import com.example.Libreria.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;


    //obtener todos
    public List<Libro> obtenerTodos(){
        return libroRepository.findAll();
    }

    //obtener por id
    public Optional<Libro> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    //guardar
    public Libro guardar(Libro libro){
        return libroRepository.save(libro);
    }

    public void eliminar(Long id){
        libroRepository.deleteById(id);
    }
}
