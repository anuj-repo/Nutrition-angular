import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

/**
 * Backend `UserAddress` model fields:
 *   address (single line), city, state, country, pincode,
 *   isDefault (BooleanEnum -> JSON "0" / "1"),
 *   addressType (enum, only "billing" today),
 *   isOtherCity (BooleanEnum)
 *
 * The form mirrors that shape exactly so values round-trip correctly.
 */
@Component({
  selector: 'app-addresses',
  templateUrl: './addresses.component.html',
  styleUrls: ['./addresses.component.css']
})
export class AddressesComponent implements OnInit {
  addresses: any[] = [];
  form: FormGroup;
  editingId: number | null = null;
  showForm = false;
  loading = false;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      address: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      country: ['India', Validators.required],
      pincode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      isDefault: [false]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    this.loading = true;
    this.api.addresses().subscribe(
      (res: any) => {
        this.loading = false;
        this.addresses = res?.data || [];
      },
      () => {
        this.loading = false;
        this.addresses = [];
      }
    );
  }

  /** Treat backend BooleanEnum ("1"/"0") and JS booleans as the same thing. */
  isDefaultAddr(a: any): boolean {
    return a?.isDefault === '1' || a?.isDefault === 1 || a?.isDefault === true || a?.isDefault === 'YES';
  }

  newAddress() {
    this.showForm = true;
    this.editingId = null;
    this.successMessage = ''; this.errorMessage = '';
    this.form.reset({ address: '', city: '', state: '', country: 'India', pincode: '', isDefault: false });
  }

  edit(a: any) {
    this.showForm = true;
    this.editingId = a.id;
    this.successMessage = ''; this.errorMessage = '';
    this.form.patchValue({
      address: a.address || '',
      city: a.city || '',
      state: a.state || '',
      country: a.country || 'India',
      pincode: a.pincode || '',
      isDefault: this.isDefaultAddr(a)
    });
  }

  cancel() {
    this.showForm = false;
    this.editingId = null;
    this.successMessage = ''; this.errorMessage = '';
  }

  save() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    const v = this.form.value;
    // Map to backend model — BooleanEnum is serialized as "1"/"0", and the
    // only AddressType the backend accepts today is "billing".
    const payload: any = {
      address: (v.address || '').trim(),
      city: (v.city || '').trim(),
      state: (v.state || '').trim(),
      country: (v.country || 'India').trim(),
      pincode: (v.pincode || '').trim(),
      isDefault: v.isDefault ? '1' : '0',
      addressType: 'billing'
    };

    const handler = this.editingId
      ? this.api.updateAddress(this.editingId, payload)
      : this.api.addAddress(payload);

    handler.subscribe(
      () => {
        this.successMessage = this.editingId ? 'Address updated' : 'Address added';
        this.toastr.success(this.successMessage, 'Success');
        this.showForm = false;
        this.editingId = null;
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Save failed.';
        this.toastr.error(this.errorMessage, 'Save failed');
      }
    );
  }

  remove(id: number) {
    if (!confirm('Delete this address?')) return;
    this.api.deleteAddress(id).subscribe(
      () => {
        this.toastr.success('Address removed.', 'Deleted');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not delete address.', 'Failed')
    );
  }
}
