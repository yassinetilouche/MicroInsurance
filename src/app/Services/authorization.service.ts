import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor() { }

  isAuthorized(allowedRole?: string[]): boolean {
    if (!allowedRole) {
      return true;
    }
    return allowedRole?.includes(localStorage.getItem('role') ?? '');
  }
}