import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NetworkService } from '../_services/network.service';

interface RankView {
  level: number;
  rank: string;
  rankHindi: string;
  category: 'Entry' | 'Elite' | 'Legend';
  icon: string;
  color: string;
}

@Component({
  selector: 'app-ranks',
  templateUrl: './ranks.component.html',
  styleUrls: ['./ranks.component.css']
})
export class RanksComponent implements OnInit {

  myCurrentLevel = 0;
  myCurrentRank = '';
  rankProgress: any = null;

  // Static metadata (icons, colors, category, hindi) - merged with backend rank list
  private rankMeta: { [name: string]: Partial<RankView> } = {
    'ASSOCIATE': { rankHindi: 'एसोसिएट', category: 'Entry', icon: '🌱', color: '#4caf50' },
    'BRONZE_LEADER': { rankHindi: 'ब्रॉन्ज लीडर', category: 'Entry', icon: '🥉', color: '#cd7f32' },
    'SILVER_LEADER': { rankHindi: 'सिल्वर लीडर', category: 'Entry', icon: '🥈', color: '#c0c0c0' },
    'GOLD_LEADER': { rankHindi: 'गोल्ड लीडर', category: 'Entry', icon: '🥇', color: '#ffd700' },
    'PLATINUM_LEADER': { rankHindi: 'प्लैटिनम', category: 'Entry', icon: '💎', color: '#e5e4e2' },
    'RUBY_LEADER': { rankHindi: 'रूबी लीडर', category: 'Elite', icon: '❤️', color: '#e0115f' },
    'SAPPHIRE_LEADER': { rankHindi: 'सफायर', category: 'Elite', icon: '💙', color: '#0f52ba' },
    'EMERALD_LEADER': { rankHindi: 'एमराल्ड', category: 'Elite', icon: '💚', color: '#50c878' },
    'DIAMOND_LEADER': { rankHindi: 'डायमंड', category: 'Elite', icon: '💎', color: '#b9f2ff' },
    'BLUE_DIAMOND': { rankHindi: 'ब्लू डायमंड', category: 'Elite', icon: '🔷', color: '#0000ff' },
    'CROWN_DIAMOND': { rankHindi: 'क्राउन डायमंड', category: 'Legend', icon: '👑', color: '#9c27b0' },
    'ROYAL_CROWN': { rankHindi: 'रॉयल क्राउन', category: 'Legend', icon: '🏰', color: '#6a1b9a' },
    'AMBASSADOR': { rankHindi: 'एंबेसडर', category: 'Legend', icon: '🌟', color: '#ff6f00' },
    'GLOBAL_AMBASSADOR': { rankHindi: 'ग्लोबल एंबेसडर', category: 'Legend', icon: '🌍', color: '#1565c0' },
    'LEGACY_CHAIRMAN': { rankHindi: 'लीगेसी चेयरमैन', category: 'Legend', icon: '🏆', color: '#bf360c' }
  };

  ranks: RankView[] = [];
  loading = true;

  constructor(private networkService: NetworkService) {}

  ngOnInit(): void {
    forkJoin({
      all: this.networkService.getAllRanks().pipe(catchError(() => of(null))),
      me: this.networkService.getMyRank().pipe(catchError(() => of(null))),
      progress: this.networkService.getMyRankProgress().pipe(catchError(() => of(null)))
    }).subscribe(res => {
      const all = res.all?.data;

      if (Array.isArray(all) && all.length > 0) {
        this.ranks = all.map((r: any) => {
          const upper = (r.name || '').toUpperCase();
          const meta = this.rankMeta[upper] || {};
          return {
            level: Number(r.level || 0),
            rank: this.formatRank(r.name || ''),
            rankHindi: meta.rankHindi || '',
            category: meta.category || 'Entry',
            icon: meta.icon || '🏅',
            color: meta.color || '#999'
          };
        }).sort((a, b) => a.level - b.level);
      } else {
        // Fallback to all 15 ranks
        this.ranks = Object.keys(this.rankMeta).map((name, i) => {
          const m = this.rankMeta[name];
          return {
            level: i + 1,
            rank: this.formatRank(name),
            rankHindi: m.rankHindi || '',
            category: m.category || 'Entry',
            icon: m.icon || '🏅',
            color: m.color || '#999'
          };
        });
      }

      const me = res.me?.data || {};
      this.myCurrentLevel = Number(me.currentLevel || 0);
      this.myCurrentRank = this.formatRank(me.currentRank || '');

      this.rankProgress = res.progress?.data || null;

      this.loading = false;
    });
  }

  private formatRank(raw: string): string {
    if (!raw) return '';
    return raw.toLowerCase().split('_')
      .map(w => w.charAt(0).toUpperCase() + w.slice(1)).join(' ');
  }

  isAchieved(level: number): boolean {
    return level <= this.myCurrentLevel;
  }

  isCurrent(level: number): boolean {
    return level === this.myCurrentLevel;
  }

  getEntryRanks() { return this.ranks.filter(r => r.category === 'Entry'); }
  getEliteRanks() { return this.ranks.filter(r => r.category === 'Elite'); }
  getLegendRanks() { return this.ranks.filter(r => r.category === 'Legend'); }
}
