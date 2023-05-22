package com.flexTechChallenge.services;

import com.flexTechChallenge.dtos.NuevoUsuarioDTO;
import com.flexTechChallenge.usuario.Usuario;
import com.flexTechChallenge.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {
    private final UsuarioRepository usuarioRepository;

    public UsuariosService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

}
