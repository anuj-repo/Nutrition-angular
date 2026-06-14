import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {
  summary: any = { balance: 0, totalEarned: 0, totalWithdrawn: 0, totalTdsDeducted: 0 };
  transactions: any[] = [];
  withdrawals: any[] = [];
  withdrawForm: FormGroup;
  selectedTab = 'overview';
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.withdrawForm = this.fb.group({
      amount: ['', [Validators.required, Validators.min(100)]],
      remarks: ['']
    });
  }

  ngOnInit(): void { this.loadAll(); }

  loadAll() {
    forkJoin({
      bal: this.api.walletBalance().pipe(catchError(() => of(null as any))),
      txns: this.api.walletTxns(0, 50).pipe(catchError(() => of(null as any))),
      wds: this.api.myWithdrawals().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.summary = res.bal?.data || this.summary;
      this.transactions = res.txns?.data || [];
      this.withdrawals = res.wds?.data || [];
    });
  }

  switchTab(t: string) { this.selectedTab = t; }

  submitWithdraw() {
    if (this.withdrawForm.invalid) { this.withdrawForm.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    this.api.withdraw(this.withdrawForm.value).subscribe(
      () => {
        this.successMessage = 'Withdrawal request submitted!';
        this.toastr.success('Withdrawal request submitted!', 'Success');
        this.withdrawForm.reset();
        this.loadAll();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Withdrawal failed.';
        this.toastr.error(this.errorMessage, 'Withdrawal failed');
      }
    );
  }
}
