package com.api.libreria.services;

import org.springframework.stereotype.Service;

import com.api.libreria.models.Libro;
import com.api.libreria.repositories.LibroRepository;

@Service
public class LibroService extends BaseCrudService<Libro, String> {

    public LibroService(LibroRepository repository) {
        super(repository, "El libro");
    }

    public boolean existsById(String isbn) {
        return repository.existsById(isbn);
    }
}
