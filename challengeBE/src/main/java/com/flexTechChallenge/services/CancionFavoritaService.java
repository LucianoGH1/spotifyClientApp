package com.flexTechChallenge.services;

import com.flexTechChallenge.dtos.CancionFavoritaDTO;
import com.flexTechChallenge.dtos.FavCancionDTO;
import com.flexTechChallenge.favoritos.CancionFavorita;
import com.flexTechChallenge.repositories.CancionFavoritaRepository;
import com.flexTechChallenge.repositories.UsuarioRepository;
import com.flexTechChallenge.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancionFavoritaService {

    private final CancionFavoritaRepository cancionFavoritaRepository;
    private final UsuarioRepository usuarioRepository;

    public CancionFavoritaService(CancionFavoritaRepository cancionFavoritaRepository, UsuarioRepository usuarioRepository) {
        this.cancionFavoritaRepository = cancionFavoritaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void likeTrack(FavCancionDTO request) {
        CancionFavorita cancionFavorita = new CancionFavorita();
        cancionFavorita.setSpotifyId(request.getSpotifyId());
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getUserEmail());

        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            cancionFavorita.setUsuario(usuario);
            cancionFavoritaRepository.save(cancionFavorita);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }


    }

    public void unlikeTrack(FavCancionDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        cancionFavoritaRepository.deleteBySpotifyIdAndUsuario(request.getSpotifyId(), usuario);
    }


    public List<CancionFavoritaDTO> getFavoriteTracks(String userEmail) {

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CancionFavorita> cancionesFavoritas = cancionFavoritaRepository.findByUsuario(usuario);
        List<CancionFavoritaDTO> resultado = new ArrayList<>();

        for (CancionFavorita cancionFavorita : cancionesFavoritas) {
            CancionFavoritaDTO cancionFavoritaDTO =
                    CancionFavoritaDTO.builder()
                    .spotifyId(cancionFavorita.getSpotifyId())
                    .build();

            resultado.add(cancionFavoritaDTO);
        }
        return resultado;
    }
}
