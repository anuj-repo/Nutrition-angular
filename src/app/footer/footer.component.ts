import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  year = new Date().getFullYear();
  newsletterForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.newsletterForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  subscribe() {
    if (this.newsletterForm.invalid) return;
    this.successMessage = ''; this.errorMessage = '';
    this.api.newsletterSubscribe(this.newsletterForm.value).subscribe(
      () => {
        this.successMessage = '✓ Subscribed!';
        this.toastr.success('Subscribed to newsletter!', 'Success');
        this.newsletterForm.reset();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Subscribe failed';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }
}
