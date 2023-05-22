import { Component, OnInit } from '@angular/core';
import { SpotifyService } from './spotify/spotify.service';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'flex-tech-challenge';
  constructor( private authService: AuthService ) {
    
  }
  ngOnInit(): void {
    console.log('iniciando app..')
    
  }
}
