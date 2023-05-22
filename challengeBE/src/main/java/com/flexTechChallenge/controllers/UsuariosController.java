package com.flexTechChallenge.controllers;

import com.flexTechChallenge.usuario.Usuario;
import com.flexTechChallenge.services.UsuariosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuariosService.getUsuarios();
    }

}
