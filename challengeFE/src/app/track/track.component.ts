import { Component, Input } from '@angular/core';
import { Cancion } from '../main/main.component';
import { faPlay, faPause,faHeart } from '@fortawesome/free-solid-svg-icons';
import { SpotifyService } from '../spotify/spotify.service';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent  {

  faPlay = faPlay;
  faHeart = faHeart;
  @Input() track: Cancion;
  faPause = faPause;
  isPlaying = false;
  isLiked = false;
  isLoggedIn$: Observable<boolean>; 

  constructor(
    private spotifyService: SpotifyService,
    private authService: AuthService,
    private router: Router) {    
 }



 ngOnInit(): void {
   this.isLoggedIn$ = this.authService.isLoggedIn$; 
 }



  formatearTexto(text: string, maxLength: number): string {
    if (text.length <= maxLength) {
      return text;
    }
    return text.substr(0, maxLength) + '...';
  }

  mostrarBotonFav(): boolean {
    const url = this.router.url;
    if(url.endsWith('/favoritos') || !localStorage.getItem('token') || localStorage.getItem('token') == '') {
      return false;
    } else {
        return true;
    }
  }

  togglePlayback(audio: HTMLAudioElement): void {
    if (this.isPlaying) {
      audio.pause();
    } else {
      audio.play();
    }
    this.isPlaying = !this.isPlaying;
  }
  toggleLike() {
    const emailUsuario = this.authService.extraerUserEmailFromToken();
    console.log(emailUsuario);
    if (this.isLiked) {
      this.spotifyService.unlikeTrack(this.track.id, emailUsuario).subscribe({
        next: () => {
          this.isLiked = false;
          const cancionesFavoritas = localStorage.getItem('cancionesFavoritas');
          if( cancionesFavoritas !== null) {
            const cancionesFavoritasArray = cancionesFavoritas.split(',');
            const cancionesFavoritasArrayFiltered = cancionesFavoritasArray.filter(
               cancionId => this.track.id != cancionId);

            localStorage.setItem('cancionesFavoritas', cancionesFavoritasArrayFiltered.toString())
            console.log(localStorage.getItem('cancionesFavoritas'));
          }
        },
        error: (error) => {
          console.error(error);
        }
      });
    } else {
      this.spotifyService.likeTrack(this.track.id, emailUsuario).subscribe({
        next: () => {
          this.isLiked = true;
          const cancionesFavoritas = localStorage.getItem('cancionesFavoritas');
          if( cancionesFavoritas !== null) {
            const cancionesFavoritasArray = cancionesFavoritas.split(',');
            cancionesFavoritasArray.push(this.track.id);
            localStorage.setItem('cancionesFavoritas', cancionesFavoritasArray.toString())
            console.log(localStorage.getItem('cancionesFavoritas'));
          } else {
            localStorage.setItem('cancionesFavoritas', this.track.id)
          }       
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }
  
}
