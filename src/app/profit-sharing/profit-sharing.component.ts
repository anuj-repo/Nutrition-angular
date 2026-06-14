import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-profit-sharing',
  templateUrl: './profit-sharing.component.html',
  styleUrls: ['./profit-sharing.component.css']
})
export class ProfitSharingComponent implements OnInit {
  stats: any = {};
  message = ''; error = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.api.networkStats().subscribe(
      (r: any) => this.stats = r?.data || {},
      () => this.stats = {}
    );
  }

  enroll() {
    this.message = ''; this.error = '';
    this.api.enrollProfitSharing().subscribe(
      (r: any) => {
        this.message = r?.data || r?.message || 'Enrolled successfully.';
        this.toastr.success(this.message, '🎉 Enrolled');
      },
      err => {
        this.error = err?.error?.message || 'You may not be eligible yet.';
        this.toastr.error(this.error, 'Enrollment failed');
      }
    );
  }

  get totalTeam(): number { return Number(this.stats?.totalTeam || 0); }
  get progressPct(): number { return Math.min((this.totalTeam / 50000) * 100, 100); }
}
