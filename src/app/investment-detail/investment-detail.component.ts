import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-investment-detail',
  templateUrl: './investment-detail.component.html',
  styleUrls: ['./investment-detail.component.css']
})
export class InvestmentDetailComponent implements OnInit {
  investmentId!: number;
  schedule: any[] = [];
  loading = true;

  constructor(private route: ActivatedRoute, private router: Router, private api: BackendApiService) {}

  ngOnInit(): void {
    this.investmentId = Number(this.route.snapshot.paramMap.get('id'));
    this.api.invSchedule(this.investmentId).subscribe(
      (r: any) => { this.schedule = r?.data || []; this.loading = false; },
      () => { this.loading = false; this.schedule = []; }
    );
  }

  totalPaid(): number {
    return this.schedule.filter(s => s.status === 'PAID' || s.isPaid).reduce((sum, s) => sum + Number(s.returnAmount || 0), 0);
  }
  totalDue(): number {
    return this.schedule.filter(s => s.status !== 'PAID' && !s.isPaid).reduce((sum, s) => sum + Number(s.returnAmount || 0), 0);
  }

  back() { this.router.navigate(['/investment']); }
}
