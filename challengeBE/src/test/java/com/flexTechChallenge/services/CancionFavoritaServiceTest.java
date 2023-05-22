package com.flexTechChallenge.services;

import com.flexTechChallenge.dtos.CancionFavoritaDTO;
import com.flexTechChallenge.dtos.FavCancionDTO;
import com.flexTechChallenge.favoritos.CancionFavorita;
import com.flexTechChallenge.repositories.CancionFavoritaRepository;
import com.flexTechChallenge.repositories.UsuarioRepository;
import com.flexTechChallenge.usuario.Role;
import com.flexTechChallenge.usuario.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CancionFavoritaServiceTest {

    @Mock
    private CancionFavoritaRepository cancionFavoritaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CancionFavoritaService cancionFavoritaService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLikeTrack() {
        FavCancionDTO request = new FavCancionDTO();
        request.setSpotifyId("trackId");
        request.setUserEmail("user@example.com");

        Usuario usuario = new Usuario();
        when(usuarioRepository.findByEmail(request.getUserEmail())).thenReturn(Optional.of(usuario));

        cancionFavoritaService.likeTrack(request);

        verify(cancionFavoritaRepository).save(any(CancionFavorita.class));
    }

    @Test
    public void testLikeTrack_ThrowsException_WhenUserNotFound() {
        FavCancionDTO request = new FavCancionDTO();
        request.setSpotifyId("trackId");
        request.setUserEmail("user@example.com");

        when(usuarioRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancionFavoritaService.likeTrack(request));

        verify(cancionFavoritaRepository, never()).save(any(CancionFavorita.class));
    }

    @Test
    public void testUnlikeTrack() {
        FavCancionDTO request = new FavCancionDTO();
        request.setSpotifyId("trackId");
        request.setUserEmail("user@example.com");

        Usuario usuario = new Usuario();
        when(usuarioRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.of(usuario));

        cancionFavoritaService.unlikeTrack(request);

        verify(cancionFavoritaRepository).deleteBySpotifyIdAndUsuario(eq("trackId"), eq(usuario));
    }

    @Test
    public void testUnlikeTrack_ThrowsException_WhenUserNotFound() {
        FavCancionDTO request = new FavCancionDTO();
        request.setSpotifyId("trackId");
        request.setUserEmail("user@example.com");

        when(usuarioRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancionFavoritaService.unlikeTrack(request));

        verify(cancionFavoritaRepository, never()).deleteBySpotifyIdAndUsuario(anyString(), any(Usuario.class));
    }

    @Test
    public void testGetFavoriteTracks() {
        String userEmail = "user@example.com";

        Usuario usuario = new Usuario();
        when(usuarioRepository.findByEmail(eq(userEmail))).thenReturn(Optional.of(usuario));

        CancionFavorita cancionFavorita1 = new CancionFavorita();
        cancionFavorita1.setSpotifyId("trackId1");
        cancionFavorita1.setUsuario(usuario);

        CancionFavorita cancionFavorita2 = new CancionFavorita();
        cancionFavorita2.setSpotifyId("trackId2");
        cancionFavorita2.setUsuario(usuario);

        List<CancionFavorita> cancionesFavoritas = new ArrayList<>();
        cancionesFavoritas.add(cancionFavorita1);
        cancionesFavoritas.add(cancionFavorita2);

        when(cancionFavoritaRepository.findByUsuario(eq(usuario))).thenReturn(cancionesFavoritas);

        List<CancionFavoritaDTO> resultado = cancionFavoritaService.getFavoriteTracks(userEmail);

        assertEquals(2, resultado.size());
        assertEquals("trackId1", resultado.get(0).getSpotifyId());
        assertEquals("trackId2", resultado.get(1).getSpotifyId());
    }

    @Test
    public void testGetFavoriteTracks_ThrowsException_WhenUserNotFound() {
        String userEmail = "user@example.com";

        when(usuarioRepository.findByEmail(eq(userEmail))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancionFavoritaService.getFavoriteTracks(userEmail));

        verify(cancionFavoritaRepository, never()).findByUsuario(any(Usuario.class));
    }
}