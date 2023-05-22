package com.flexTechChallenge.repositories;

import com.flexTechChallenge.favoritos.CancionFavorita;
import com.flexTechChallenge.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CancionFavoritaRepository
        extends JpaRepository<CancionFavorita, Integer> {

    void deleteBySpotifyIdAndUsuario(String spotifyId, Usuario usuario);

    List<CancionFavorita> findByUsuario(Usuario usuario);
}
