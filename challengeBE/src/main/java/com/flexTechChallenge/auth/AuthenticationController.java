package com.flexTechChallenge.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUsuario(
            @RequestBody AuthRequestDTO request
    ) throws Exception {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> registrarUsuario(
            @RequestBody RegistrarRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.registrar(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> autenticarUsuario(
            @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.autenticar(request));
    }
}
