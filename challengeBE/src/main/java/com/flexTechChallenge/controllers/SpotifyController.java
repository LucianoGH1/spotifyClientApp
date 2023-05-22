package com.flexTechChallenge.controllers;

import com.flexTechChallenge.SpotifyApiClient;
import com.flexTechChallenge.dtos.CancionFavoritaDTO;
import com.flexTechChallenge.dtos.FavCancionDTO;
import com.flexTechChallenge.services.CancionFavoritaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/v1/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    @Autowired
    private SpotifyApiClient spotifyApiClient;
    @Autowired
    private CancionFavoritaService cancionFavoritaService;

    @GetMapping("/track/{id}")
    public ResponseEntity<String> getTrack(@PathVariable String id) throws Exception {
        String response = spotifyApiClient.getTrack(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/tracks/{ids}")
    public ResponseEntity<String> getTracks(@PathVariable String ids) throws Exception {
        String response = spotifyApiClient.getTracks(ids);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<String> searchTracks(@RequestParam("q") String query,
                                               @RequestParam("type") String type) throws Exception {
        String response = spotifyApiClient.searchTracks(query, type);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/like")
    public void likeTrack(@RequestBody FavCancionDTO request) {
        cancionFavoritaService.likeTrack(request);

    }

    @PostMapping("/unlike")
    @Transactional
    public ResponseEntity<Void> unlikeTrack(@RequestBody FavCancionDTO request) {
        cancionFavoritaService.unlikeTrack(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favoritos")
    public ResponseEntity<List<CancionFavoritaDTO>> getFavoriteTracks(@RequestParam("userEmail") String userEmail) {
        List<CancionFavoritaDTO> resultado = cancionFavoritaService.getFavoriteTracks(userEmail);
        return ResponseEntity.ok(resultado);
    }
}