import { Component, Input } from '@angular/core';
import { SpotifyService } from '../spotify/spotify.service';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
export interface Cancion {
  id: string;
  name: string;
  artists: { name: string }[];
  album: { images: { url: string }[] };
  preview_url: string;
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
  query: string = '';
  data: Cancion[] = [];
  mostrarAlerta: boolean = false;
  faSearch = faSearch;

  @Input() track: any;
  constructor(private service: SpotifyService) {}

  search() {
    this.service.buscarCanciones(this.query, 'track')
      .subscribe((response: any) => {
        console.log(response);
        this.data = response.tracks.items;
        if(this.data.length === 0) {
          this.mostrarAlerta = true;
        } else {
          this.mostrarAlerta = false;
        }
      });
  }


}
