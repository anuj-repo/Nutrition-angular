import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor() {}

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles() {
    return JSON.parse(sessionStorage.getItem('roles'));
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken');
  }

  public clear() {
    localStorage.clear();
    sessionStorage.clear();
  }

  public isLoggedIn() {
    return this.getRoles() && this.getToken();
  }

  public isAdmin() {
    const roles= this.getRoles();
    return roles == 'Admin';
  }

  public isUser() {
    const roles: any[] = this.getRoles();
    return roles[0].roleName === 'User';
  }
}
