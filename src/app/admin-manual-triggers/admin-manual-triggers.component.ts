import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-manual-triggers',
  templateUrl: './admin-manual-triggers.component.html',
  styleUrls: ['./admin-manual-triggers.component.css']
})
export class AdminManualTriggersComponent {
  selectedTab = 'commission';

  commissionForm: FormGroup;
  receiptForm: FormGroup;
  receiptFiles: File[] = [];

  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.commissionForm = this.fb.group({
      userId: ['', Validators.required],
      businessVolume: ['', Validators.required],
      type: ['REGISTRATION', Validators.required]
    });
    this.receiptForm = this.fb.group({
      userId: ['', Validators.required],
      transactionId: ['', Validators.required],
      amount: ['', Validators.required],
      paymentMode: ['UPI'],
      remarks: ['']
    });
  }

  switchTab(t: string) { this.selectedTab = t; this.successMessage = ''; this.errorMessage = ''; }

  triggerCommission() {
    if (this.commissionForm.invalid) { this.commissionForm.markAllAsTouched(); return; }
    const v = this.commissionForm.value;
    this.api.triggerCommission(Number(v.userId), Number(v.businessVolume), v.type).subscribe(
      (r: any) => {
        this.successMessage = r?.data || 'Commission distribution triggered.';
        this.toastr.success(this.successMessage, 'Triggered');
        this.commissionForm.reset({ type: 'REGISTRATION' });
      },
      err => {
        this.errorMessage = err?.error?.message || 'Trigger failed.';
        this.toastr.error(this.errorMessage, 'Trigger failed');
      }
    );
  }

  onReceiptFiles(event: any) {
    this.receiptFiles = Array.from(event.target.files || []);
  }

  uploadReceipt() {
    if (this.receiptForm.invalid) { this.receiptForm.markAllAsTouched(); return; }
    if (!this.receiptFiles.length) {
      this.errorMessage = 'Please select at least one receipt image.';
      this.toastr.warning(this.errorMessage, 'No file');
      return;
    }
    this.api.uploadPaymentReceipt(this.receiptForm.value, this.receiptFiles).subscribe(
      (r: any) => {
        this.successMessage = r?.data || 'Receipt uploaded.';
        this.toastr.success(this.successMessage, 'Uploaded');
        this.receiptForm.reset({ paymentMode: 'UPI' });
        this.receiptFiles = [];
      },
      err => {
        this.errorMessage = err?.error?.message || 'Upload failed.';
        this.toastr.error(this.errorMessage, 'Upload failed');
      }
    );
  }
}
