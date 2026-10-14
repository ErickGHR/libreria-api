package com.api.libreria.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.libreria.models.Usuario;
import com.api.libreria.repositories.UsuarioRepository;

@Service
public class UsuarioService extends BaseCrudService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository, "El usuario");
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        usuarioRepository.findByCorreoElectronicoIgnoreCase(usuario.getCorreoElectronico())
                .filter(saved -> !saved.getId().equals(usuario.getId()))
                .ifPresent(saved -> {
                    throw new IllegalArgumentException("Ya existe un usuario con ese correo");
                });

        if (usuario.getId() != null) {
            Usuario saved = findById(usuario.getId());
            if (usuario.getFechaCreacion() == null) {
                usuario.setFechaCreacion(saved.getFechaCreacion());
            }
        }
        return usuarioRepository.save(usuario);
    }
}
