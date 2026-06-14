import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { NetworkService } from '../_services/network.service';
import { UserService } from '../_services/user.service';
import { BackendApiService } from '../_services/backend-api.service';
import { UtililtyFunctions } from '../utils/utils';

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent implements OnInit {

  memberForm: FormGroup;
  countries: any[] = [];
  states: any[] = [];
  cities: any[] = [];
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';
  myReferralCode = '';
  /** Referral code that will actually be sent with the new member. Defaults to myReferralCode but the sponsor can override. */
  referralCode = '';
  /** Toggles the inline edit view for the referral code. */
  isEditingReferral = false;
  /** Working buffer used while editing the referral code. */
  referralEditDraft = '';
  /** Sponsor lookup state for a custom (non-self) referral code. */
  sponsorValid: boolean | null = null;
  sponsorName = '';
  sponsorEmail = '';
  sponsorError = '';
  sponsorChecking = false;
  selectedCountryId: number | null = null;

  /** Custom validator: DOB must be at least `minAge` years ago. */
  private minAgeValidator(minAge: number) {
    return (control: any) => {
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

  /** Latest selectable DOB (today). Prevents future-dated DOBs in the picker. */
  maxDob: Date = new Date();

  /** Hide the payment options block until the sponsor needs it. */
  showPaymentOptions = false;

  /** KYC document files captured from the upload tiles. */
  kycFiles: { [key: string]: { name: string; type: string; data: string } | null } = {
    PAN: null,
    AADHAAR: null,
    BANK_PROOF: null
  };
  kycFileError = '';
  private readonly MAX_KYC_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

  // Backend uses PackageTakenEnum @JsonValue numeric strings
  packages = [
    { value: '2280', label: 'Stamina Core Plus - ₹2,280 (120 Capsules / 60 Days)' },
    { value: '5000', label: '₹5,000 Package' },
    { value: '10000', label: '₹10,000 Package' },
    { value: '15000', label: '₹15,000 Package' },
    { value: '20000', label: '₹20,000 Package' },
    { value: '25000', label: '₹25,000 Package' }
  ];

  // Backend uses PackageChoiceEnum @JsonValue lowercase strings
  productChoices = [
    { value: 'wellness', label: 'Wellness Products' },
    { value: 'home', label: 'Household and Personal Care Products' },
    { value: 'aggriculture', label: 'Aggriculture Products' }
  ];

  constructor(
    private fb: FormBuilder,
    private networkService: NetworkService,
    private userService: UserService,
    private api: BackendApiService,
    private utils: UtililtyFunctions,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.memberForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: [
        '',
        [Validators.required, Validators.email],
        [this.uniqueEmailValidator()] // async — runs after sync validators pass
      ],
      mobNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      gender: ['Male', Validators.required],
      dob: ['', [Validators.required, this.minAgeValidator(10)]],
      address: ['', Validators.required],
      country: ['', Validators.required],
      state: ['', Validators.required],
      city: ['', Validators.required],
      zipCode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      packageTaken: ['2280', Validators.required],
      productChoice: ['wellness', Validators.required],
      utrNumber: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(30),
        Validators.pattern('^[A-Za-z0-9]+$')
      ]],
      // KYC text fields
      panNumber: ['', [
        Validators.required,
        Validators.pattern('^[A-Z]{5}[0-9]{4}[A-Z]$')
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
        Validators.pattern('^[A-Z]{4}0[A-Z0-9]{6}$')
      ]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    });
  }

  /** Convenience getter for the template */
  get emailCtrl(): AbstractControl { return this.memberForm.get('email'); }

  /**
   * Debounced async validator that hits /api/emailUniqueness
   * and flags `{ emailTaken: true }` when the email already exists.
   * Network errors fall back to "valid" so the user is never blocked
   * by transient backend issues.
   */
  uniqueEmailValidator(): AsyncValidatorFn {
    return (control: AbstractControl) => {
      const value = (control.value || '').toString().trim().toLowerCase();
      if (!value) return of(null);

      return of(value).pipe(
        debounceTime(400),
        distinctUntilChanged(),
        switchMap(email =>
          this.userService.isEmailExist(email).pipe(
            map((exists: any) => {
              const taken =
                exists === true ||
                exists?.data === true ||
                /already.*exist|already.*registered|taken/i.test(exists?.message || '');
              if (taken) {
                // Toast once per check so the user notices on blur.
                this.toastr.error(
                  `${email} is already registered. Try a different email.`,
                  'Email already exists',
                  { timeOut: 4000, closeButton: true }
                );
                return { emailTaken: true };
              }
              return null;
            }),
            catchError(() => of(null)) // ignore transient errors
          )
        )
      );
    };
  }

  ngOnInit(): void {
    const userData = this.utils.getUserMeData();
    this.myReferralCode = userData?.referralCode || '';
    this.referralCode = this.myReferralCode;
    this.loadCountries();
  }

  /** Open the inline editor for the referral code. */
  startEditReferral() {
    this.referralEditDraft = this.referralCode || '';
    this.isEditingReferral = true;
  }

  /** Cancel editing without changing the referral code. */
  cancelEditReferral() {
    this.isEditingReferral = false;
    this.referralEditDraft = '';
  }

  /** Apply the edited referral code after light validation. */
  saveReferral() {
    const next = (this.referralEditDraft || '').trim().toUpperCase();
    if (!next) {
      this.toastr.warning('Referral code cannot be empty.', 'Invalid code');
      return;
    }
    if (!/^[A-Z0-9]{4,20}$/.test(next)) {
      this.toastr.warning('Use 4-20 letters or digits only.', 'Invalid code');
      return;
    }
    this.referralCode = next;
    this.isEditingReferral = false;
    if (next !== this.myReferralCode) {
      this.lookupSponsor(next);
    } else {
      this.clearSponsorState();
    }
  }

  /** Restore the sponsor's own referral code. */
  resetReferral() {
    this.referralCode = this.myReferralCode;
    this.referralEditDraft = '';
    this.isEditingReferral = false;
    this.clearSponsorState();
  }

  /**
   * Hit the public sponsor-validation endpoint and surface the matched
   * person's name (when the backend returns it) so the user can confirm
   * they're routing the new member under the right sponsor.
   */
  private lookupSponsor(code: string) {
    this.clearSponsorState();
    if (!code) return;
    this.sponsorChecking = true;
    this.api.validateSponsor(code).subscribe(
      (res: any) => {
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
          const msg = (res?.message || '').toLowerCase();
          valid = msg.includes('valid sponsor') && !msg.includes('invalid');
        }

        this.sponsorChecking = false;
        this.sponsorValid = valid;
        this.sponsorName = valid ? name : '';
        this.sponsorEmail = valid ? email : '';

        if (!valid) {
          this.toastr.warning(
            `${code} doesn't match any sponsor. Double-check the code.`,
            'Invalid referral code'
          );
        }
      },
      (err) => {
        // Don't block the flow on a transient network/server error — the
        // backend re-validates during signup.
        this.sponsorChecking = false;
        this.sponsorValid = null;
        this.sponsorName = '';
        this.sponsorEmail = '';
        this.sponsorError = 'Could not verify referral code right now. You can still continue.';
        console.warn('Sponsor validation failed:', err);
      }
    );
  }

  private clearSponsorState() {
    this.sponsorValid = null;
    this.sponsorName = '';
    this.sponsorEmail = '';
    this.sponsorError = '';
    this.sponsorChecking = false;
  }

  loadCountries() {
    this.userService.getCountries().subscribe(
      (response: any) => {
        const data = response?.data || [];
        this.countries = data
          .filter((c: any) => c.countryName === 'India' || data.length === 1)
          .map((c: any) => ({ id: c.id, countryName: c.countryName }));
        if (this.countries.length === 0) {
          this.countries = data.map((c: any) => ({ id: c.id, countryName: c.countryName }));
        }
      },
      err => console.log('Error loading countries', err)
    );
  }

  onCountryChange(event: any) {
    const countryId = event.value;
    this.selectedCountryId = countryId;
    this.states = [];
    this.cities = [];
    this.memberForm.patchValue({ state: '', city: '' });

    if (countryId) {
      // existing UserService.getStates uses /state with HttpParams - mimic register.component
      this.userService.getStates({ smartSearch: '', publicUse: '1' } as any).subscribe(
        (response: any) => {
          const data = response?.data || [];
          this.states = data.map((s: any) => ({ id: s.id, stateName: s.stateName }));
        },
        err => console.log('Error loading states', err)
      );
    }
  }

  onStateChange(event: any) {
    const stateId = event.value;
    this.cities = [];
    this.memberForm.patchValue({ city: '' });

    const countryId = this.memberForm.get('country').value;
    if (stateId && countryId) {
      this.userService.getCity({ countryId: Number(countryId), stateId: Number(stateId) }).subscribe(
        (response: any) => {
          const data = response?.data || [];
          this.cities = data.map((c: any) => ({ id: c.id, cityName: c.cityName }));
        },
        err => console.log('Error loading cities', err)
      );
    }
  }

  onSubmit() {
    // If the sponsor typed a new referral code but didn't click Save,
    // commit the draft now so it actually goes out with the request
    // instead of silently falling back to the sponsor's own code.
    if (this.isEditingReferral) {
      const draft = (this.referralEditDraft || '').trim().toUpperCase();
      if (draft && /^[A-Z0-9]{4,20}$/.test(draft)) {
        this.referralCode = draft;
        this.isEditingReferral = false;
        if (draft !== this.myReferralCode) {
          this.lookupSponsor(draft);
        } else {
          this.clearSponsorState();
        }
      } else if (draft) {
        // Draft is non-empty but malformed — block the submission so the
        // user can correct it instead of sending the auto-applied code.
        this.toastr.warning(
          'Referral code must be 4-20 letters or digits. Save or clear it before submitting.',
          'Invalid referral code'
        );
        return;
      } else {
        // Empty draft → just exit edit mode and keep the existing code.
        this.isEditingReferral = false;
      }
    }

    // Block submission while the async email check is still running
    if (this.memberForm.pending) {
      this.toastr.info('Hold on while we verify the email...', 'Checking');
      return;
    }

    if (this.memberForm.invalid) {
      this.memberForm.markAllAsTouched();
      if (this.emailCtrl.hasError('emailTaken')) {
        this.toastr.error(
          `${this.emailCtrl.value} is already registered. Try a different email.`,
          'Email already exists'
        );
      } else if (this.memberForm.get('utrNumber')?.invalid) {
        this.toastr.error(
          'Enter the UTR / Transaction ID from the bank receipt.',
          'Payment proof required',
          { timeOut: 5000 }
        );
      } else if (this.hasInvalidKycField()) {
        this.toastr.error(
          'PAN, Aadhaar, account number and IFSC are all required.',
          'KYC details missing',
          { timeOut: 5000 }
        );
      } else {
        this.toastr.warning('Please fill in all required fields correctly.', 'Form incomplete');
      }
      return;
    }

    if (this.memberForm.value.password !== this.memberForm.value.confirmPassword) {
      this.errorMessage = 'Passwords do not match!';
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

    // Reject DOBs that are in the future
    const dobValue = this.memberForm.value.dob;
    if (dobValue) {
      const dobDate = (dobValue instanceof Date) ? dobValue : new Date(dobValue);
      if (!isNaN(dobDate.getTime()) && dobDate.getTime() > Date.now()) {
        this.toastr.error('Date of birth cannot be in the future.', 'Invalid date');
        this.memberForm.get('dob').setErrors({ futureDate: true });
        return;
      }
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const formValue = this.memberForm.value;

    // Match backend SignUpRequest shape
    const payload: any = {
      firstName: (formValue.firstName || '').trim(),
      lastName: (formValue.lastName || '').trim(),
      email: (formValue.email || '').trim().toLowerCase(),
      mobNumber: (formValue.mobNumber || '').trim(),
      password: formValue.password,
      confirmPassword: formValue.confirmPassword,
      dob: this.formatDob(formValue.dob),
      gender: formValue.gender,
      address: (formValue.address || '').trim(),
      city: Number(formValue.city),
      state: Number(formValue.state),
      zipCode: (formValue.zipCode || '').trim(),
      packageTaken: formValue.packageTaken,
      productChoice: formValue.productChoice,
      utrNumber: (formValue.utrNumber || '').trim(),
      referralCode: this.referralCode || this.myReferralCode,

      // KYC text fields (normalized)
      panNumber: (formValue.panNumber || '').trim().toUpperCase(),
      aadhaarNumber: (formValue.aadhaarNumber || '').replace(/\D+/g, ''),
      accountHolderName: (formValue.accountHolderName || '').trim(),
      accountNumber: (formValue.accountNumber || '').replace(/\D+/g, ''),
      ifscCode: (formValue.ifscCode || '').trim().toUpperCase(),
      bankName: (formValue.bankName || '').trim(),

      // KYC document images (data URLs from onKycFile)
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

    this.networkService.addMember(payload).subscribe(
      (res: any) => {
        this.isSubmitting = false;
        const fullName = `${payload.firstName} ${payload.lastName}`.trim();
        const msg = res?.message || `${fullName} has been added to your team.`;
        this.successMessage = msg + ' Earned 20% commission on their Approval!';
        this.toastr.success(msg, '🎉 Member added', { timeOut: 4500, progressBar: true });
        this.memberForm.reset({
          gender: 'Male',
          packageTaken: '2280',
          productChoice: 'wellness'
        });
        // reset() flips all controls to invalid + pristine; explicitly mark
        // them untouched too so the red mat-error messages don't flash up
        // immediately after a successful submission.
        this.memberForm.markAsPristine();
        this.memberForm.markAsUntouched();
        Object.values(this.memberForm.controls)
          .forEach(c => c.markAsUntouched());
        this.kycFiles = { PAN: null, AADHAAR: null, BANK_PROOF: null };
        this.kycFileError = '';
        // Restore the sponsor's own referral code for the next member.
        this.referralCode = this.myReferralCode;
        this.isEditingReferral = false;
        this.referralEditDraft = '';
        this.clearSponsorState();
        // Auto-clear the green inline message after a short while so the
        // page returns to a clean state for the next member.
        setTimeout(() => { this.successMessage = ''; }, 6000);
      },
      err => {
        this.isSubmitting = false;
        const raw = err?.error?.message || err?.error?.fieldError;
        const friendly = this.normalizeError(raw, payload.email);
        this.errorMessage = friendly;

        if (/email/i.test(friendly) && /(exist|registered|taken|duplicate)/i.test(friendly)) {
          // Mark the email field invalid so the user can correct it.
          this.emailCtrl.setErrors({ emailTaken: true });
          this.toastr.error(friendly, 'Email already exists', { timeOut: 5000 });
        } else {
          this.toastr.error(friendly, 'Could not add member', { timeOut: 5000 });
        }
      }
    );
  }

  private normalizeError(raw: any, email: string): string {
    if (!raw) return 'Failed to add member. Please check the details and try again.';
    if (typeof raw === 'string') {
      if (/email.*exist/i.test(raw)) return `${email} is already registered.`;
      return raw;
    }
    // fieldError object: { email: "...", mobNumber: "..." }
    if (typeof raw === 'object') {
      const first = Object.keys(raw)[0];
      if (first) return `${first}: ${raw[first]}`;
    }
    return 'Failed to add member. Please check the details and try again.';
  }

  /** Copy a payment detail to clipboard with a friendly toast. */
  copyToClipboard(value: string, label: string) {
    if (!value) return;
    navigator.clipboard.writeText(value).then(
      () => this.toastr.success(value, `${label} copied`),
      () => this.toastr.error(`Could not copy ${label}.`, 'Failed')
    );
  }

  /** Read a chosen KYC file, validate type/size, and stash a base64 data URL. */
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

  private hasInvalidKycField(): boolean {
    return ['panNumber', 'aadhaarNumber', 'accountNumber', 'ifscCode']
      .some(f => this.memberForm.get(f)?.invalid);
  }

  /** Inline placeholder if the QR PNG asset is missing. */
  onQrLoadError(event: Event) {
    const img = event.target as HTMLImageElement;
    if (!img) return;
    img.src =
      'data:image/svg+xml;utf8,' + encodeURIComponent(
        '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" viewBox="0 0 200 200">' +
        '<rect width="100%" height="100%" fill="%23fff8e1"/>' +
        '<rect x="6" y="6" width="188" height="188" fill="none" stroke="%23ff9800" stroke-width="2" stroke-dasharray="6 6" rx="12"/>' +
        '<text x="50%" y="46%" font-family="Segoe UI, Arial, sans-serif" font-size="14" fill="%23bf360c" text-anchor="middle" font-weight="700">QR coming soon</text>' +
        '<text x="50%" y="58%" font-family="Segoe UI, Arial, sans-serif" font-size="11" fill="%235b6b7b" text-anchor="middle">Use UPI ID below</text>' +
        '</svg>'
      );
  }

  /**
   * Format the date picker value as an ISO Instant ("yyyy-MM-ddT00:00:00Z").
   * The backend uses Instant.parse(...) on the DOB field, so a bare "yyyy-MM-dd"
   * causes a 500 (DateTimeParseException). Building the instant at UTC midnight
   * keeps the calendar day stable across server time zones.
   */
  private formatDob(date: any): string {
    if (!date) return '';
    try {
      const d = (date instanceof Date) ? date : new Date(date);
      if (isNaN(d.getTime())) return '';
      const yyyy = d.getFullYear();
      const mm = String(d.getMonth() + 1).padStart(2, '0');
      const dd = String(d.getDate()).padStart(2, '0');
      return `${yyyy}-${mm}-${dd}T00:00:00Z`;
    } catch {
      return '';
    }
  }
}
