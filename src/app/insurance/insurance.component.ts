import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-insurance',
  templateUrl: './insurance.component.html',
  styleUrls: ['./insurance.component.css']
})
export class InsuranceComponent implements OnInit {
  plans: any[] = [];
  myInsurance: any[] = [];
  myClaims: any[] = [];
  eligibility: any = {};

  selectedTab = 'plans';
  claimForm: FormGroup;
  nomineeForm: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.claimForm = this.fb.group({
      userInsuranceId: ['', Validators.required],
      claimedAmount: ['', [Validators.required, Validators.min(1)]],
      description: ['', Validators.required],
      incidentDate: ['', Validators.required],
      supportingDocs: ['']
    });
    this.nomineeForm = this.fb.group({
      name: ['', Validators.required],
      relationship: ['', Validators.required],
      dob: [''],
      mobile: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      addressLine: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      pincode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      sharePercent: [100, [Validators.required, Validators.min(1), Validators.max(100)]]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    forkJoin({
      plans: this.api.insPlans().pipe(catchError(() => of(null as any))),
      mine: this.api.myInsurance().pipe(catchError(() => of(null as any))),
      claims: this.api.myInsuranceClaims().pipe(catchError(() => of(null as any))),
      elig: this.api.insEligibility().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.plans = res.plans?.data || [];
      this.myInsurance = res.mine?.data || [];
      this.myClaims = res.claims?.data || [];
      this.eligibility = res.elig?.data || {};
    });
  }

  switchTab(t: string) { this.selectedTab = t; this.successMessage = ''; this.errorMessage = ''; }

  autoEnroll() {
    this.api.insAutoEnroll().subscribe(
      () => {
        this.successMessage = 'Auto-enrolled successfully!';
        this.toastr.success('Auto-enrolled successfully!', 'Success');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Auto-enroll failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  submitClaim() {
    if (this.claimForm.invalid) { this.claimForm.markAllAsTouched(); return; }
    this.api.insClaim(this.claimForm.value).subscribe(
      () => {
        this.successMessage = 'Claim filed successfully.';
        this.toastr.success('Claim filed successfully.', 'Success');
        this.claimForm.reset();
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Claim filing failed.';
        this.toastr.error(this.errorMessage, 'Filing failed');
      }
    );
  }

  saveNominee() {
    if (this.nomineeForm.invalid) { this.nomineeForm.markAllAsTouched(); return; }
    this.api.saveNominee(this.nomineeForm.value).subscribe(
      () => {
        this.successMessage = 'Nominee saved.';
        this.toastr.success('Nominee saved.', 'Success');
      },
      err => {
        this.errorMessage = err?.error?.message || 'Save failed.';
        this.toastr.error(this.errorMessage, 'Save failed');
      }
    );
  }
}
