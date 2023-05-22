import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpotifyService {
  private baseUrl: string = 'http://localhost:8080/api/v1/spotify'; 

  constructor(private http: HttpClient) {}

  getCancion(id: string): Observable<string> {
    const url = `${this.baseUrl}/track/${id}`;
    return this.http.get<string>(url);
  }

 getCanciones(ids: string): Observable<string> {
  const url = `${this.baseUrl}/tracks/${ids}`;
  return this.http.get<string>(url);
}

  buscarCanciones(query: string, type: string): Observable<string> {
    const url = `${this.baseUrl}/search?q=${query}&type=${type}`;
    return this.http.get<string>(url);
  }

  getFavoritos(userEmail: string): Observable<any> {
    const url = `${this.baseUrl}/favoritos?userEmail=${userEmail}`;
    console.log(url)
    return this.http.get<string>(url);
  }

  likeTrack(spotifyId: string, userEmail: string): Observable<any> {
    const url = `${this.baseUrl}/like`; 
    const body = { spotifyId, userEmail };
    return this.http.post(url, body);
  }
  
  unlikeTrack(spotifyId: string, userEmail: string): Observable<any> {
    const url = `${this.baseUrl}/unlike`; 
    const body = { spotifyId, userEmail };
    return this.http.post(url, body);
  }


}
