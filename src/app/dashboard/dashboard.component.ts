import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NetworkService } from '../_services/network.service';
import { UtililtyFunctions } from '../utils/utils';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  // Built from /api/dashboard/me/summary + /me/income-breakdown + /me/team-growth
  dashboardStats: any = {
    totalEarnings: 0,
    monthlyEarnings: 0,
    totalTeamSize: 0,
    directReferrals: 0,
    currentRank: 'Associate',
    currentLevel: 1,
    repurchaseBonus: 0,
    walletBalance: 0
  };

  earningsGraph: { label: string; amount: number }[] = [];
  recentEarnings: any[] = [];
  userData: any;
  referralCode = '';
  referralLink = '';
  loading = true;

  // Personalized greeting helpers
  greeting = 'Hello';
  todayLabel = '';
  motivationalLine = '';

  commissionLevels = [
    { level: 1, percentage: 20, rank: 'Associate' },
    { level: 2, percentage: 10, rank: 'Bronze Leader' },
    { level: 3, percentage: 6, rank: 'Silver Leader' },
    { level: 4, percentage: 4, rank: 'Gold Leader' },
    { level: 5, percentage: 4, rank: 'Platinum Leader' },
    { level: 6, percentage: 3, rank: 'Ruby Leader' },
    { level: 7, percentage: 3, rank: 'Sapphire Leader' },
    { level: 8, percentage: 2, rank: 'Emerald Leader' },
    { level: 9, percentage: 2, rank: 'Diamond' },
    { level: 10, percentage: 2, rank: 'Blue Diamond' },
    { level: 11, percentage: 1.5, rank: 'Crown Diamond' },
    { level: 12, percentage: 1, rank: 'Royal Crown' },
    { level: 13, percentage: 1, rank: 'Ambassador' },
    { level: 14, percentage: 1, rank: 'Global Ambassador' },
    { level: 15, percentage: 0.5, rank: 'Legacy Chairman' }
  ];

  constructor(
    private networkService: NetworkService,
    private utils: UtililtyFunctions,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.userData = this.utils.getUserMeData() || {};
    this.referralCode = this.userData?.referralCode || '';
    this.computeGreeting();
    this.loadDashboard();
  }

  /**
   * First name shown in the welcome banner. Falls back to a friendly
   * "there" if no profile data is available, so the user never sees
   * "Welcome, Member!" or a stale value.
   */
  get firstName(): string {
    const raw =
      this.userData?.fname ||
      this.userData?.firstName ||
      this.userData?.name ||
      '';
    const first = String(raw).trim().split(/\s+/)[0] || '';
    if (!first) return 'there';
    // Title-case (handles "HANUMAN" → "Hanuman")
    return first.charAt(0).toUpperCase() + first.slice(1).toLowerCase();
  }

  private computeGreeting() {
    const hour = new Date().getHours();
    if (hour < 5)        this.greeting = 'Hello';
    else if (hour < 12)  this.greeting = 'Good morning';
    else if (hour < 17)  this.greeting = 'Good afternoon';
    else if (hour < 21)  this.greeting = 'Good evening';
    else                 this.greeting = 'Good night';

    this.todayLabel = new Date().toLocaleDateString('en-IN', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });

    const lines = [
      'Wishing you a productive day ahead.',
      'Hope you’re feeling great today.',
      'Let’s make today count.',
      'Small steps every day lead to big results.',
      'Your team is growing — keep it up.',
      'A new day, a new opportunity.'
    ];
    this.motivationalLine = lines[new Date().getDate() % lines.length];
  }

  loadDashboard() {
    this.loading = true;

    forkJoin({
      summary: this.safeGet(this.networkService.getDashboardSummary()),
      breakdown: this.safeGet(this.networkService.getIncomeBreakdown()),
      teamGrowth: this.safeGet(this.networkService.getTeamGrowth()),
      stats: this.safeGet(this.networkService.getNetworkStats()),
      rank: this.safeGet(this.networkService.getMyRank()),
      referral: this.safeGet(this.networkService.getReferralLink()),
      graph: this.safeGet(this.networkService.getEarningsGraph()),
      ledger: this.safeGet(this.networkService.getCommissionLedger(0, 5)),
      wallet: this.safeGet(this.networkService.getWalletBalance())
    }).subscribe((res: any) => {
      const summary = res.summary?.data || {};
      const breakdown = res.breakdown?.data || {};
      const teamGrowth = res.teamGrowth?.data || {};
      const stats = res.stats?.data || {};
      const rank = res.rank?.data || {};
      const referral = res.referral?.data || {};
      const graph = res.graph?.data || {};
      const wallet = res.wallet?.data || {};

      this.dashboardStats = {
        totalEarnings: this.num(breakdown.commission || summary.totalEarnings),
        monthlyEarnings: this.num(summary.monthlyEarnings),
        totalTeamSize: this.num(stats.totalTeam || summary.totalTeamSize),
        directReferrals: this.num(stats.directs || summary.directs),
        currentRank: rank.currentRank || summary.currentRank || 'Associate',
        currentLevel: this.num(rank.currentLevel || 1),
        repurchaseBonus: this.num(breakdown.repurchaseBonus),
        walletBalance: this.num(wallet.balance)
      };

      // earnings graph from { "YYYY-MM": amount }
      this.earningsGraph = Object.keys(graph).map(k => ({
        label: k,
        amount: this.num(graph[k])
      }));

      this.recentEarnings = (res.ledger?.data || []).map((t: any) => ({
        date: t.transactionDate,
        type: t.transactionType || 'Commission',
        level: t.commissionLevel,
        amount: this.num(t.commissionAmount || t.amount)
      }));

      if (referral.referralCode) {
        this.referralCode = referral.referralCode;
      }
      // Prefer the link from the API; otherwise build a local one from the
      // code so the user always has something they can copy.
      if (referral.link) {
        this.referralLink = referral.link;
      } else if (this.referralCode) {
        this.referralLink = `${window.location.origin}/register?ref=${encodeURIComponent(this.referralCode)}`;
      } else {
        this.referralLink = '';
      }

      this.loading = false;
    });
  }

  private num(v: any): number {
    if (v == null) return 0;
    const n = Number(v);
    return isNaN(n) ? 0 : n;
  }

  private safeGet<T = any>(obs$: any) {
    return obs$.pipe(catchError(() => of(null as any))) as import('rxjs').Observable<any>;
  }

  copyReferralCode() {
    if (!this.referralCode) {
      this.toastr.info('Your referral code isn\'t ready yet.');
      return;
    }
    navigator.clipboard.writeText(this.referralCode).then(
      () => this.toastr.success(this.referralCode, 'Code copied'),
      () => this.toastr.error('Could not copy referral code')
    );
  }

  copyReferralLink() {
    if (!this.referralLink) {
      this.toastr.info('Referral link isn\'t available yet.');
      return;
    }
    navigator.clipboard.writeText(this.referralLink).then(
      () => this.toastr.success('Share it anywhere you like!', 'Link copied'),
      () => this.toastr.error('Could not copy referral link')
    );
  }

  getMonthBarWidth(amount: number): number {
    const max = Math.max(...this.earningsGraph.map(g => g.amount), 1);
    return Math.max(2, (amount / max) * 100);
  }
}
