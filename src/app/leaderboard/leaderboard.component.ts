import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {
  network: any[] = [];
  ranks: any[] = [];
  selectedTab = 'network';
  loading = true;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void { this.load(); }

  load() {
    this.api.networkLeaderboard().subscribe(
      (r: any) => this.network = r?.data || [],
      () => this.network = []
    );
    this.api.rankLeaderboard().subscribe(
      (r: any) => { this.ranks = r?.data || []; this.loading = false; },
      () => { this.ranks = []; this.loading = false; }
    );
  }

  switchTab(t: string) { this.selectedTab = t; }

  trophy(i: number) {
    if (i === 0) return '🥇';
    if (i === 1) return '🥈';
    if (i === 2) return '🥉';
    return `#${i + 1}`;
  }
}
