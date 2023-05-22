package com.flexTechChallenge.auth;

import com.flexTechChallenge.config.JwtService;
import com.flexTechChallenge.repositories.UsuarioRepository;
import com.flexTechChallenge.usuario.Role;
import com.flexTechChallenge.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponseDTO registrar(RegistrarRequestDTO request){
        var usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        usuarioRepository.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder().
                token(jwtToken)
                .build();
    }

    public AuthResponseDTO autenticar(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder().
                token(jwtToken)
                .build();
    }

    public AuthResponseDTO login(AuthRequestDTO request) throws Exception {
        String userEmail = request.getEmail();
        String password = request.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, password)
            );
        } catch (AuthenticationException ex) {
            throw new Exception("Invalid username or password");
        }

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("Invalid username or password"));

        String authToken = jwtService.generateToken(usuario);

        return new AuthResponseDTO(authToken);
    }

}
