import { Component, Input } from '@angular/core';
import { SpotifyService } from '../spotify/spotify.service';
import { Cancion } from '../main/main.component';

@Component({
  selector: 'app-favoritos',
  templateUrl: './favoritos.component.html',
  styleUrls: ['./favoritos.component.css']
})
export class FavoritosComponent {
  data: Cancion[] = [];
  mostrarAlerta: boolean = false;

  @Input() track: any;
  constructor(private service: SpotifyService) {}

  ngOnInit(): void {
    const ids = localStorage.getItem('cancionesFavoritas');
    if (ids !== null) {
      console.log(ids);
      this.service.getCanciones(ids)
      .subscribe((response: any) => {
        console.log(response);
        this.data = response.tracks;
        console.log(this.data);
 
      });
    }
  }

}
