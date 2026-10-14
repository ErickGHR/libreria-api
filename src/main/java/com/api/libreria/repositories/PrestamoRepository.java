package com.api.libreria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.libreria.models.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    @Query("""
            select count(p) from Prestamo p
            where p.libro.isbn = :isbn
              and p.fechaDevolucion is null
              and (:prestamoId is null or p.id <> :prestamoId)
            """)
    long contarPrestamosActivos(
            @Param("isbn") String isbn,
            @Param("prestamoId") Long prestamoId);
}
