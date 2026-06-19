import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../_services/user.service';
import { BackendApiService } from '../_services/backend-api.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { trigger, style, transition, animate } from '@angular/animations';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(10px)' }),
        animate('500ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('buttonPress', [
      transition(':enter', [
        style({ transform: 'scale(0.95)' }),
        animate('200ms ease-out', style({ transform: 'scale(1)' }))
      ])
    ])
  ]
})
export class RegisterComponent implements OnInit {

  countries: { id: number; countryName: string }[] = [];
  states: { id: number; stateName: string }[] = [];
  cities: { id: number; cityName: string }[] = [];
  selectedCountryId: number | null = null;

  registerForm: FormGroup;
  submitting = false;
  successMessage = '';
  errorMessage = '';
  hidePassword = true;
  hideConfirmPassword = true;

  // Max date for DOB = today minus 10 years (minimum age)
  maxDob = new Date(new Date().getFullYear() - 10, new Date().getMonth(), new Date().getDate());

  /** Show / hide the payment-options block (QR + bank details). Off by default
   *  to keep the form short — user clicks "Show payment options" if they
   *  haven't paid yet. */
  showPaymentOptions = false;

  // Sponsor & pincode validation state
  sponsorValid: boolean | null = null;
  sponsorName = '';
  sponsorEmail = '';
  sponsorError = ''; // network/server error (kept separate from "invalid code")
  pincodeServiceable: boolean | null = null;

  // KYC file uploads (PAN, Aadhaar, Bank proof)
  kycFiles: { [key: string]: { name: string; type: string; data: string } | null } = {
    PAN: null,
    AADHAAR: null,
    BANK_PROOF: null
  };
  kycFileError = '';
  private readonly MAX_KYC_FILE_SIZE = 1 * 1024 * 1024; // 1 MB

  cityParams: any = { smartSearch: '', publicUse: '1' };

  constructor(
    private userService: UserService,
    private api: BackendApiService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email], [this.emailAsyncValidator(this.userService)]],
      mobNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      referralCode: [''],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      dob: ['', [Validators.required, this.minAgeValidator(10)]],
      gender: ['Male', Validators.required],
      // Kept in form for UX/segmentation but NOT sent to backend (backend SignUpRequest no longer has these)
      packageTaken: ['2280'],
      productChoice: ['home'],
      address: ['', [Validators.required, Validators.maxLength(200)]],
      country: ['', Validators.required],
      state: ['', Validators.required],
      city: ['', Validators.required],
      zipCode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      // UTR / Transaction ID — proof of payment, gates registration
      utrNumber: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30),
        Validators.pattern('^[A-Za-z0-9]+$')
      ]],
      // KYC text fields - mandatory at registration time
      panNumber: ['', [
        Validators.required,
        Validators.pattern('^[A-Za-z]{5}[0-9]{4}[A-Za-z]$')
      ]],
      aadhaarNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{12}$')
      ]],
      accountHolderName: [''],
      bankName: [''],
      accountNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{9,18}$')
      ]],
      ifscCode: ['', [
        Validators.required,
        Validators.pattern('^[A-Za-z]{4}0[A-Za-z0-9]{6}$')
      ]],
      // Not part of backend payload anymore - kept for UX only
      upiId: ['']
    });
  }

  ngOnInit(): void {
    this.getAllCountry();

    // Pick up referral from URL: /register?ref=ABC123 (also accept ?referral= or ?referralCode=)
    this.route.queryParamMap.subscribe(params => {
      const ref =
        params.get('ref') ||
        params.get('referral') ||
        params.get('referralCode');
      if (ref) {
        this.registerForm.patchValue({ referralCode: ref.trim() });
        this.validateSponsor();
      }
    });
  }

  register(form: FormGroup) {
    if (form.invalid) {
      form.markAllAsTouched();
      // Friendly call-out for the most commonly forgotten field.
      if (form.get('utrNumber')?.invalid) {
        this.errorMessage = 'Please enter the UTR / Transaction ID — payment proof is required to register.';
        this.toastr.error(
          'Enter the UTR / Transaction ID from your bank receipt.',
          'Payment proof required',
          { timeOut: 5000, closeButton: true }
        );
      } else if (this.hasInvalidKycField(form)) {
        this.errorMessage = 'Please complete the KYC section (PAN, Aadhaar and bank details).';
        this.toastr.error(
          'PAN, Aadhaar, account number and IFSC are all required.',
          'KYC details missing',
          { timeOut: 5000, closeButton: true }
        );
      } else {
        this.errorMessage = 'Please fix the highlighted fields.';
        this.toastr.warning('Please fix the highlighted fields.', 'Form incomplete');
      }
      return;
    }
    if (form.value.password !== form.value.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      this.toastr.error('Passwords do not match.', 'Check passwords');
      return;
    }

    // KYC documents are mandatory at registration time.
    if (!this.kycFiles.PAN || !this.kycFiles.AADHAAR || !this.kycFiles.BANK_PROOF) {
      this.kycFileError = 'Upload PAN, Aadhaar and a bank proof (passbook / cheque / statement).';
      this.toastr.error(this.kycFileError, 'KYC documents missing', { timeOut: 5000 });
      return;
    }
    this.kycFileError = '';

    this.submitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const v = form.value;

    // Build payload matching backend SignUpRequest exactly.
    // Backend SignUpRequest fields: firstName, lastName, referralCode, email,
    // mobNumber, password, confirmPassword, dob (ISO Instant string), gender,
    // address, city (Long), state (Long), zipCode + KYC bundle.
    const payload: any = {
      firstName: (v.firstName || '').trim(),
      lastName: (v.lastName || '').trim(),
      email: (v.email || '').trim().toLowerCase(),
      mobNumber: (v.mobNumber || '').trim(),
      password: v.password,
      confirmPassword: v.confirmPassword,
      gender: v.gender || 'Male',
      address: (v.address || '').trim(),
      city: v.city ? Number(v.city) : null,
      state: v.state ? Number(v.state) : null,
      zipCode: (v.zipCode || '').trim(),
      dob: this.toIsoInstant(v.dob),
      referralCode: (v.referralCode || '').trim(),
      utrNumber: (v.utrNumber || '').trim(),

      // KYC text fields (normalised)
      panNumber: (v.panNumber || '').trim().toUpperCase(),
      aadhaarNumber: (v.aadhaarNumber || '').replace(/\D+/g, ''),
      accountHolderName: (v.accountHolderName || '').trim(),
      accountNumber: (v.accountNumber || '').replace(/\D+/g, ''),
      ifscCode: (v.ifscCode || '').trim().toUpperCase(),
      bankName: (v.bankName || '').trim(),

      // KYC document images (data URLs base64-encoded by onKycFile)
      panImage: this.kycFiles.PAN.data,
      panImageName: this.kycFiles.PAN.name,
      panImageType: this.kycFiles.PAN.type,
      aadhaarImage: this.kycFiles.AADHAAR.data,
      aadhaarImageName: this.kycFiles.AADHAAR.name,
      aadhaarImageType: this.kycFiles.AADHAAR.type,
      bankProofImage: this.kycFiles.BANK_PROOF.data,
      bankProofImageName: this.kycFiles.BANK_PROOF.name,
      bankProofImageType: this.kycFiles.BANK_PROOF.type
    };

    // Sanity check before posting
    if (!payload.dob) {
      this.submitting = false;
      this.errorMessage = 'Please select a valid date of birth.';
      this.toastr.error('Please select a valid date of birth.', 'Invalid date');
      return;
    }
    if (!payload.city || !payload.state) {
      this.submitting = false;
      this.errorMessage = 'Please select your country, state and city.';
      this.toastr.error('Please select your country, state and city.', 'Location required');
      return;
    }

    this.userService.register(payload).subscribe(
      (res: any) => {
        this.submitting = false;
        const serverMsg = res?.message;
        const friendly = (serverMsg && !/already exist/i.test(serverMsg))
          ? serverMsg
          : '🎉 Registration successful! Welcome to Nutrition Forever Wellness.';

        this.successMessage =
          friendly + ' Please check your email and sign in. Redirecting to login...';
        this.errorMessage = '';

        // Toast popup so the user notices even on a long form
        this.toastr.success(
          'Welcome to Nutrition Forever Wellness! Redirecting you to login...',
          'Account created 🎉',
          { timeOut: 4000, closeButton: true, progressBar: true }
        );

        // Scroll the inline alert into view
        setTimeout(() => {
          const el = document.querySelector('.register-card .alert.success');
          if (el) (el as HTMLElement).scrollIntoView({ behavior: 'smooth', block: 'center' });
        }, 50);

        // Reset form and redirect
        form.reset({
          firstName: '',
          lastName: '',
          email: '',
          mobNumber: '',
          referralCode: '',
          password: '',
          confirmPassword: '',
          dob: '',
          gender: 'Male',
          packageTaken: '2280',
          productChoice: 'home',
          address: '',
          country: '',
          state: '',
          city: '',
          zipCode: '',
          utrNumber: '',
          panNumber: '',
          aadhaarNumber: '',
          accountHolderName: '',
          bankName: '',
          accountNumber: '',
          ifscCode: '',
          upiId: ''
        });
        // After reset() controls become invalid/pristine — mark every
        // control as untouched too so the red mat-error labels don't
        // flash up while the success banner is still on screen.
        Object.values(form.controls).forEach(c => {
          c.markAsUntouched();
          c.markAsPristine();
          c.setErrors(null);
        });
        // Clear the KYC upload state too so the next signup starts fresh.
        this.kycFiles = { PAN: null, AADHAAR: null, BANK_PROOF: null };
        this.kycFileError = '';
        // Clear async-validation visual state so the next form is clean.
        this.sponsorValid = null;
        this.sponsorName = '';
        this.sponsorEmail = '';
        this.sponsorError = '';
        this.pincodeServiceable = null;

        setTimeout(() => this.router.navigate(['/login']), 2500);
      },
      err => {
        this.submitting = false;
        const msg = this.extractError(err) || 'Registration failed. Please try again.';
        this.errorMessage = msg;
        this.successMessage = '';
        this.toastr.error(msg, 'Registration failed', { timeOut: 5000, closeButton: true });
      }
    );
  }

  /**
   * Read a chosen KYC file, validate type/size, and stash a base64 data URL
   * in the kycFiles map keyed by docType.
   */
  onKycFile(docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF', input: HTMLInputElement) {
    this.kycFileError = '';
    const file = input.files && input.files[0];
    if (!file) return;

    const allowed = ['image/jpeg', 'image/png', 'image/webp', 'application/pdf'];
    if (!allowed.includes(file.type)) {
      this.kycFileError = 'Only JPG, PNG, WEBP or PDF files are allowed.';
      this.toastr.warning(this.kycFileError, 'Unsupported file');
      input.value = '';
      return;
    }
    if (file.size > this.MAX_KYC_FILE_SIZE) {
      this.kycFileError = `File "${file.name}" is larger than 5 MB. Please pick a smaller file.`;
      this.toastr.warning(this.kycFileError, 'File too large');
      input.value = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.kycFiles[docType] = {
        name: file.name,
        type: file.type,
        data: String(reader.result || '')
      };
    };
    reader.onerror = () => {
      this.toastr.error('Could not read the selected file.', 'Upload failed');
      input.value = '';
    };
    reader.readAsDataURL(file);
  }

  clearKycFile(docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF') {
    this.kycFiles[docType] = null;
  }

  previewKycFile(docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF') {
    const file = this.kycFiles[docType];
    if (!file) return;
    // data is a base64 data URL — open it in new tab
    const win = window.open('', '_blank');
    if (win) {
      if (file.type === 'application/pdf') {
        win.document.write(`<iframe src="${file.data}" width="100%" height="100%" style="border:none;position:fixed;top:0;left:0;right:0;bottom:0;"></iframe>`);
      } else {
        win.document.write(`<img src="${file.data}" style="max-width:100%;height:auto;">`);
      }
    }
  }

  /** Copy a payment detail to clipboard with a friendly Toastr confirmation. */
  copyToClipboard(value: string, label: string) {
    if (!value) return;
    navigator.clipboard.writeText(value).then(
      () => this.toastr.success(value, `${label} copied`),
      () => this.toastr.error(`Could not copy ${label}.`, 'Failed')
    );
  }

  /**
   * If the QR PNG isn't bundled in the project (asset missing), swap it for
   * an inline SVG placeholder so the section still looks intentional rather
   * than showing a broken image icon.
   */
  onQrLoadError(event: Event) {
    const img = event.target as HTMLImageElement;
    if (!img) return;
    const placeholder =
      'data:image/svg+xml;utf8,' + encodeURIComponent(
        '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" viewBox="0 0 200 200">' +
        '<rect width="100%" height="100%" fill="%23fff8e1"/>' +
        '<rect x="6" y="6" width="188" height="188" fill="none" stroke="%23ff9800" stroke-width="2" stroke-dasharray="6 6" rx="12"/>' +
        '<text x="50%" y="46%" font-family="Segoe UI, Arial, sans-serif" font-size="14" fill="%23bf360c" text-anchor="middle" font-weight="700">QR coming soon</text>' +
        '<text x="50%" y="58%" font-family="Segoe UI, Arial, sans-serif" font-size="11" fill="%235b6b7b" text-anchor="middle">Use UPI ID below</text>' +
        '</svg>'
      );
    img.src = placeholder;
  }

  private hasInvalidKycField(form: FormGroup): boolean {
    return ['panNumber', 'aadhaarNumber', 'accountNumber', 'ifscCode']
      .some(f => form.get(f)?.invalid);
  }

  /**
   * Show a toast when a KYC field loses focus with a value that doesn't
   * match its expected format. Inline mat-errors are still shown — the toast
   * is an extra cue at the top-right so the user can't miss it.
   */
  onKycFieldBlur(field: 'panNumber' | 'aadhaarNumber' | 'accountNumber' | 'ifscCode'): void {
    // Disabled — inline mat-error is sufficient. Toast was too aggressive with browser autofill.
  }

  private toIsoInstant(d: any): string {
    if (!d) return '';
    try {
      const dt = (d instanceof Date) ? d : new Date(d);
      if (isNaN(dt.getTime())) return '';
      // Build a UTC instant at midnight of the selected calendar day, so the
      // backend's Instant.parse(...).atZone(systemDefault).toLocalDate() lands
      // on the same calendar date the user picked, regardless of the JVM zone.
      const y = dt.getFullYear();
      const m = (dt.getMonth() + 1).toString().padStart(2, '0');
      const day = dt.getDate().toString().padStart(2, '0');
      return `${y}-${m}-${day}T00:00:00Z`;
    } catch {
      return '';
    }
  }

  /** Custom validator: DOB must be at least `minAge` years ago. */
  private minAgeValidator(minAge: number) {
    return (control: AbstractControl) => {
      if (!control.value) return null;
      const dob = (control.value instanceof Date) ? control.value : new Date(control.value);
      if (isNaN(dob.getTime())) return null;
      const today = new Date();
      const cutoff = new Date(today.getFullYear() - minAge, today.getMonth(), today.getDate());
      if (dob > cutoff) {
        return { minAge: { requiredAge: minAge } };
      }
      return null;
    };
  }

  private extractError(err: any): string {
    if (!err) return '';
    if (typeof err.error === 'string') return err.error;
    if (err.error?.message) return err.error.message;
    if (err.error?.fieldError) {
      // { field: "message", ... }
      const fe = err.error.fieldError;
      const first = Object.keys(fe)[0];
      if (first) return `${first}: ${fe[first]}`;
    }
    if (err.message) return err.message;
    return '';
  }

  // Async email uniqueness validator
  emailAsyncValidator(userService: UserService): AsyncValidatorFn {
    return (control: AbstractControl) => {
      if (!control.value) return of(null);
      return userService.isEmailExist(control.value).pipe(
        map((exists: any) => (exists ? { emailTaken: true } : null)),
        catchError(() => of(null))
      );
    };
  }

  // Sponsor code validation (calls public endpoint)
  validateSponsor() {
    const raw = (this.registerForm.value.referralCode || '').toString();
    const code = raw.trim();
    this.sponsorError = '';

    // Normalize input back into the form (strip whitespace) so submission
    // sends the clean value.
    if (raw !== code) {
      this.registerForm.patchValue({ referralCode: code }, { emitEvent: false });
    }

    if (!code) {
      this.sponsorValid = null;
      this.sponsorName = '';
      this.sponsorEmail = '';
      return;
    }

    this.api.validateSponsor(code).subscribe(
      (res: any) => {
        // Backend may return either:
        //   { data: { valid: true, name: "...", email: "ma***@x.com" }, message: "Valid sponsor" }
        // or the legacy shape:
        //   { data: true, message: "Valid sponsor" }
        const data = res?.data;
        let valid: boolean;
        let name = '';
        let email = '';

        if (typeof data === 'boolean') {
          valid = data;
        } else if (data && typeof data === 'object') {
          valid = data.valid !== false;
          name = data.name || data.sponsorName || data.fname || '';
          email = data.email || '';
        } else {
          // Fall back to message string check
          valid = (res?.message || '').toLowerCase().includes('valid sponsor')
            && !(res?.message || '').toLowerCase().includes('invalid');
        }

        this.sponsorValid = valid;
        this.sponsorName = valid ? name : '';
        this.sponsorEmail = valid ? email : '';
      },
      (err) => {
        // Network / server error - do NOT mark the code invalid; let the
        // user submit and let the backend re-validate during signup.
        this.sponsorValid = null;
        this.sponsorName = '';
        this.sponsorEmail = '';
        this.sponsorError = 'Could not verify referral code right now. You can still continue.';
        console.warn('Sponsor validation failed:', err);
      }
    );
  }

  // Pincode serviceability check
  checkPincode() {
    const pin = (this.registerForm.value.zipCode || '').trim();
    if (!/^[0-9]{6}$/.test(pin)) { this.pincodeServiceable = null; return; }
    this.api.pincodeServiceability(pin).subscribe(
      (res: any) => {
        const data = res?.data;
        this.pincodeServiceable = data?.serviceable !== false;
      },
      () => { this.pincodeServiceable = null; }
    );
  }

  getAllCountry() {
    this.userService.getCountries().subscribe(
      (response: any) => {
        const all = response?.data || [];
        const india = all.filter((c: any) => c.countryName === 'India');
        const list = india.length ? india : all;
        this.countries = list.map((c: any) => ({ id: c.id, countryName: c.countryName }));
      },
      err => console.log('Error loading countries', err)
    );
  }

  onCountryChange(event: any) {
    const countryId = event.value;
    this.selectedCountryId = countryId;
    this.states = [];
    this.cities = [];
    this.registerForm.patchValue({ state: '', city: '' });
    if (countryId) this.getStates(countryId);
  }

  getStates(countryId: number) {
    this.userService.getStates(this.cityParams).subscribe(
      (response: any) => {
        const data = response?.data || [];
        this.states = data.map((s: any) => ({ id: s.id, stateName: s.stateName }));
      },
      err => console.error('Error fetching states:', err)
    );
  }

  onStateChange(event: any) {
    const stateId = event.value;
    this.cities = [];
    this.registerForm.patchValue({ city: '' });
    if (stateId) this.getCity(stateId);
  }

  getCity(stateId: number) {
    const params = {
      countryId: Number(this.selectedCountryId),
      stateId: Number(stateId)
    };
    this.userService.getCity(params).subscribe(
      (response: any) => {
        const data = response?.data || [];
        this.cities = data.map((c: any) => ({ id: c.id, cityName: c.cityName }));
      },
      err => console.error('Error fetching city:', err)
    );
  }

  onCityChange(event: any) { /* no-op */ }
}
