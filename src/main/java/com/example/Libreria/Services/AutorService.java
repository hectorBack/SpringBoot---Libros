package com.example.Libreria.Services;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> obtenerTodos(){
        return autorRepository.findAll();
    }

    public Autor guardar (Autor autor){
        return autorRepository.save(autor);
    }

}
