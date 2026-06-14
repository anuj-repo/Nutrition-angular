import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NetworkService } from '../_services/network.service';

interface BonusSlab {
  id?: number;
  min: number;
  max: number;
  percentage: number;
}

@Component({
  selector: 'app-repurchase-bonus',
  templateUrl: './repurchase-bonus.component.html',
  styleUrls: ['./repurchase-bonus.component.css']
})
export class RepurchaseBonusComponent implements OnInit {

  // Default fallback (matches doc tier table)
  private defaultSlabs: BonusSlab[] = [
    { min: 2000, max: 6000, percentage: 12 },
    { min: 6001, max: 10000, percentage: 10 },
    { min: 10001, max: 20000, percentage: 9 },
    { min: 20001, max: 50000, percentage: 8 },
    { min: 50001, max: 100000, percentage: 7 },
    { min: 100001, max: 150000, percentage: 6 },
    { min: 150001, max: 200000, percentage: 5 },
    { min: 200001, max: 300000, percentage: 4 },
    { min: 300001, max: 400000, percentage: 3 },
    { min: 400001, max: 500000, percentage: 2 },
    { min: 500001, max: 700000, percentage: 1 },
    { min: 700001, max: 1000000, percentage: 0.5 }
  ];

  bonusSlabs: BonusSlab[] = [...this.defaultSlabs];

  myBonusData: any = {
    currentTeamBusiness: 0,
    currentBonusPercentage: 0,
    currentBonusAmount: 0,
    lastMonthBonus: 0,
    totalBonusEarned: 0
  };

  bonusHistory: any[] = [];
  loading = true;

  constructor(private networkService: NetworkService) {}

  ngOnInit(): void {
    forkJoin({
      config: this.networkService.getRepurchaseConfig().pipe(catchError(() => of(null))),
      cycle: this.networkService.getRepurchaseCurrentCycle().pipe(catchError(() => of(null))),
      history: this.networkService.getRepurchaseHistory().pipe(catchError(() => of(null)))
    }).subscribe(res => {
      // Backend returns List<RepurchaseBonusConfig> with { minAmount, maxAmount, bonusPercentage }
      const config = res.config?.data;
      if (Array.isArray(config) && config.length > 0) {
        this.bonusSlabs = config
          .map((c: any) => ({
            id: c.id,
            min: this.num(c.minAmount),
            max: this.num(c.maxAmount),
            percentage: this.num(c.bonusPercentage)
          }))
          .sort((a, b) => a.min - b.min);
      }

      // Cycle: { teamVolume, applicableBonusPercent }
      const cycle = res.cycle?.data || {};
      const teamVolume = this.num(cycle.teamVolume);
      const applicablePercent = this.num(cycle.applicableBonusPercent);

      this.myBonusData = {
        currentTeamBusiness: teamVolume,
        currentBonusPercentage: applicablePercent,
        currentBonusAmount: Math.round((teamVolume * applicablePercent) / 100),
        lastMonthBonus: 0,
        totalBonusEarned: 0
      };

      // History: List<RepurchaseTransaction>
      const history = res.history?.data || [];
      this.bonusHistory = (Array.isArray(history) ? history : []).map((h: any) => ({
        month: this.formatMonth(h.repurchaseDate || h.transactionDate),
        teamBusiness: this.num(h.businessVolume || h.repurchaseAmount),
        bonusPercent: this.num(h.bonusPercentage),
        bonusAmount: this.num(h.bonusAmount)
      }));

      this.myBonusData.totalBonusEarned = this.bonusHistory.reduce(
        (s, h) => s + (h.bonusAmount || 0), 0
      );
      if (this.bonusHistory.length > 0) {
        this.myBonusData.lastMonthBonus = this.bonusHistory[0].bonusAmount;
      }

      this.loading = false;
    });
  }

  private num(v: any): number {
    if (v == null) return 0;
    const n = Number(v);
    return isNaN(n) ? 0 : n;
  }

  private formatMonth(date: any): string {
    if (!date) return '-';
    const d = new Date(date);
    return d.toLocaleDateString('en-IN', { month: 'short', year: 'numeric' });
  }

  isCurrentSlab(slab: BonusSlab): boolean {
    const business = this.myBonusData.currentTeamBusiness;
    return business >= slab.min && business <= slab.max;
  }

  formatCurrency(amount: number): string {
    if (amount >= 100000) {
      return '₹' + (amount / 100000).toFixed(1) + ' Lakh';
    }
    return '₹' + amount.toLocaleString('en-IN');
  }
}
