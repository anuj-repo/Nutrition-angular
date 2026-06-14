import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-otp-verify',
  templateUrl: './otp-verify.component.html',
  styleUrls: ['./otp-verify.component.css']
})
export class OtpVerifyComponent {
  form: FormGroup;
  channel = 'EMAIL';
  purpose = 'LOGIN';
  resendCooldown = 0;
  message = '';
  error = '';

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      identifier: ['', Validators.required],
      otp: ['', Validators.required]
    });
    this.purpose = this.route.snapshot.queryParamMap.get('purpose') || 'LOGIN';
    this.channel = this.route.snapshot.queryParamMap.get('channel') || 'EMAIL';
    const id = this.route.snapshot.queryParamMap.get('id');
    if (id) this.form.patchValue({ identifier: id });
  }

  send() {
    if (!this.form.value.identifier) return;
    this.message = ''; this.error = '';
    this.api.otpSend({ identifier: this.form.value.identifier, channel: this.channel, purpose: this.purpose }).subscribe(
      () => {
        this.message = 'OTP sent.';
        this.toastr.success('OTP sent.', 'Check your inbox');
        this.startCooldown();
      },
      err => {
        this.error = err?.error?.message || 'Failed to send OTP.';
        this.toastr.error(this.error, 'Failed');
      }
    );
  }

  resend() {
    this.api.otpResend({ identifier: this.form.value.identifier, channel: this.channel, purpose: this.purpose }).subscribe(
      () => {
        this.message = 'OTP resent.';
        this.toastr.success('OTP resent.', 'Check your inbox');
        this.startCooldown();
      },
      err => this.toastr.error(err?.error?.message || 'Could not resend OTP.', 'Failed')
    );
  }

  verify() {
    if (this.form.invalid) return;
    this.message = ''; this.error = '';
    this.api.otpVerify({ identifier: this.form.value.identifier, otp: this.form.value.otp, purpose: this.purpose }).subscribe(
      () => {
        this.message = 'OTP verified successfully.';
        this.toastr.success('OTP verified successfully.', 'Success');
      },
      err => {
        this.error = err?.error?.message || 'Verification failed.';
        this.toastr.error(this.error, 'Verification failed');
      }
    );
  }

  private startCooldown() {
    this.resendCooldown = 30;
    const t = setInterval(() => {
      this.resendCooldown--;
      if (this.resendCooldown <= 0) clearInterval(t);
    }, 1000);
  }
}
