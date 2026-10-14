package com.api.libreria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.libreria.models.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
}
