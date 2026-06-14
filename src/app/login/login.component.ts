import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { UtililtyFunctions } from '../utils/utils';
import { trigger, state, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('buttonAnimation', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('200ms ease-out', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('200ms ease-out', style({ opacity: 0 })),
      ]),
    ]),
  ],
})
export class LoginComponent implements OnInit {

  userRoles: any[] = ['User', 'Admin'];

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router,
    private utilityservice: UtililtyFunctions,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void { }

  login(loginForm: NgForm) {
    this.userService.login(loginForm.value).subscribe(
      (loginData: any) => {
        if (loginData && loginData.data && loginData.data.accessToken) {
          this.userService.getCurrentUserData().subscribe(
            (data) => {
              if (data && data.data) {
                if (this.userRoles.includes(data.data.role)) {
                  this.userAuthService.setRoles(data.data.role);
                  this.userAuthService.setToken(loginData.data.accessToken);
                  const role = data.data.role;
                  this.toastr.success(`Welcome back, ${data.data.fname || ''}!`.trim(), 'Logged in');
                  if (role === 'Admin') {
                    this.router.navigate(['/admin']);
                  } else {
                    this.router.navigate(['/dashboard']);
                  }
                } else if (data.data.role !== "User") {
                  this.userAuthService.setRoles(data.data.role);
                  this.userAuthService.setToken(loginData.data.accessToken);
                  this.toastr.success('Logged in.', 'Welcome');
                  this.router.navigateByUrl("/admin");
                } else {
                  this.toastr.error('Your account does not have access. Please contact support.', 'Access denied');
                  this.logoutUser();
                }
              }

            },
            (error) => { this.toastr.error('Invalid credentials. Please try again.', 'Login failed'); }
          );
        }
        else{
          this.toastr.error('Invalid credentials. Please try again.', 'Login failed');
        }
      },
      (error) => {
        this.toastr.error(error?.error?.message || 'Invalid credentials. Please try again.', 'Login failed');
      }
    );
  }

  registerUser() {
    this.router.navigate(['/register']);
  }

  logoutUser() {
    let datainput: any = {};
    this.userService.signOut(datainput).subscribe(
      (data) => {
        if (data && data.data == null) {
          this.utilityservice.onLoginSuccessfully.next(true);
          if (this.router.url == "/") {
            this.utilityservice.onLogoutSuccessfully.next(true);
          } else {
            this.router.navigate([""]);
          }
        }
      },
      (error) => { }
    );
  }

  focusInput(input: string) {
    const field = document.getElementById(input);
    if (field) {
      field.classList.add('focus');
    }
  }

  blurInput(input: string) {
    const field = document.getElementById(input);
    if (field) {
      field.classList.remove('focus');
    }
  }

  forgotPassword() {
    this.toastr.info('Redirecting to password reset...', 'Forgot password');
    this.router.navigate(['/forgot-password']);
  }
}
