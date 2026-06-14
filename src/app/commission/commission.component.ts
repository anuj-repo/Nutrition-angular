import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NetworkService } from '../_services/network.service';

interface CommissionLevelView {
  level: number;
  percentage: number;
  rank: string;
  rankHindi: string;
  totalEarned?: number;
}

@Component({
  selector: 'app-commission',
  templateUrl: './commission.component.html',
  styleUrls: ['./commission.component.css']
})
export class CommissionComponent implements OnInit {

  // Static rank-Hindi mapping (backend doesn't translate rank names)
  private rankHindiMap: { [key: string]: string } = {
    'ASSOCIATE': 'एसोसिएट',
    'BRONZE_LEADER': 'ब्रॉन्ज लीडर',
    'SILVER_LEADER': 'सिल्वर लीडर',
    'GOLD_LEADER': 'गोल्ड लीडर',
    'PLATINUM_LEADER': 'प्लैटिनम',
    'RUBY_LEADER': 'रूबी लीडर',
    'SAPPHIRE_LEADER': 'सफायर',
    'EMERALD_LEADER': 'एमराल्ड',
    'DIAMOND_LEADER': 'डायमंड',
    'BLUE_DIAMOND': 'ब्लू डायमंड',
    'CROWN_DIAMOND': 'क्राउन',
    'ROYAL_CROWN': 'रॉयल क्राउन',
    'AMBASSADOR': 'एंबेसडर',
    'GLOBAL_AMBASSADOR': 'ग्लोबल एंबेसडर',
    'LEGACY_CHAIRMAN': 'लीगेसी चेयरमैन'
  };

  // Default fallback when backend config is empty
  private defaultLevels: CommissionLevelView[] = [
    { level: 1, percentage: 20, rank: 'Associate', rankHindi: 'एसोसिएट' },
    { level: 2, percentage: 10, rank: 'Bronze Leader', rankHindi: 'ब्रॉन्ज लीडर' },
    { level: 3, percentage: 6, rank: 'Silver Leader', rankHindi: 'सिल्वर लीडर' },
    { level: 4, percentage: 4, rank: 'Gold Leader', rankHindi: 'गोल्ड लीडर' },
    { level: 5, percentage: 4, rank: 'Platinum Leader', rankHindi: 'प्लैटिनम' },
    { level: 6, percentage: 3, rank: 'Ruby Leader', rankHindi: 'रूबी लीडर' },
    { level: 7, percentage: 3, rank: 'Sapphire Leader', rankHindi: 'सफायर' },
    { level: 8, percentage: 2, rank: 'Emerald Leader', rankHindi: 'एमराल्ड' },
    { level: 9, percentage: 2, rank: 'Diamond', rankHindi: 'डायमंड' },
    { level: 10, percentage: 2, rank: 'Blue Diamond', rankHindi: 'ब्लू डायमंड' },
    { level: 11, percentage: 1.5, rank: 'Crown Diamond', rankHindi: 'क्राउन' },
    { level: 12, percentage: 1, rank: 'Royal Crown', rankHindi: 'रॉयल क्राउन' },
    { level: 13, percentage: 1, rank: 'Ambassador', rankHindi: 'एंबेसडर' },
    { level: 14, percentage: 1, rank: 'Global Ambassador', rankHindi: 'ग्लोबल एंबेसडर' },
    { level: 15, percentage: 0.5, rank: 'Legacy Chairman', rankHindi: 'लीगेसी चेयरमैन' }
  ];

  commissionLevels: CommissionLevelView[] = [...this.defaultLevels];
  totalCommission = 60;
  myCurrentLevel = 1;
  loading = true;

  constructor(private networkService: NetworkService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    forkJoin({
      config: this.networkService.getCommissionConfig().pipe(catchError(() => of(null))),
      summary: this.networkService.getCommissionSummary().pipe(catchError(() => of(null))),
      rank: this.networkService.getMyRank().pipe(catchError(() => of(null)))
    }).subscribe(res => {
      // Backend config: List<CommissionLevelConfig> { level, percentage, ... }
      const config = res.config?.data;
      if (config && Array.isArray(config) && config.length > 0) {
        this.commissionLevels = config
          .map((c: any) => ({
            level: c.level || c.commissionLevel,
            percentage: Number(c.percentage || c.commissionPercentage || 0),
            rank: this.formatRank(c.rankTitle || ''),
            rankHindi: this.rankHindiMap[c.rankTitle?.toUpperCase()] || ''
          }))
          .sort((a, b) => a.level - b.level);

        // Recompute total
        this.totalCommission = this.commissionLevels.reduce((s, l) => s + l.percentage, 0);
      }

      // Merge in earned-per-level from summary
      const summary = res.summary?.data || [];
      if (Array.isArray(summary) && summary.length > 0) {
        const map = new Map<number, number>();
        summary.forEach((s: any) => map.set(s.level, Number(s.totalEarned || 0)));
        this.commissionLevels.forEach(l => {
          l.totalEarned = map.get(l.level) || 0;
        });
      }

      // Current rank
      const rank = res.rank?.data || {};
      this.myCurrentLevel = Number(rank.currentLevel || 1);

      this.loading = false;
    });
  }

  private formatRank(raw: string): string {
    if (!raw) return '';
    return raw
      .toLowerCase()
      .split('_')
      .map(w => w.charAt(0).toUpperCase() + w.slice(1))
      .join(' ');
  }

  getBarWidth(percentage: number): string {
    const max = Math.max(...this.commissionLevels.map(l => l.percentage), 1);
    return (percentage / max * 100) + '%';
  }
}
