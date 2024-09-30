import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private http = inject(HttpClient);
  onlogin(user: any) {
    return this.http.post("http://localhost:8080/login", user);
  }
}
