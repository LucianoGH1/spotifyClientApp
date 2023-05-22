import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { SpotifyService } from '../spotify/spotify.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';
  private loggedIn$ = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private spotifySerivce: SpotifyService, private router: Router) {
    this.checkTokenInLocalStorage();
  }

  get isLoggedIn$(): Observable<boolean> {
    return this.loggedIn$.asObservable();
  }

  registrar(email: string, password: string): Observable<any> {
    const loginData = { email, password };
    return this.http.post(`${this.apiUrl}/registrar`, loginData).pipe(
      tap( (response:any) => {
        const token = response.token;
        localStorage.setItem('token', token);
        this.loggedIn$.next(true);
      })
    );
  }

  login(email: string, password: string): Observable<any> {
    const loginData = { email, password };
    return this.http.post(`${this.apiUrl}/login`, loginData).pipe(
      tap((response: any) => {
        const token = response.token;
        localStorage.setItem('token', token);
        const usuario = this.extraerUserEmailFromToken();
        
        this.loggedIn$.next(true);
        this.spotifySerivce.getFavoritos(usuario)
        .subscribe( (res) => {
          console.log(res);
          const cancionesArr: string[] = [];
          res.map((cancion: any) => {
            cancionesArr.push(cancion.spotifyId);
            
          })
          localStorage.setItem('cancionesFavoritas', cancionesArr.join(','));
        }
        ); 
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('cancionesFavoritas');
    this.loggedIn$.next(false);
    location.reload();
    this.router.navigate(['/']);
    
  }

  private checkTokenInLocalStorage(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      const userEmail = decodedToken.sub;
      if (userEmail) {
        this.loggedIn$.next(true);
      } else {
        this.loggedIn$.next(false);
      }
    } else {
      this.loggedIn$.next(false);
    }
  }

  extraerUserEmailFromToken(): string  {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwt_decode(token);
      const userEmail = decodedToken.sub;
      return userEmail;
    }
    return '';
  }
}
