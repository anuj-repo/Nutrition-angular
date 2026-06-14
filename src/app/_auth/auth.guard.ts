import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { UtililtyFunctions } from '../utils/utils';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private userService: UserService,
    private utils: UtililtyFunctions
  ) { }

  // canActivate(
  //   route: ActivatedRouteSnapshot,
  //   state: RouterStateSnapshot
  // ):
  //   | Observable<boolean | UrlTree>
  //   | Promise<boolean | UrlTree>
  //   | boolean
  //   | UrlTree {
  //   if (this.userAuthService.getToken() !== null) {
  //     const role = route.data['roles'] as Array<string>;

  //     if (role) {
  //       const match = this.userService.roleMatch(role);

  //       if (match) {
  //         return true;
  //       } 
  //       // else {
  //       //   this.router.navigate(['/forbidden']);
  //       //   return false;
  //       // }
  //     }
  //   }

  //   //this.router.navigate(['/login']);
  //   return true;
  // }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let userToken = this.utils.getUserTokenData();
    let userData = this.utils.getUserMeData();
    if (userToken && userToken.accessToken) {
        return true;
    }
    this.router.navigateByUrl("/");
    return false;
}

}
