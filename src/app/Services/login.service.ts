import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from '../Models/Login';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUri = environment.baseUri;

  constructor(private httpClient: HttpClient,
    private router: Router
  ) { }

  login(payload: Login) {
    return this.httpClient.post<any>(
      `${this.baseUri}/foryou/authenticate`,
      payload
    );
  }

  forgotPassword(email: string) {
    return this.httpClient.post<any>(
      `${this.baseUri}/foryou/forgot_password/${email}`,
      {}
    )
  }

  resetPassword(token: string, pwd: string) {
    return this.httpClient.post<any>(
      `${this.baseUri}/foryou/reset_password/${token}/${pwd}`,
      {}
    )
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/']);
  }

  loggedIn(): boolean {
    return localStorage.getItem('token') !== null;
  }
}
