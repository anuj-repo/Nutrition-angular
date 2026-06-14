import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-reward-claims',
  templateUrl: './reward-claims.component.html',
  styleUrls: ['./reward-claims.component.css']
})
export class RewardClaimsComponent implements OnInit {
  claims: any[] = [];
  loading = true;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    this.api.rewardClaims().subscribe(
      (r: any) => { this.claims = r?.data || []; this.loading = false; },
      () => { this.loading = false; this.claims = []; }
    );
  }
}
