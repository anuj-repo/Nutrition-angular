import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';
import { environment } from './../../environments/environment';
import { API_PATH } from './../utils/constants/api.constants';
import { map } from 'rxjs/operators';
import { UtililtyFunctions } from '../utils/utils';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  PATH_OF_API = environment.BASE_URL;

  //requestHeader = new HttpHeaders({'Content-Type': 'application/json' });
  constructor(
    private http: HttpClient,
    private userAuthService: UserAuthService,
    private utils: UtililtyFunctions
  ) { }

  public register(registerData) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.ADD_USER}`, registerData)
      .pipe(map(userData => {
        return userData;
      }));
  }

  // public login(loginData) {
  //   return this.http.post(this.PATH_OF_API + '/auth/signin', loginData, {
  //     headers: this.requestHeader,
  //   });
  // }

  isEmailExist(email) {
    // Backend expects @RequestBody String which means a JSON-encoded string.
    // We must send Content-Type: application/json so Spring picks the right converter,
    // and the body must be a JSON-quoted string (e.g. "foo@bar.com").
    return this.http.post<any>(
      `${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.CHECK_EMAIL_EXIST}`,
      JSON.stringify(email),
      { headers: { 'Content-Type': 'application/json' } }
    ).pipe(map(userData => {
      return userData;
    }));
  }

  isPanExist(pan: string) {
    return this.http.post<any>(
      `${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.CHECK_PAN_EXIST}`,
      JSON.stringify(pan),
      { headers: { 'Content-Type': 'application/json' } }
    ).pipe(map(result => {
      return result;
    }));
  }

  login(data) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.SIGNIN}`, data)
      .pipe(map(userData => {
        if (userData && userData.data && userData.data.accessToken) {
          this.utils.saveUserToken(userData);
        }
        return userData;
      }));
  }

  getUserMeData() {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.USER_ME}`)
      .pipe(map(data => {
        return data;
      }));
  }

  getCurrentUserData(params = {}) {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.USER_ME}`, { params: params })
      .pipe(map(userData => {
        if (userData && userData.data) {
          if (!userData.data.loyaltyPoint) {
            userData.data.loyaltyPoint = 0;
          }
          if (!userData.data.voucherAmount) {
            userData.data.loyaltyPoint = 0;
          }
          this.utils.saveLoginedUserData(userData);
        }
        return userData;
      }));
  }


  public forUser() {
    return this.http.get(this.PATH_OF_API + '/forUser', {
      responseType: 'text',
    });
  }


  public forAdmin() {
    return this.http.get(this.PATH_OF_API + '/forAdmin', {
      responseType: 'text',
    });
  }

  public roleMatch(allowedRoles): boolean {
    let isMatch = false;
    const userRoles: any = this.userAuthService.getRoles();

    if (userRoles != null && userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i].roleName === allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          } else {
            return isMatch;
          }
        }
      }
    }
  }

  signOut(data) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.SIGNOUT}`, data)
      .pipe(map(userData => {
        localStorage.clear();
        sessionStorage.clear();
        return userData;
      }));
  }

  getCountries() {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.GET_COUNTRY}`)
      .pipe(map(userData => {
        return userData;
      }));
  }

  getStates(payload) {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.GET_ALL_STATES}`, payload)
      .pipe(map(data => {
        return data;
      }));
  }


  getCity(payload) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.GET_ALL_CITY}`, payload)
      .pipe(map(data => {
        return data;
      }));
  }

  getAllUser() {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.GET_All_USER}`)
      .pipe(map(data => {
        return data;
      }));
  }


  getAllUserByAdmin(payload) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.GET_ALL_USER_BY_ADMIN}`,payload)
      .pipe(map(data => {
        return data;
      }));
  }

  updatePaymentByAdmin(payload) {
    return this.http.post<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.UPDATE_USER_BY_ADMIN}`,payload)
      .pipe(map(data => {
        return data;
      }));
  }
}