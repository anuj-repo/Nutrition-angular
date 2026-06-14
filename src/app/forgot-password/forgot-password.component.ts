import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

/**
 * Forgot-password is a 3-step wizard:
 *   1. user enters their registered email/username  -> /get-password-recovery-otp
 *   2. user enters the 6-digit OTP we emailed       -> /verify-password-recovery-otp
 *      -> backend returns a single-use reset token in `data`
 *   3. user enters a new password                    -> /reset-password
 *      -> body = { token, password, confirmPassword }   (matches ResetPasswordDto)
 *
 * The backend password regex requires 6-40 chars, at least one lowercase,
 * uppercase, digit, and one special character from #?!@$%^&*-_ — we mirror
 * that on the client so users see the rule before they hit submit.
 */
@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  step: 1 | 2 | 3 = 1;
  emailForm: FormGroup;
  otpForm: FormGroup;
  resetForm: FormGroup;
  successMessage = '';
  errorMessage = '';
  loading = false;

  // Single-use reset token returned by /verify-password-recovery-otp.
  // Required by /reset-password.
  private resetToken: string | null = null;

  // Mirrors the backend @Pattern on ResetPasswordDto.password.
  private static readonly PASSWORD_PATTERN =
    /^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_\-]).*$/;

  readonly passwordRulesHint =
    'Use 6 or more characters with a mix of upper & lower case, a number and a special character (#?!@$%^&*-_).';

  constructor(
    private fb: FormBuilder,
    private api: BackendApiService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.emailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
    this.otpForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      otp: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(8)]]
    });
    this.resetForm = this.fb.group({
      newPassword: ['', [Validators.required, Validators.pattern(ForgotPasswordComponent.PASSWORD_PATTERN)]],
      confirmPassword: ['', Validators.required]
    });
  }

  // -----------------------------------------------------------
  // Step 1 — request OTP
  // -----------------------------------------------------------
  sendOtp() {
    if (this.emailForm.invalid) {
      this.emailForm.markAllAsTouched();
      return;
    }
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const email: string = this.emailForm.value.email.trim();

    this.api.getPwdRecoveryOtp(email).subscribe(
      (res: any) => {
        this.loading = false;
        const msg = res?.message || 'OTP sent to your registered email.';
        this.successMessage = msg;
        this.toastr.success(msg, 'Check your inbox');

        this.otpForm.patchValue({ email, otp: '' });
        this.resetToken = null;
        this.step = 2;
      },
      err => {
        this.loading = false;
        this.errorMessage = this.extractError(err, 'Failed to send OTP.');
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  // -----------------------------------------------------------
  // Step 2 — verify OTP, capture reset token
  // -----------------------------------------------------------
  verifyOtp() {
    if (this.otpForm.invalid) {
      this.otpForm.markAllAsTouched();
      return;
    }
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.api.verifyPwdRecoveryOtp({
      email: this.otpForm.value.email,
      otp: String(this.otpForm.value.otp || '').trim()
    }).subscribe(
      (res: any) => {
        this.loading = false;
        // Backend returns the single-use reset token in `data`.
        const token = res?.data;
        if (!token) {
          this.errorMessage = 'Could not start password reset. Please try again.';
          this.toastr.error(this.errorMessage, 'Verification failed');
          return;
        }
        this.resetToken = token;
        this.successMessage = 'OTP verified.';
        this.toastr.success('OTP verified.', 'Success');
        this.resetForm.reset();
        this.step = 3;
      },
      err => {
        this.loading = false;
        this.errorMessage = this.extractError(err, 'Invalid OTP.');
        this.toastr.error(this.errorMessage, 'Verification failed');
      }
    );
  }

  // -----------------------------------------------------------
  // Step 3 — submit new password using the reset token
  // -----------------------------------------------------------
  resetPassword() {
    if (this.resetForm.invalid) {
      this.resetForm.markAllAsTouched();
      this.errorMessage = this.passwordRulesHint;
      return;
    }
    const { newPassword, confirmPassword } = this.resetForm.value;
    if (newPassword !== confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      this.toastr.error(this.errorMessage, 'Check passwords');
      return;
    }
    if (!this.resetToken) {
      this.errorMessage = 'Reset session expired. Please start over.';
      this.toastr.error(this.errorMessage, 'Session expired');
      this.step = 1;
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    // Backend ResetPasswordDto expects { token, password, confirmPassword }.
    const payload = {
      token: this.resetToken,
      password: newPassword,
      confirmPassword: confirmPassword
    };

    this.api.resetPassword(payload).subscribe(
      (res: any) => {
        this.loading = false;
        const msg = res?.message || 'Password reset successfully. Please log in.';
        this.toastr.success(msg, 'Success');
        this.resetToken = null;
        this.router.navigate(['/login']);
      },
      err => {
        this.loading = false;
        this.errorMessage = this.extractError(err, 'Reset failed.');
        this.toastr.error(this.errorMessage, 'Reset failed');
      }
    );
  }

  // -----------------------------------------------------------
  // Helpers
  // -----------------------------------------------------------
  goBackToEmail() {
    this.step = 1;
    this.errorMessage = '';
    this.successMessage = '';
    this.resetToken = null;
  }

  /** Pulls the most informative message off a server error. */
  private extractError(err: any, fallback: string): string {
    if (!err) return fallback;
    if (typeof err === 'string') return err;
    const e = err.error;
    if (e) {
      if (typeof e === 'string') return e;
      if (e.message) return e.message;
      if (e.fieldError) {
        const first = Object.values(e.fieldError)[0];
        if (first) return String(first);
      }
    }
    return err.message || fallback;
  }
}
