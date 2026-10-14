package com.api.libreria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.libreria.models.Libro;
import com.api.libreria.services.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) { this.service = service; }

    @GetMapping
    public List<Libro> findAll() { return service.findAll(); }

    @GetMapping("/{isbn}")
    public Libro findById(@PathVariable String isbn) { return service.findById(isbn); }

    @PostMapping
    public ResponseEntity<Libro> create(@Valid @RequestBody Libro libro) {
        if (service.existsById(libro.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con ese ISBN");
        }
        Libro saved = service.save(libro);
        return ResponseEntity.created(URI.create("/api/libros/" + saved.getIsbn())).body(saved);
    }

    @PutMapping("/{isbn}")
    public Libro update(@PathVariable String isbn, @Valid @RequestBody Libro libro) {
        service.findById(isbn);
        libro.setIsbn(isbn);
        return service.save(libro);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        service.deleteById(isbn);
        return ResponseEntity.noContent().build();
    }
}
