package com.flexTechChallenge.controllers;

import com.flexTechChallenge.auth.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginUsuario() throws Exception {
        AuthRequestDTO request = AuthRequestDTO.builder()
                .email("email")
                .password("password")
                .build();
        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .token("randomtoken")
                .build();

        when(authenticationService.login(request)).thenReturn(responseDTO);

        ResponseEntity<AuthResponseDTO> response = authenticationController.loginUsuario(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testRegistrarUsuario() {
        RegistrarRequestDTO request = new RegistrarRequestDTO();
        AuthResponseDTO expectedResponse = new AuthResponseDTO();

        when(authenticationService.registrar(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthResponseDTO> response = authenticationController.registrarUsuario(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAutenticarUsuario() {
        AuthRequestDTO request = new AuthRequestDTO();
        AuthResponseDTO expectedResponse = new AuthResponseDTO();

        when(authenticationService.autenticar(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthResponseDTO> response = authenticationController.autenticarUsuario(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
