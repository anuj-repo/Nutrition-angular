import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { NetworkService } from '../_services/network.service';

@Component({
  selector: 'app-investment',
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.css']
})
export class InvestmentComponent implements OnInit {

  investmentForm: FormGroup;
  plans: any[] = [];
  myInvestments: any[] = [];
  calculatorAmount = 100000;
  calculatorRate = 10;
  calculatorMonths = 12;
  calculatorResult: any = null;

  isSubmitting = false;
  successMessage = '';
  errorMessage = '';
  selectedTab = 'calculator';

  // Screenshot for payment proof
  screenshotFile: { name: string; type: string; data: string } | null = null;

  // Quick reference table at 10% annual
  calculatorExamples = [
    { amount: 10000, annual: 1000, monthly: 83 },
    { amount: 25000, annual: 2500, monthly: 208 },
    { amount: 50000, annual: 5000, monthly: 417 },
    { amount: 100000, annual: 10000, monthly: 833 },
    { amount: 250000, annual: 25000, monthly: 2083 },
    { amount: 500000, annual: 50000, monthly: 4167 },
    { amount: 1000000, annual: 100000, monthly: 8333 }
  ];

  constructor(
    private fb: FormBuilder,
    private networkService: NetworkService,
    private toastr: ToastrService
  ) {
    this.investmentForm = this.fb.group({
      planId: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(10000)]],
      tenureMonths: [12, Validators.required],
      utrNumber: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30), Validators.pattern('^[A-Za-z0-9]+$')]]
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    forkJoin({
      plans: this.networkService.getInvestmentPlans().pipe(catchError(() => of(null))),
      mine: this.networkService.getMyInvestments().pipe(catchError(() => of(null)))
    }).subscribe(res => {
      this.plans = res.plans?.data || [];
      this.myInvestments = res.mine?.data || [];

      if (this.plans.length > 0 && !this.investmentForm.get('planId').value) {
        this.investmentForm.patchValue({ planId: this.plans[0].id });
      }
    });
  }

  // Live calculator using backend
  calculate() {
    if (!this.calculatorAmount || this.calculatorAmount < 1) return;
    this.networkService.calculateInvestment(
      this.calculatorAmount,
      this.calculatorRate,
      this.calculatorMonths
    ).pipe(catchError(() => of(null))).subscribe(res => {
      this.calculatorResult = res?.data || null;
    });
  }

  get calculatedAnnualReturn(): number {
    if (this.calculatorResult?.annualReturn != null) {
      return Number(this.calculatorResult.annualReturn);
    }
    return Math.round((this.calculatorAmount * this.calculatorRate) / 100);
  }

  get calculatedMonthlyReturn(): number {
    if (this.calculatorResult?.monthlyReturn != null) {
      return Number(this.calculatorResult.monthlyReturn);
    }
    return Math.round(this.calculatedAnnualReturn / 12);
  }

  get calculatedMaturity(): number {
    if (this.calculatorResult?.maturityAmount != null) {
      return Number(this.calculatorResult.maturityAmount);
    }
    return this.calculatorAmount + Math.round((this.calculatedAnnualReturn * this.calculatorMonths) / 12);
  }

  onInvest() {
    if (this.investmentForm.invalid) {
      this.investmentForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.networkService.subscribeInvestment({
      planId: Number(this.investmentForm.value.planId),
      amount: Number(this.investmentForm.value.amount),
      tenureMonths: Number(this.investmentForm.value.tenureMonths),
      utrNumber: (this.investmentForm.value.utrNumber || '').trim(),
      screenshotData: this.screenshotFile?.data || null,
      screenshotName: this.screenshotFile?.name || null,
      screenshotType: this.screenshotFile?.type || null
    }).subscribe(
      () => {
        this.isSubmitting = false;
        this.successMessage = 'Investment created successfully! Your payment will be verified by admin.';
        this.toastr.success('Investment created successfully!', '🎉 Invested');
        this.investmentForm.reset({ tenureMonths: 12 });
        if (this.plans.length > 0) {
          this.investmentForm.patchValue({ planId: this.plans[0].id });
        }
        this.screenshotFile = null;
        this.loadAll();
      },
      err => {
        this.isSubmitting = false;
        this.errorMessage = err?.error?.message || 'Failed to create investment.';
        this.toastr.error(this.errorMessage, 'Investment failed');
      }
    );
  }

  onScreenshotFile(input: HTMLInputElement) {
    const file = input.files && input.files[0];
    if (!file) return;
    const allowed = ['image/jpeg', 'image/png', 'image/webp', 'application/pdf'];
    if (!allowed.includes(file.type)) {
      this.toastr.warning('Only JPG, PNG, WEBP or PDF files are allowed.', 'Unsupported file');
      input.value = '';
      return;
    }
    if (file.size > 5 * 1024 * 1024) {
      this.toastr.warning('File is too large. Max 5 MB.', 'File too large');
      input.value = '';
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      this.screenshotFile = { name: file.name, type: file.type, data: String(reader.result || '') };
    };
    reader.readAsDataURL(file);
  }

  clearScreenshot() {
    this.screenshotFile = null;
  }

  getProgress(inv: any): number {
    if (!inv.startDate || !inv.maturityDate) return 0;
    const start = new Date(inv.startDate).getTime();
    const end = new Date(inv.maturityDate).getTime();
    const now = Date.now();
    if (now >= end) return 100;
    if (now <= start) return 0;
    return Math.round(((now - start) / (end - start)) * 100);
  }

  withdraw(id: number) {
    if (!confirm('Are you sure you want to withdraw this investment?')) return;
    this.networkService.withdrawInvestment(id).subscribe(
      () => {
        this.successMessage = 'Withdrawal initiated.';
        this.toastr.success('Withdrawal initiated.', 'Success');
        this.loadAll();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Withdrawal failed.';
        this.toastr.error(this.errorMessage, 'Withdrawal failed');
      }
    );
  }

  switchTab(tab: string) {
    this.selectedTab = tab;
  }
}
