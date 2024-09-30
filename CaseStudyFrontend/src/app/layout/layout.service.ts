import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {
  // http = inject(HttpClient);
  // acctoken: string = '';
  
  // getaccesstoken() {
  //   const token = localStorage.getItem('token');
  //   if (token) {
  //     this.acctoken = JSON.parse(token);
  //   }
  // }
  // getheaders() {
  //   this.getaccesstoken();
  //   const headers = new HttpHeaders({
  //     Authorization: `Bearer ${this.acctoken}`,
  //   });
  //   return headers;
  // }
  // getuser() {
  //   const headers = this.getheaders();
    
  //   return this.http.get<any>(
  //     `http://localhost:8080/employee/${data.id}/getEmployee`,
  //     {
  //       headers,
  //     }
  //   );
  // }
}
