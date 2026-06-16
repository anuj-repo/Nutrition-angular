import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-finance',
  templateUrl: './admin-finance.component.html',
  styleUrls: ['./admin-finance.component.css']
})
export class AdminFinanceComponent implements OnInit {

  loading = true;
  fromDate: Date | null = null;
  toDate: Date | null = null;

  // Summary
  totalRevenue = 0;
  totalCommissionPaid = 0;
  totalRepurchaseBonus = 0;
  totalWithdrawn = 0;
  pendingPayoutAmount = 0;
  pendingPayoutCount = 0;
  totalGst = 0;
  totalTds = 0;

  // Expense pools
  pools: any[] = [];
  totalPoolBalance = 0;
  totalPoolCredited = 0;
  totalPoolSpent = 0;

  // Withdrawals
  withdrawals: any[] = [];
  filteredWithdrawals: any[] = [];
  withdrawalFilter = 'ALL';

  // Pending payouts
  pendingPayouts: any[] = [];
  totalEarnedAll = 0;
  totalWithdrawnAll = 0;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loading = true;
    const from = this.formatDate(this.fromDate);
    const to = this.formatDate(this.toDate);

    forkJoin({
      bv: this.api.reportBV(from, to).pipe(catchError(() => of(null))),
      comm: this.api.reportCommission(from, to).pipe(catchError(() => of(null))),
      rep: this.api.reportRepurchase(from, to).pipe(catchError(() => of(null))),
      gst: this.api.reportGst(from, to).pipe(catchError(() => of(null))),
      tds: this.api.reportTds().pipe(catchError(() => of(null))),
      pools: this.api.expensePools().pipe(catchError(() => of(null))),
      withdrawals: this.api.allWithdrawals().pipe(catchError(() => of(null))),
      pending: this.api.pendingPayouts().pipe(catchError(() => of(null)))
    }).subscribe((res: any) => {
      this.totalRevenue = Number(res.bv?.data?.totalBV) || 0;
      this.totalCommissionPaid = Number(res.comm?.data?.totalEarnings) || 0;
      this.totalRepurchaseBonus = Number(res.rep?.data?.totalRepurchaseAmount) || 0;
      this.totalGst = Number(res.gst?.data?.totalGstCollected) || 0;
      this.totalTds = Number(res.tds?.data?.totalTdsDeducted) || 0;

      this.pools = res.pools?.data || [];
      this.totalPoolBalance = this.pools.reduce((s: number, p: any) => s + (Number(p.balance) || 0), 0);
      this.totalPoolCredited = this.pools.reduce((s: number, p: any) => s + (Number(p.totalCredited) || 0), 0);
      this.totalPoolSpent = this.pools.reduce((s: number, p: any) => s + (Number(p.totalSpent) || 0), 0);

      this.withdrawals = res.withdrawals?.data || [];
      this.totalWithdrawn = this.withdrawals
        .filter((w: any) => w.requestStatus === 'APPROVED' || w.requestStatus === 'PAID')
        .reduce((s: number, w: any) => s + (Number(w.netAmount || w.amount) || 0), 0);
      this.filterWithdrawals();

      this.pendingPayouts = res.pending?.data || [];
      this.pendingPayoutAmount = this.pendingPayouts.reduce((s: number, w: any) => s + (Number(w.balance) || 0), 0);
      this.pendingPayoutCount = this.pendingPayouts.length;
      this.totalEarnedAll = this.pendingPayouts.reduce((s: number, w: any) => s + (Number(w.totalEarned) || 0), 0);
      this.totalWithdrawnAll = this.pendingPayouts.reduce((s: number, w: any) => s + (Number(w.totalWithdrawn) || 0), 0);

      this.loading = false;
    });
  }

  filterWithdrawals(): void {
    if (this.withdrawalFilter === 'ALL') {
      this.filteredWithdrawals = this.withdrawals;
    } else {
      this.filteredWithdrawals = this.withdrawals.filter((w: any) => w.requestStatus === this.withdrawalFilter);
    }
  }

  applyFilter(): void { this.loadAll(); }

  clearFilter(): void {
    this.fromDate = null;
    this.toDate = null;
    this.loadAll();
  }

  private formatDate(d: Date | null): string | undefined {
    if (!d) return undefined;
    const y = d.getFullYear();
    const m = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    return `${y}-${m}-${day}`;
  }

  inr(n: number): string {
    if (!isFinite(n)) return '₹0';
    return '₹' + Math.round(n).toLocaleString('en-IN');
  }

  get companyNet(): number {
    return this.totalRevenue - this.totalCommissionPaid - this.totalRepurchaseBonus - this.totalPoolSpent - this.totalWithdrawn;
  }
}
