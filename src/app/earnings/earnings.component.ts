import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NetworkService } from '../_services/network.service';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-earnings',
  templateUrl: './earnings.component.html',
  styleUrls: ['./earnings.component.css']
})
export class EarningsComponent implements OnInit {

  earningsSummary: any = {
    totalEarnings: 0,
    monthlyEarnings: 0,
    weeklyEarnings: 0,
    pendingPayout: 0,
    walletBalance: 0,
    totalWithdrawn: 0
  };

  earningsHistory: any[] = [];
  commissionByLevel: { level: number; rank: string; totalEarned: number; members: number }[] = [];

  // Theoretical earning potential (illustrative - based on perfect 4-wide duplication)
  earningPotential = [
    { level: 1, people: 4, business: 9120, percentage: 20, earnings: 1824 },
    { level: 2, people: 16, business: 36480, percentage: 10, earnings: 3648 },
    { level: 3, people: 64, business: 145920, percentage: 6, earnings: 8755 },
    { level: 4, people: 256, business: 583680, percentage: 4, earnings: 23347 },
    { level: 5, people: 1024, business: 2334720, percentage: 4, earnings: 93389 },
    { level: 6, people: 4096, business: 9338880, percentage: 3, earnings: 280166 },
    { level: 7, people: 16384, business: 37355520, percentage: 3, earnings: 1120666 },
    { level: 8, people: 65536, business: 149422080, percentage: 2, earnings: 2988442 }
  ];

  displayedColumns = ['date', 'type', 'level', 'fromMember', 'amount', 'status'];
  selectedTab = 'summary';
  loading = true;

  // Detail dialog
  selectedTxn: any = null;

  constructor(private networkService: NetworkService, private api: BackendApiService) {}

  ngOnInit(): void {
    this.loadEarnings();
  }

  loadEarnings() {
    this.loading = true;

    const currentMonth = this.formatMonth(new Date());
    const lastWeekStart = new Date();
    lastWeekStart.setDate(lastWeekStart.getDate() - 7);

    forkJoin({
      breakdown: this.networkService.getIncomeBreakdown().pipe(catchError(() => of(null))),
      monthly: this.networkService.getCommissionMonthly(currentMonth).pipe(catchError(() => of(null))),
      ledger: this.networkService.getCommissionLedger(0, 50).pipe(catchError(() => of(null))),
      summary: this.networkService.getCommissionSummary().pipe(catchError(() => of(null))),
      teamGrowth: this.networkService.getTeamGrowth().pipe(catchError(() => of(null))),
      wallet: this.networkService.getWalletBalance().pipe(catchError(() => of(null)))
    }).subscribe(res => {
      const breakdown = res.breakdown?.data || {};
      const wallet = res.wallet?.data || {};
      const teamByLevel = res.teamGrowth?.data || {};

      this.earningsSummary = {
        totalEarnings: this.num(breakdown.commission),
        monthlyEarnings: this.num(res.monthly?.data),
        weeklyEarnings: this.computeWeekly(res.ledger?.data, lastWeekStart),
        pendingPayout: this.num(wallet.balance),
        walletBalance: this.num(wallet.balance),
        totalWithdrawn: this.num(wallet.totalWithdrawn)
      };

      // Ledger -> earningsHistory
      this.earningsHistory = (res.ledger?.data || []).map((t: any) => ({
        id: t.id,
        date: t.transactionDate,
        type: t.transactionType || 'Commission',
        level: t.commissionLevel,
        fromMember: t.fromUserName || (t.fromUserId ? '#' + t.fromUserId : '-'),
        amount: this.num(t.commissionAmount || t.amount),
        status: t.paidStatus === true || t.status === 'PAID' ? 'paid' : 'pending'
      }));

      // Commission summary -> commissionByLevel
      const summary = res.summary?.data || [];
      this.commissionByLevel = (Array.isArray(summary) ? summary : []).map((s: any) => ({
        level: s.level,
        rank: s.rankTitle || `Level ${s.level}`,
        totalEarned: this.num(s.totalEarned),
        members: this.num(teamByLevel[s.level])
      }));

      this.loading = false;
    });
  }

  private num(v: any): number {
    if (v == null) return 0;
    const n = Number(v);
    return isNaN(n) ? 0 : n;
  }

  private formatMonth(d: Date): string {
    const m = (d.getMonth() + 1).toString().padStart(2, '0');
    return `${d.getFullYear()}-${m}`;
  }

  private computeWeekly(ledger: any[], from: Date): number {
    if (!Array.isArray(ledger)) return 0;
    return ledger
      .filter(t => t.transactionDate && new Date(t.transactionDate) >= from)
      .reduce((s, t) => s + this.num(t.commissionAmount || t.amount), 0);
  }

  switchTab(tab: string) {
    this.selectedTab = tab;
  }

  viewTxn(t: any) {
    // Show row data first; enrich with backend detail if id is available
    this.selectedTxn = t;
    if (t?.id) {
      this.api.commissionTransaction(t.id).subscribe(
        (r: any) => { if (r?.data) this.selectedTxn = { ...t, ...r.data }; },
        () => {}
      );
    }
  }

  closeTxn() { this.selectedTxn = null; }
}
