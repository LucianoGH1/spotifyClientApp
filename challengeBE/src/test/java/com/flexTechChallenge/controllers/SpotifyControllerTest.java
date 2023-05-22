package com.flexTechChallenge.controllers;

import com.flexTechChallenge.SpotifyApiClient;
import com.flexTechChallenge.dtos.CancionFavoritaDTO;
import com.flexTechChallenge.dtos.FavCancionDTO;
import com.flexTechChallenge.services.CancionFavoritaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyControllerTest {
    @Mock
    private SpotifyApiClient spotifyApiClient;

    @Mock
    private CancionFavoritaService cancionFavoritaService;

    @InjectMocks
    private SpotifyController spotifyController;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetTrack() throws Exception {
        when(spotifyApiClient.getTrack(anyString())).thenReturn("Respuesta de la api");
        ResponseEntity<String> response = spotifyController.getTrack("trackId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Respuesta de la api", response.getBody());
    }
    @Test
    public void testLikeTrack() {
        FavCancionDTO request = new FavCancionDTO();
        spotifyController.likeTrack(request);
        verify(cancionFavoritaService).likeTrack(request);
    }

    @Test
    public void testGetTracks() throws Exception {
        String ids = "track1,track2";

        when(spotifyApiClient.getTracks(ids)).thenReturn("Respuesta de la api");

        ResponseEntity<String> response = spotifyController.getTracks(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Respuesta de la api", response.getBody());
    }

    @Test
    public void testSearchTracks() throws Exception {
        String query = "query";
        String type = "type";

        when(spotifyApiClient.searchTracks(query, type)).thenReturn("Respuesta de la api");

        ResponseEntity<String> response = spotifyController.searchTracks(query, type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Respuesta de la api", response.getBody());
    }

    @Test
    public void testUnlikeTrack() {
        FavCancionDTO request = new FavCancionDTO();

        ResponseEntity<Void> response = spotifyController.unlikeTrack(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetFavoriteTracks() {
        String userEmail = "user@example.com";
        List<CancionFavoritaDTO> expectedResult = Arrays.asList(new CancionFavoritaDTO());

        when(cancionFavoritaService.getFavoriteTracks(userEmail)).thenReturn(expectedResult);

        ResponseEntity<List<CancionFavoritaDTO>> response = spotifyController.getFavoriteTracks(userEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
    }
}