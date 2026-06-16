import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

interface ExpensePool {
  id?: number;
  code: string;
  name?: string;
  percentage: number;
  balance?: number;
  totalCredited?: number;
  totalSpent?: number;
  description?: string;
  isActive?: boolean;
}

interface PoolTxn {
  id?: number;
  txnType?: string;
  source?: string;
  amount?: number;
  balanceAfter?: number;
  reference?: string;
  description?: string;
  txnDate?: string;
  percentage?: number;
  businessVolume?: number;
}

@Component({
  selector: 'app-admin-expense-pools',
  templateUrl: './admin-expense-pools.component.html',
  styleUrls: ['./admin-expense-pools.component.css']
})
export class AdminExpensePoolsComponent implements OnInit {

  loading = false;
  pools: ExpensePool[] = [];

  newPoolForm: FormGroup;
  spendForm: FormGroup;

  selectedCode: string | null = null;
  ledger: PoolTxn[] = [];
  ledgerLoading = false;
  ledgerPage = 0;
  ledgerSize = 20;
  ledgerHasMore = true;

  // Quick payment form
  quickPool: string = '';
  quickAmount: number | null = null;
  quickUtr: string = '';
  quickDesc: string = '';

  constructor(
    private fb: FormBuilder,
    private api: BackendApiService,
    private toastr: ToastrService
  ) {
    this.newPoolForm = this.fb.group({
      code: ['', [Validators.required, Validators.pattern(/^[A-Za-z0-9_]{2,40}$/)]],
      name: ['', Validators.required],
      percentage: [0, [Validators.required, Validators.min(0), Validators.max(100)]],
      description: [''],
      isActive: [true]
    });

    this.spendForm = this.fb.group({
      amount: [null, [Validators.required, Validators.min(0.01)]],
      reference: [''],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.load();
  }

  // ----------------- Aggregate views -----------------

  get totalPercentage(): number {
    return (this.pools || [])
      .filter(p => p.isActive !== false)
      .reduce((s, p) => s + (Number(p.percentage) || 0), 0);
  }

  get totalBalance(): number {
    return (this.pools || []).reduce((s, p) => s + (Number(p.balance) || 0), 0);
  }

  get totalCredited(): number {
    return (this.pools || []).reduce((s, p) => s + (Number(p.totalCredited) || 0), 0);
  }

  get totalSpent(): number {
    return (this.pools || []).reduce((s, p) => s + (Number(p.totalSpent) || 0), 0);
  }

  /**
   * The L1..L15 commission share is fixed at 60% by design — surfaced here
   * so admins can see at a glance whether their pool % + commission % == 100.
   */
  readonly commissionShare = 60;

  get distributionTotal(): number {
    return this.totalPercentage + this.commissionShare;
  }

  get distributionWarning(): string | null {
    const total = this.distributionTotal;
    if (Math.abs(total - 100) < 0.01) return null;
    return `Active pools (${this.totalPercentage}%) + commission (${this.commissionShare}%) = ${total}%. Should be 100%.`;
  }

  // ----------------- Loading -----------------

  load(): void {
    this.loading = true;
    this.api.expensePools().subscribe(
      (r: any) => {
        this.pools = (r?.data || []).map((p: any) => ({ ...p }));
        this.loading = false;
        if (this.selectedCode) {
          // Refresh the open ledger when balances change.
          this.loadLedger(this.selectedCode, true);
        }
      },
      err => {
        this.loading = false;
        this.toastr.error(err?.error?.message || 'Could not load expense pools.', 'Error');
      }
    );
  }

  trackByCode(_i: number, p: ExpensePool): string {
    return p.code;
  }

  // ----------------- Quick Payment -----------------

  quickSpend(): void {
    if (!this.quickPool || !this.quickAmount || !this.quickUtr) {
      this.toastr.warning('Please fill all required fields.', 'Incomplete');
      return;
    }
    const desc = this.quickDesc
      ? `${this.quickDesc} | UTR: ${this.quickUtr}`
      : `UTR: ${this.quickUtr}`;

    if (!confirm(`Debit ₹${this.quickAmount} from ${this.quickPool}?\nUTR: ${this.quickUtr}`)) return;

    this.api.spendExpensePool(this.quickPool, Number(this.quickAmount), this.quickUtr, desc).subscribe(
      () => {
        this.toastr.success(`₹${this.quickAmount} debited from ${this.quickPool}. UTR: ${this.quickUtr}`, 'Payment Recorded');
        this.quickPool = '';
        this.quickAmount = null;
        this.quickUtr = '';
        this.quickDesc = '';
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Payment recording failed.', 'Failed')
    );
  }

  // ----------------- Per-row edits -----------------

  savePercentage(p: ExpensePool): void {
    const pct = Number(p.percentage);
    if (!isFinite(pct) || pct < 0) {
      this.toastr.warning('Enter a valid percentage.', 'Invalid');
      return;
    }
    this.api.updateExpensePoolPercentage(p.code, pct).subscribe(
      () => {
        this.toastr.success(`${p.code} percentage saved.`, 'Updated');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Update failed.', 'Failed')
    );
  }

  toggleActive(p: ExpensePool): void {
    const next = !(p.isActive !== false);
    this.api.setExpensePoolActive(p.code, next).subscribe(
      () => {
        this.toastr.success(`${p.code} is now ${next ? 'active' : 'inactive'}.`, 'Updated');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Toggle failed.', 'Failed')
    );
  }

  // ----------------- Create -----------------

  createPool(): void {
    if (this.newPoolForm.invalid) {
      this.newPoolForm.markAllAsTouched();
      return;
    }
    const body = this.newPoolForm.value;
    body.code = (body.code || '').toString().trim().toUpperCase();
    this.api.createExpensePool(body).subscribe(
      () => {
        this.toastr.success(`Pool ${body.code} created.`, 'Saved');
        this.newPoolForm.reset({ percentage: 0, isActive: true });
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Create failed.', 'Failed')
    );
  }

  // ----------------- Ledger -----------------

  openLedger(p: ExpensePool): void {
    if (this.selectedCode === p.code) {
      this.selectedCode = null;
      this.ledger = [];
      return;
    }
    this.selectedCode = p.code;
    this.spendForm.reset();
    this.loadLedger(p.code, true);
  }

  loadLedger(code: string, reset = false): void {
    if (reset) {
      this.ledger = [];
      this.ledgerPage = 0;
      this.ledgerHasMore = true;
    }
    this.ledgerLoading = true;
    this.api.expensePoolLedger(code, this.ledgerPage, this.ledgerSize).subscribe(
      (r: any) => {
        const rows: PoolTxn[] = r?.data || [];
        this.ledger = reset ? rows : this.ledger.concat(rows);
        this.ledgerHasMore = rows.length === this.ledgerSize;
        this.ledgerLoading = false;
      },
      err => {
        this.ledgerLoading = false;
        this.toastr.error(err?.error?.message || 'Could not load ledger.', 'Error');
      }
    );
  }

  loadMore(): void {
    if (!this.selectedCode || !this.ledgerHasMore) return;
    this.ledgerPage++;
    this.loadLedger(this.selectedCode, false);
  }

  // ----------------- Spend -----------------

  recordSpend(): void {
    if (!this.selectedCode) {
      this.toastr.warning('Open a pool to record a spend.', 'No pool');
      return;
    }
    if (this.spendForm.invalid) {
      this.spendForm.markAllAsTouched();
      return;
    }
    const v = this.spendForm.value;
    const code = this.selectedCode;
    if (!confirm(`Debit ₹${v.amount} from ${code}?`)) return;

    this.api.spendExpensePool(code, Number(v.amount), v.reference || undefined, v.description || undefined).subscribe(
      () => {
        this.toastr.success(`₹${v.amount} debited from ${code}.`, 'Recorded');
        this.spendForm.reset();
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Spend failed.', 'Failed')
    );
  }

  // ----------------- Helpers -----------------

  inr(n: number | undefined | null): string {
    const v = Number(n || 0);
    return '₹' + Math.round(v).toLocaleString('en-IN');
  }

  isCredit(t: PoolTxn): boolean {
    return (t.txnType || '').toUpperCase() === 'CREDIT';
  }
}
