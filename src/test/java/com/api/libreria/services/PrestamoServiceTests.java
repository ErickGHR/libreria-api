package com.api.libreria.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.api.libreria.models.Libro;
import com.api.libreria.models.Prestamo;
import com.api.libreria.models.Usuario;

@SpringBootTest
@Transactional
class PrestamoServiceTests {

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PrestamoService prestamoService;

    @Test
    void noPrestaMasEjemplaresDeLosQueExisten() {
        Libro libro = new Libro();
        libro.setIsbn("978-1-23456-789-0");
        libro.setTitulo("Un libro de prueba");
        libro.setAutor("Autora de prueba");
        libro.setAnioPublicacion(2024);
        libro.setTotalEjemplares(1);
        libroService.save(libro);

        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario.setApellidos("López");
        usuario.setCorreoElectronico("ana@correo.com");
        usuario.setContrasena("clave-segura");
        usuario = usuarioService.save(usuario);

        Prestamo primero = new Prestamo();
        primero.setLibro(libro);
        primero.setUsuario(usuario);
        primero = prestamoService.save(primero);
        assertNotNull(primero.getFechaPrestamo());

        Prestamo segundo = new Prestamo();
        segundo.setLibro(libro);
        segundo.setUsuario(usuario);
        assertThrows(IllegalArgumentException.class, () -> prestamoService.save(segundo));
    }
}
