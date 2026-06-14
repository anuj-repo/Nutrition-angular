import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, switchMap, filter, take } from 'rxjs/operators';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserAuthService } from '../_services/user-auth.service';
import { UtililtyFunctions } from '../utils/utils';
import { BackendApiService } from '../_services/backend-api.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  /** Set once we've decided the session is gone, so we don't toast/redirect repeatedly. */
  private loggingOut = false;

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private utils: UtililtyFunctions,
    private api: BackendApiService,
    private toastr: ToastrService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.utils.getUserTokenData();
    if (currentUser && currentUser.accessToken) {
      request = this.addToken(request, currentUser.accessToken);
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // Skip auth-redirect logic for public endpoints (signup, sponsor lookup,
        // pincode check, country/state/city, email uniqueness, password reset, etc.)
        // and for any unauthenticated request - there's no session to refresh.
        const url = request.url || '';
        const isPublicCall =
          /\/api\/public\//.test(url) ||
          /\/signUp$/.test(url) ||
          /\/emailUniqueness$/.test(url) ||
          /\/auth\/signin$/.test(url) ||
          /\/auth\/refresh-token$/.test(url) ||
          /\/country$|\/state$|\/city$/.test(url) ||
          /\/get-password-recovery-otp/.test(url) ||
          /\/verify-password-recovery-otp/.test(url) ||
          /\/reset-password/.test(url);

        if (error.status === 401 && !isPublicCall && currentUser) {
          // Try a silent refresh first if we still have a refresh token.
          if (currentUser.refreshToken) {
            return this.handle401(request, next);
          }
          // No refresh token - session is dead, kick out cleanly.
          this.forceLogout('Your session has expired. Please sign in again.');
          return throwError(error);
        }

        // 403s on authenticated calls also indicate the token is no longer
        // valid for this user (revoked / role change / disabled account).
        if (error.status === 403 && !isPublicCall && currentUser) {
          this.forceLogout('Your session is no longer valid. Please sign in again.');
          return throwError(error);
        }

        return throwError(error);
      })
    );
  }

  private handle401(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const tokenData = this.utils.getUserTokenData();
      return this.api.refreshToken({ refreshToken: tokenData?.refreshToken }).pipe(
        switchMap((res: any) => {
          this.isRefreshing = false;
          if (res?.data?.accessToken) {
            const merged = { ...tokenData, ...res.data };
            sessionStorage.setItem('userToken', JSON.stringify(merged));
            this.refreshTokenSubject.next(res.data.accessToken);
            return next.handle(this.addToken(request, res.data.accessToken));
          }
          // Backend returned 200 but no token - treat as failure.
          this.refreshTokenSubject.error('Refresh failed');
          this.refreshTokenSubject = new BehaviorSubject<any>(null);
          this.forceLogout('Your session has expired. Please sign in again.');
          return throwError('Unable to refresh token');
        }),
        catchError(err => {
          this.isRefreshing = false;
          // Unblock other queued requests by erroring the subject and
          // resetting it so future cycles start fresh.
          this.refreshTokenSubject.error(err);
          this.refreshTokenSubject = new BehaviorSubject<any>(null);
          this.forceLogout('Your session has expired. Please sign in again.');
          return throwError(err);
        })
      );
    }
    // Another request is already refreshing - queue this one until
    // the new token shows up.
    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token: string) => next.handle(this.addToken(request, token)))
    );
  }

  /**
   * Wipe all client-side auth state and route the user to /login. Idempotent:
   * if multiple in-flight requests trip 401 at the same time we only toast
   * and navigate once.
   */
  private forceLogout(message: string): void {
    if (this.loggingOut) return;
    this.loggingOut = true;
    try {
      this.userAuthService.clear();
    } catch {
      // best-effort - storage may already be gone
      try {
        sessionStorage.clear();
        localStorage.clear();
      } catch { /* ignore */ }
    }
    try {
      this.toastr.warning(message, 'Signed out', { timeOut: 4000 });
    } catch { /* toastr may not be ready in some edge cases */ }

    // Navigate after the current microtask so we don't fight Angular's
    // change detection while another HTTP error is being processed.
    setTimeout(() => {
      this.router.navigate(['/login']).finally(() => {
        // Allow future logouts (e.g. user logs back in then expires again).
        this.loggingOut = false;
      });
    }, 0);
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
}
