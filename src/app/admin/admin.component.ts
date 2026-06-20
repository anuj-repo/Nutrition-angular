import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BackendApiService } from '../_services/backend-api.service';

interface StatTile {
  label: string;
  value: string | number;
  hint?: string;
  icon: string;
  tone: 'good' | 'warn' | 'bad' | 'info';
  link?: string;
}

interface BarSlice {
  label: string;
  value: number;
  cls: 'pending' | 'completed' | 'failed' | 'approved' | 'rejected' | 'na';
}

interface DonutSlice {
  label: string;
  value: number;
  color: string;
}

interface LinePoint {
  date: string;        // YYYY-MM-DD
  shortLabel: string;  // dd/MM
  count: number;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  loading = true;
  error = '';

  tiles: StatTile[] = [];

  // Registrations by payment status
  regStatusBars: BarSlice[] = [];
  regStatusMax = 1;
  totalRegistrations = 0;

  // KYC status donut
  kycSlices: DonutSlice[] = [];
  kycTotal = 0;
  // SVG donut path data
  kycSvgSegments: { d: string; color: string; label: string; value: number }[] = [];

  // Last 14 days registrations line chart
  trend: LinePoint[] = [];
  trendMax = 1;
  trendPath = '';
  trendArea = '';
  trendPoints: { x: number; y: number; p: LinePoint }[] = [];

  // Recent registrations list
  recent: any[] = [];

  // Quick links
  quickLinks: { label: string; icon: string; url: string; desc: string }[] = [
    { label: 'Approvals',       icon: 'fact_check',     url: '/admin/approvals',        desc: 'Verify UTRs, KYC, withdrawals' },
    { label: 'User hierarchy',  icon: 'account_tree',   url: '/admin/user-tree',        desc: 'Browse parents and downlines' },
    { label: 'Users',           icon: 'people',         url: '/admin/users',            desc: 'Search, status' },
    { label: 'Reports',         icon: 'insights',       url: '/admin/reports',          desc: 'BV, commissions, GST, TDS' },
    { label: 'Catalog',         icon: 'inventory_2',    url: '/admin/catalog',          desc: 'Products & pricing' },
    { label: 'Expense Pools',   icon: 'savings',        url: '/admin/expense-pools',    desc: 'Manufacturing, reward, medical, software, meeting, misc' },
    { label: 'Email Templates', icon: 'mail',           url: '/admin/email-templates',  desc: 'Notification copy' },
    { label: 'Settings',        icon: 'settings',       url: '/admin/settings',         desc: 'System config' },
    { label: 'Manual Triggers', icon: 'play_circle',    url: '/admin/manual-triggers',  desc: 'Cron jobs, payouts' },
    { label: 'Role Permissions',icon: 'admin_panel_settings', url: '/admin/role-permissions', desc: 'Access control' },
  ];

  constructor(
    private api: BackendApiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.load();
  }

  refresh(): void {
    this.load();
  }

  go(url: string): void {
    this.router.navigateByUrl(url);
  }

  private load(): void {
    this.loading = true;
    this.error = '';

    // Load each API independently so tiles appear as data arrives
    // instead of blocking everything until the slowest call finishes.
    let completed = 0;
    const total = 3;
    let regs: any[] = [];
    let payouts: any[] = [];
    let users: any[] = [];

    const tryFinish = () => {
      completed++;
      // Render partial data as each call completes
      this.computeTiles(regs, payouts, users);
      if (completed >= total) {
        this.computeRegStatusBars(regs);
        this.computeKycDonut(regs);
        this.computeTrend(regs);
        this.recent = [...regs]
          .sort((a, b) => (b.createdAt || '').localeCompare(a.createdAt || ''))
          .slice(0, 6);
        this.loading = false;
      }
    };

    this.api.allRegistrations().pipe(catchError(() => of({ data: [] }))).subscribe(res => {
      regs = res?.data || [];
      tryFinish();
    });

    this.api.pendingPayouts().pipe(catchError(() => of({ data: [] }))).subscribe(res => {
      payouts = res?.data || [];
      tryFinish();
    });

    this.api.adminUsers().pipe(catchError(() => of({ data: [] }))).subscribe(res => {
      users = res?.data || [];
      tryFinish();
    });
  }

  // ----- Tiles -----
  private computeTiles(regs: any[], payouts: any[], users: any[]): void {
    const totalUsers = users.length || regs.length;
    const activeUsers = users.filter(u =>
      (u?.status === 'ACTIVE' || u?.active === true)
    ).length || regs.filter(r => r.user?.active).length;

    const pendingRegs = regs.filter(r => r.paymentStatus === 'PENDING').length;
    const pendingKyc = regs.filter(r => (r.user?.kyc?.status || 'PENDING') === 'PENDING').length;
    const pendingPayoutCount = payouts.length;

    const totalRevenue = regs
      .filter(r => r.paymentStatus === 'COMPLETED')
      .reduce((sum, r) => sum + Number(r.amountPaid || 0), 0);

    const pendingRevenue = regs
      .filter(r => r.paymentStatus === 'PENDING')
      .reduce((sum, r) => sum + Number(r.amountToPay || 0) - Number(r.amountPaid || 0), 0);

    this.tiles = [
      { label: 'Total Users',          value: totalUsers,         icon: 'group',          tone: 'info', link: '/admin/users' },
      { label: 'Active Users',         value: activeUsers,        icon: 'verified_user',  tone: 'good', link: '/admin/users' },
      { label: 'Pending Registrations',value: pendingRegs,        icon: 'how_to_reg',     tone: 'warn', link: '/admin/approvals', hint: 'Awaiting UTR verification' },
      { label: 'Pending KYC',          value: pendingKyc,         icon: 'badge',          tone: 'warn', link: '/admin/approvals', hint: 'Documents awaiting review' },
      { label: 'Pending Payouts',      value: pendingPayoutCount, icon: 'payments',       tone: 'warn', link: '/admin/approvals' },
      { label: 'Revenue Collected',    value: this.inr(totalRevenue),    icon: 'currency_rupee',  tone: 'good', hint: 'Sum of completed registration payments' },
      { label: 'Revenue Pending',      value: this.inr(pendingRevenue),  icon: 'hourglass_top',   tone: 'bad',  hint: 'Outstanding on pending UTRs' },
      { label: 'Total Registrations',  value: regs.length,        icon: 'app_registration', tone: 'info' },
    ];
  }

  // ----- Bar chart: registrations by payment status -----
  private computeRegStatusBars(regs: any[]): void {
    this.totalRegistrations = regs.length;
    const counts: Record<string, number> = { PENDING: 0, COMPLETED: 0, FAILED: 0 };
    regs.forEach(r => {
      const s = (r.paymentStatus || 'PENDING').toUpperCase();
      counts[s] = (counts[s] || 0) + 1;
    });
    this.regStatusBars = [
      { label: 'Completed', value: counts['COMPLETED'] || 0, cls: 'completed' },
      { label: 'Pending',   value: counts['PENDING']   || 0, cls: 'pending'   },
      { label: 'Failed',    value: counts['FAILED']    || 0, cls: 'failed'    }
    ];
    this.regStatusMax = Math.max(1, ...this.regStatusBars.map(b => b.value));
  }

  // ----- Donut: KYC status distribution -----
  private computeKycDonut(regs: any[]): void {
    const counts: Record<string, number> = {
      APPROVED: 0, PENDING: 0, REJECTED: 0, NOT_SUBMITTED: 0
    };
    regs.forEach(r => {
      const s = (r.user?.kyc?.status || 'NOT_SUBMITTED').toUpperCase();
      counts[s] = (counts[s] || 0) + 1;
    });

    const palette: Record<string, string> = {
      APPROVED:      '#2e7d32',
      PENDING:       '#f57c00',
      REJECTED:      '#c62828',
      NOT_SUBMITTED: '#90a4ae'
    };
    this.kycSlices = Object.keys(counts).map(k => ({
      label: this.titleCase(k.replace('_', ' ')),
      value: counts[k],
      color: palette[k]
    }));
    this.kycTotal = this.kycSlices.reduce((s, x) => s + x.value, 0);

    // Build SVG arc segments for the donut.
    this.kycSvgSegments = [];
    if (this.kycTotal === 0) return;

    const cx = 70, cy = 70, rOuter = 60, rInner = 38;
    let cumulative = 0;
    this.kycSlices.forEach(sl => {
      if (sl.value === 0) return;
      const startAngle = (cumulative / this.kycTotal) * Math.PI * 2;
      cumulative += sl.value;
      const endAngle = (cumulative / this.kycTotal) * Math.PI * 2;
      this.kycSvgSegments.push({
        d: this.donutArc(cx, cy, rOuter, rInner, startAngle, endAngle),
        color: sl.color,
        label: sl.label,
        value: sl.value
      });
    });
  }

  private donutArc(cx: number, cy: number, rOuter: number, rInner: number,
                   startAngle: number, endAngle: number): string {
    const largeArc = endAngle - startAngle > Math.PI ? 1 : 0;
    // Single full circle: SVG can't render a 360° arc with one path, so split.
    if (endAngle - startAngle >= Math.PI * 2 - 1e-6) {
      return [
        `M ${cx + rOuter} ${cy}`,
        `A ${rOuter} ${rOuter} 0 1 1 ${cx - rOuter} ${cy}`,
        `A ${rOuter} ${rOuter} 0 1 1 ${cx + rOuter} ${cy}`,
        `M ${cx + rInner} ${cy}`,
        `A ${rInner} ${rInner} 0 1 0 ${cx - rInner} ${cy}`,
        `A ${rInner} ${rInner} 0 1 0 ${cx + rInner} ${cy}`,
        'Z'
      ].join(' ');
    }
    const x1 = cx + rOuter * Math.cos(startAngle);
    const y1 = cy + rOuter * Math.sin(startAngle);
    const x2 = cx + rOuter * Math.cos(endAngle);
    const y2 = cy + rOuter * Math.sin(endAngle);
    const x3 = cx + rInner * Math.cos(endAngle);
    const y3 = cy + rInner * Math.sin(endAngle);
    const x4 = cx + rInner * Math.cos(startAngle);
    const y4 = cy + rInner * Math.sin(startAngle);
    return [
      `M ${x1} ${y1}`,
      `A ${rOuter} ${rOuter} 0 ${largeArc} 1 ${x2} ${y2}`,
      `L ${x3} ${y3}`,
      `A ${rInner} ${rInner} 0 ${largeArc} 0 ${x4} ${y4}`,
      'Z'
    ].join(' ');
  }

  // ----- Line/area: registrations over last 14 days -----
  private computeTrend(regs: any[]): void {
    const days = 14;
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const buckets: Record<string, number> = {};
    for (let i = days - 1; i >= 0; i--) {
      const d = new Date(today);
      d.setDate(today.getDate() - i);
      buckets[this.ymd(d)] = 0;
    }
    regs.forEach(r => {
      if (!r.createdAt) return;
      const d = new Date(r.createdAt);
      if (isNaN(d.getTime())) return;
      const key = this.ymd(d);
      if (key in buckets) buckets[key]++;
    });

    this.trend = Object.keys(buckets).map(k => {
      const dt = new Date(k);
      return {
        date: k,
        shortLabel: `${String(dt.getDate()).padStart(2, '0')}/${String(dt.getMonth() + 1).padStart(2, '0')}`,
        count: buckets[k]
      };
    });
    this.trendMax = Math.max(1, ...this.trend.map(p => p.count));

    // Build SVG path for the line + area. ViewBox 600x180, padded 30 each side.
    const W = 600, H = 180, padX = 30, padY = 20;
    const innerW = W - padX * 2;
    const innerH = H - padY * 2;
    const stepX = this.trend.length > 1 ? innerW / (this.trend.length - 1) : innerW;

    this.trendPoints = this.trend.map((p, i) => ({
      x: padX + i * stepX,
      y: padY + innerH - (p.count / this.trendMax) * innerH,
      p
    }));

    if (this.trendPoints.length === 0) {
      this.trendPath = '';
      this.trendArea = '';
      return;
    }

    const lineSegs: string[] = [];
    this.trendPoints.forEach((pt, i) => {
      lineSegs.push(`${i === 0 ? 'M' : 'L'} ${pt.x.toFixed(2)} ${pt.y.toFixed(2)}`);
    });
    this.trendPath = lineSegs.join(' ');

    const first = this.trendPoints[0];
    const last = this.trendPoints[this.trendPoints.length - 1];
    this.trendArea = [
      `M ${first.x.toFixed(2)} ${(padY + innerH).toFixed(2)}`,
      ...this.trendPoints.map(pt => `L ${pt.x.toFixed(2)} ${pt.y.toFixed(2)}`),
      `L ${last.x.toFixed(2)} ${(padY + innerH).toFixed(2)}`,
      'Z'
    ].join(' ');
  }

  // ----- Helpers -----
  inr(n: number): string {
    if (!isFinite(n)) return '₹0';
    return '₹' + Math.round(n).toLocaleString('en-IN');
  }

  private ymd(d: Date): string {
    const y = d.getFullYear();
    const m = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${y}-${m}-${day}`;
  }

  private titleCase(s: string): string {
    return s.toLowerCase().replace(/\b\w/g, c => c.toUpperCase());
  }

  pct(value: number, total: number): number {
    if (!total) return 0;
    return Math.round((value / total) * 100);
  }
}
