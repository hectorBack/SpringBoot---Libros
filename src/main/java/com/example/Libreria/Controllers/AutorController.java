package com.example.Libreria.Controllers;

import com.example.Libreria.Entity.Autor;
import com.example.Libreria.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping
    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    @PostMapping
    public Autor guardar(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }

}
