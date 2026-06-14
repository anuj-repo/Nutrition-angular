import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-reports',
  templateUrl: './admin-reports.component.html',
  styleUrls: ['./admin-reports.component.css']
})
export class AdminReportsComponent implements OnInit {
  selectedTab = 'bv';
  bv: any = null;
  comm: any = null;
  rep: any = null;
  rew: any = null;
  tds: any = null;
  gst: any = null;
  inactive: any[] = [];
  audit: any[] = [];
  pending: any[] = [];
  pendingTotal: number | null = null;
  fromDate: Date | null = null;
  toDate: Date | null = null;
  year = new Date().getFullYear();
  message = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void { this.loadAll(); }

  private fmt(d: Date | null): string {
    if (!d) { return ''; }
    const y = d.getFullYear();
    const m = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    return `${y}-${m}-${day}`;
  }

  loadAll() {
    const from = this.fmt(this.fromDate);
    const to = this.fmt(this.toDate);
    forkJoin({
      bv: this.api.reportBV(from, to).pipe(catchError(() => of(null as any))),
      comm: this.api.reportCommission(from, to).pipe(catchError(() => of(null as any))),
      rep: this.api.reportRepurchase(from, to).pipe(catchError(() => of(null as any))),
      rew: this.api.reportRewards().pipe(catchError(() => of(null as any))),
      tds: this.api.reportTds(this.year).pipe(catchError(() => of(null as any))),
      gst: this.api.reportGst(from, to).pipe(catchError(() => of(null as any))),
      inactive: this.api.reportInactive().pipe(catchError(() => of(null as any))),
      audit: this.api.auditLogs().pipe(catchError(() => of(null as any))),
      pending: this.api.pendingPayouts().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.bv = res.bv?.data;
      this.comm = res.comm?.data;
      this.rep = res.rep?.data;
      this.rew = res.rew?.data;
      this.tds = res.tds?.data;
      this.gst = res.gst?.data;
      this.inactive = res.inactive?.data || [];
      this.audit = res.audit?.data || [];
      this.pending = res.pending?.data || [];
      this.pendingTotal = (this.pending || [])
        .reduce((sum: number, w: any) => sum + (Number(w?.balance) || 0), 0);
    });
  }

  switchTab(t: string) { this.selectedTab = t; }

  clearFilter() {
    this.fromDate = null;
    this.toDate = null;
    this.loadAll();
  }

  runRepurchaseCron() {
    this.api.cronRepurchase().subscribe(
      (r: any) => {
        this.message = r?.data || 'Repurchase cron triggered.';
        this.toastr.success(this.message, 'Triggered');
      },
      err => {
        this.message = 'Error: ' + (err?.error?.message || '');
        this.toastr.error(this.message, 'Failed');
      }
    );
  }
  runRanksCron() {
    this.api.cronRanks().subscribe(
      (r: any) => {
        this.message = r?.data || 'Ranks promotion cron triggered.';
        this.toastr.success(this.message, 'Triggered');
      },
      err => {
        this.message = 'Error: ' + (err?.error?.message || '');
        this.toastr.error(this.message, 'Failed');
      }
    );
  }

  applyFilter() { this.loadAll(); }
}
