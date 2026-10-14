package com.api.libreria.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.libreria.models.Libro;
import com.api.libreria.models.Prestamo;
import com.api.libreria.models.Usuario;
import com.api.libreria.models.Usuario.EstadoUsuario;
import com.api.libreria.repositories.PrestamoRepository;

@Service
public class PrestamoService extends BaseCrudService<Prestamo, Long> {

    private final PrestamoRepository prestamoRepository;
    private final LibroService libroService;
    private final UsuarioService usuarioService;

    public PrestamoService(
            PrestamoRepository prestamoRepository,
            LibroService libroService,
            UsuarioService usuarioService) {
        super(prestamoRepository, "El préstamo");
        this.prestamoRepository = prestamoRepository;
        this.libroService = libroService;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public Prestamo save(Prestamo prestamo) {
        if (prestamo.getLibro() == null || prestamo.getLibro().getIsbn() == null) {
            throw new IllegalArgumentException("Debes indicar el ISBN del libro");
        }
        if (prestamo.getUsuario() == null || prestamo.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Debes indicar el usuario");
        }

        Libro libro = libroService.findById(prestamo.getLibro().getIsbn());
        Usuario usuario = usuarioService.findById(prestamo.getUsuario().getId());
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new IllegalArgumentException("El usuario no está activo");
        }

        if (prestamo.getFechaDevolucion() == null) {
            long prestados = prestamoRepository.contarPrestamosActivos(libro.getIsbn(), prestamo.getId());
            if (prestados >= libro.getTotalEjemplares()) {
                throw new IllegalArgumentException("No hay ejemplares disponibles de este libro");
            }
        }

        if (prestamo.getId() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaDevolucion(null);
        } else {
            Prestamo saved = findById(prestamo.getId());
            prestamo.setFechaPrestamo(saved.getFechaPrestamo());
        }
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolver(Long id) {
        Prestamo prestamo = findById(id);
        if (!prestamo.isActivo()) {
            throw new IllegalArgumentException("Ese préstamo ya fue devuelto");
        }
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamoRepository.save(prestamo);
    }
}
