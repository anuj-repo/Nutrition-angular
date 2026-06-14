import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-pincode-check',
  templateUrl: './pincode-check.component.html',
  styleUrls: ['./pincode-check.component.css']
})
export class PincodeCheckComponent {
  form: FormGroup;
  result: any = null;
  errorMessage = '';
  loading = false;

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      pincode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]]
    });
  }

  check() {
    if (this.form.invalid) return;
    this.loading = true;
    this.errorMessage = '';
    this.result = null;
    this.api.pincodeServiceability(this.form.value.pincode).subscribe(
      (r: any) => {
        this.loading = false;
        this.result = r?.data;
        const serviceable = this.result?.serviceable ?? this.result?.deliverable ?? this.result?.available;
        if (serviceable === false) {
          this.toastr.warning('Sorry, we do not deliver to this pincode yet.', 'Not serviceable');
        } else {
          this.toastr.success('Delivery available at this pincode.', 'Serviceable');
        }
      },
      err => {
        this.loading = false;
        this.errorMessage = err?.error?.message || 'Check failed.';
        this.toastr.error(this.errorMessage, 'Check failed');
      }
    );
  }
}
