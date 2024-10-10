import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http = inject(HttpClient);
  onlogin(user: { email: string; password: string }) {
    return this.http.post<{
      id: number;
      email: string;
      message: string;
      token: string;
      status: number;
      admin: boolean;
    }>('http://localhost:8080/login', user);
  }
}
