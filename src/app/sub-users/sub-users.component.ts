import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-sub-users',
  templateUrl: './sub-users.component.html',
  styleUrls: ['./sub-users.component.css']
})
export class SubUsersComponent implements OnInit {
  subUsers: any[] = [];
  showForm = false;
  form: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      mobNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      role: ['SUB_USER', Validators.required]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    this.api.subUserDetail().subscribe(
      (r: any) => this.subUsers = Array.isArray(r?.data) ? r.data : (r?.data ? [r.data] : []),
      () => this.subUsers = []
    );
  }

  addSub() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.api.addSubUser(this.form.value).subscribe(
      () => {
        this.successMessage = 'Sub-user invited.';
        this.toastr.success('Sub-user invited.', 'Success');
        this.form.reset({ role: 'SUB_USER' });
        this.showForm = false;
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Add failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  resend(u: any) {
    this.api.resendOnboardingLink({ userId: u.id, email: u.email }).subscribe(
      () => {
        this.successMessage = 'Onboarding link resent.';
        this.toastr.success('Onboarding link resent.', 'Success');
      },
      err => {
        this.errorMessage = err?.error?.message || 'Resend failed.';
        this.toastr.error(this.errorMessage, 'Resend failed');
      }
    );
  }
}
