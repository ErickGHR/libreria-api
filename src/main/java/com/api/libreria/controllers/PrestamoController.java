package com.api.libreria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.libreria.models.Prestamo;
import com.api.libreria.services.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) { this.service = service; }

    @GetMapping
    public List<Prestamo> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Prestamo findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Prestamo> create(@Valid @RequestBody Prestamo prestamo) {
        prestamo.setId(null);
        Prestamo saved = service.save(prestamo);
        return ResponseEntity.created(URI.create("/api/prestamos/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Prestamo update(@PathVariable Long id, @Valid @RequestBody Prestamo prestamo) {
        service.findById(id);
        prestamo.setId(id);
        return service.save(prestamo);
    }

    @PatchMapping("/{id}/devolver")
    public Prestamo devolver(@PathVariable Long id) {
        return service.devolver(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
