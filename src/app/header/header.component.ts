import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { UtililtyFunctions } from '../utils/utils';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  userName: any = '';
  mobileNavOpen = false;

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    public userService: UserService,
    public utils: UtililtyFunctions
  ) {}

  ngOnInit(): void {
    // Auto-close the mobile nav whenever the route changes.
    this.router.events
      .pipe(filter((e) => e instanceof NavigationEnd))
      .subscribe(() => this.closeMobileNav());
  }

  public isLoggedIn(): boolean {
    return !!this.utils.getUserMeData();
  }

  public logout(): void {
    this.userAuthService.clear();
    this.router.navigate(['/']);
  }

  public isAdmin(): boolean {
    const loggedUserData = this.utils.getUserMeData();
    this.userName = loggedUserData?.fname;
    return loggedUserData?.role === 'Admin';
  }

  public toggleMobileNav(): void {
    this.mobileNavOpen = !this.mobileNavOpen;
  }

  public closeMobileNav(): void {
    this.mobileNavOpen = false;
  }
}
