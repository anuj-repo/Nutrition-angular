import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { NetworkService } from '../_services/network.service';

interface RewardView {
  id?: number;
  count: number;
  reward: string;
  rewardHindi?: string;
  icon: string;
  category?: string;
}

@Component({
  selector: 'app-rewards',
  templateUrl: './rewards.component.html',
  styleUrls: ['./rewards.component.css']
})
export class RewardsComponent implements OnInit {

  myTeamSize = 0;
  myDirectReferrals = 0;

  directRewards: RewardView[] = [];
  teamRewards: RewardView[] = [];
  premiumRewards: RewardView[] = [];
  megaRewards: RewardView[] = [];
  ultimateRewards: RewardView[] = [];

  earnedRewardIds = new Set<number>();
  claimedRewardIds = new Set<number>();

  loading = true;
  errorMessage = '';
  successMessage = '';

  // Animated counters
  animDirect = 0;
  animTeam = 0;
  animClaimed = 0;

  // Confetti / celebrate state
  showCelebration = false;
  celebrationReward: RewardView | null = null;

  // Default fallback (matches doc when backend has no tiers configured)
  private defaults = {
    direct: [
      { count: 5, reward: 'Branded T-Shirt', rewardHindi: 'ब्रांडेड टी-शर्ट', icon: '👕' },
      { count: 10, reward: 'Premium Watch', rewardHindi: 'प्रीमियम वॉच', icon: '⌚' },
      { count: 15, reward: 'Wireless Earbuds', rewardHindi: 'वायरलेस इयरबड्स', icon: '🎧' },
      { count: 20, reward: 'Luxury Gift Hamper', rewardHindi: 'लक्जरी गिफ्ट हैम्पर', icon: '🎁' },
      { count: 25, reward: 'Leadership Trophy', rewardHindi: 'लीडरशिप ट्रॉफी', icon: '🏆' },
      { count: 50, reward: 'Star Performer Award', rewardHindi: 'स्टार परफॉर्मर', icon: '⭐' }
    ],
    team: [
      { count: 50, reward: 'Nainital Trip', rewardHindi: 'नैनीताल यात्रा', icon: '🏔️' },
      { count: 75, reward: 'Goa Trip', rewardHindi: 'गोवा यात्रा', icon: '🏖️' },
      { count: 100, reward: 'Smartphone', rewardHindi: 'स्मार्टफोन', icon: '📱' },
      { count: 150, reward: 'Premium Laptop', rewardHindi: 'प्रीमियम लैपटॉप', icon: '💻' },
      { count: 200, reward: 'Scooty Fund', rewardHindi: 'स्कूटी फंड', icon: '🛵' },
      { count: 250, reward: 'Sports Bike Fund', rewardHindi: 'स्पोर्ट्स बाइक फंड', icon: '🏍️' },
      { count: 300, reward: 'Sports Bike', rewardHindi: 'स्पोर्ट्स बाइक', icon: '🏍️' },
      { count: 500, reward: 'Royal Enfield Bullet', rewardHindi: 'रॉयल एनफील्ड बुलेट', icon: '🔥' }
    ],
    premium: [
      { count: 750, reward: 'iPhone 15', rewardHindi: 'आईफोन 15', icon: '📱' },
      { count: 1000, reward: 'Car Fund', rewardHindi: 'कार फंड', icon: '🚗' },
      { count: 1500, reward: 'Swift / i20', rewardHindi: 'स्विफ्ट / i20', icon: '🚙' },
      { count: 2000, reward: 'Honda City', rewardHindi: 'होंडा सिटी', icon: '🚘' },
      { count: 3000, reward: 'Dubai Tour', rewardHindi: 'दुबई यात्रा', icon: '✈️' }
    ],
    mega: [
      { count: 5000, reward: 'Fortuner SUV', rewardHindi: 'फॉर्च्यूनर SUV', icon: '🚙' },
      { count: 7500, reward: 'Thailand Trip', rewardHindi: 'थाईलैंड यात्रा', icon: '🌴' },
      { count: 10000, reward: 'Luxury Car + VIP', rewardHindi: 'लक्जरी कार + VIP', icon: '🚗' },
      { count: 15000, reward: 'Europe Trip', rewardHindi: 'यूरोप यात्रा', icon: '✈️' },
      { count: 25000, reward: 'Mercedes / BMW', rewardHindi: 'मर्सिडीज / BMW', icon: '🚘' }
    ],
    ultimate: [
      { count: 50000, reward: 'Profit Sharing Club', rewardHindi: 'प्रॉफिट शेयरिंग क्लब', icon: '💰' },
      { count: 100000, reward: 'LUXURY VILLA!', rewardHindi: 'लक्जरी विला!', icon: '🏠' }
    ]
  };

  constructor(private networkService: NetworkService, private toastr: ToastrService) {}

  ngOnInit(): void {
    forkJoin({
      tiers: this.networkService.getRewardTiers().pipe(catchError(() => of(null as any))),
      eligible: this.networkService.getMyEligibleRewards().pipe(catchError(() => of(null as any))),
      earned: this.networkService.getMyEarnedRewards().pipe(catchError(() => of(null as any))),
      stats: this.networkService.getNetworkStats().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      const stats = res.stats?.data || {};
      this.myTeamSize = Number(stats.totalTeam || 0);
      this.myDirectReferrals = Number(stats.directs || 0);

      const tiers = res.tiers?.data;
      if (Array.isArray(tiers) && tiers.length > 0) {
        this.distributeTiers(tiers);
      } else {
        this.useDefaults();
      }

      const earned = res.earned?.data || [];
      const earnedArr: any[] = Array.isArray(earned) ? earned : [];
      this.earnedRewardIds = new Set(
        earnedArr.map(r => r.rewardTier?.id || r.rewardTierId).filter(id => id != null)
      );
      this.claimedRewardIds = new Set(
        earnedArr.filter(r => r.isClaimed === true)
          .map(r => r.rewardTier?.id || r.rewardTierId)
          .filter(id => id != null)
      );

      this.loading = false;
      this.animateCounters();
    });
  }

  private animateCounters() {
    this.animateValue('animDirect', this.myDirectReferrals);
    this.animateValue('animTeam', this.myTeamSize);
    this.animateValue('animClaimed', this.claimedRewardIds.size);
  }

  private animateValue(prop: 'animDirect' | 'animTeam' | 'animClaimed', target: number) {
    const duration = 1200;
    const start = performance.now();
    const step = (now: number) => {
      const t = Math.min((now - start) / duration, 1);
      const eased = 1 - Math.pow(1 - t, 3);
      this[prop] = Math.floor(target * eased);
      if (t < 1) requestAnimationFrame(step);
      else this[prop] = target;
    };
    requestAnimationFrame(step);
  }

  private distributeTiers(tiers: any[]) {
    const mapped: RewardView[] = tiers.map(t => ({
      id: t.id,
      count: Number(t.requiredCount || 0),
      reward: t.rewardName || '',
      rewardHindi: t.rewardNameHi || '',
      icon: this.iconForTier(t),
      category: (t.rewardCategory || '').toUpperCase()
    }));

    this.directRewards = mapped.filter(r => r.category === 'DIRECT');
    this.teamRewards = mapped.filter(r => r.category === 'TEAM');
    this.premiumRewards = mapped.filter(r => r.category === 'PREMIUM');
    this.megaRewards = mapped.filter(r => r.category === 'MEGA');
    this.ultimateRewards = mapped.filter(r => r.category === 'ULTIMATE');

    if (this.directRewards.length === 0) this.directRewards = this.defaults.direct;
    if (this.teamRewards.length === 0) this.teamRewards = this.defaults.team;
    if (this.premiumRewards.length === 0) this.premiumRewards = this.defaults.premium;
    if (this.megaRewards.length === 0) this.megaRewards = this.defaults.mega;
    if (this.ultimateRewards.length === 0) this.ultimateRewards = this.defaults.ultimate;
  }

  private useDefaults() {
    this.directRewards = this.defaults.direct;
    this.teamRewards = this.defaults.team;
    this.premiumRewards = this.defaults.premium;
    this.megaRewards = this.defaults.mega;
    this.ultimateRewards = this.defaults.ultimate;
  }

  private iconForTier(t: any): string {
    const cat = (t.rewardCategory || '').toUpperCase();
    const name = (t.rewardName || '').toLowerCase();
    if (name.includes('villa') || name.includes('home')) return '🏠';
    if (name.includes('phone') || name.includes('iphone')) return '📱';
    if (name.includes('laptop')) return '💻';
    if (name.includes('watch')) return '⌚';
    if (name.includes('shirt')) return '👕';
    if (name.includes('bike') || name.includes('bullet') || name.includes('royal')) return '🏍️';
    if (name.includes('scoot')) return '🛵';
    if (name.includes('car') || name.includes('honda') || name.includes('swift') ||
        name.includes('mercedes') || name.includes('bmw') || name.includes('fortuner')) return '🚗';
    if (name.includes('trip') || name.includes('tour') || name.includes('dubai') ||
        name.includes('thailand') || name.includes('europe') || name.includes('goa') ||
        name.includes('nainital')) return '✈️';
    if (name.includes('hamper') || name.includes('gift')) return '🎁';
    if (name.includes('trophy') || name.includes('star')) return '🏆';
    if (cat === 'ULTIMATE') return '👑';
    if (cat === 'MEGA') return '🌟';
    return '🎁';
  }

  isAchieved(count: number, type: 'direct' | 'team'): boolean {
    const current = type === 'direct' ? this.myDirectReferrals : this.myTeamSize;
    return current >= count;
  }

  getProgress(count: number, type: 'direct' | 'team'): number {
    const current = type === 'direct' ? this.myDirectReferrals : this.myTeamSize;
    return Math.min((current / Math.max(count, 1)) * 100, 100);
  }

  isClaimed(reward: RewardView): boolean {
    return reward.id ? this.claimedRewardIds.has(reward.id) : false;
  }

  canClaim(reward: RewardView, type: 'direct' | 'team'): boolean {
    return reward.id != null
        && this.isAchieved(reward.count, type)
        && !this.isClaimed(reward);
  }

  /** All rewards across categories combined */
  get allRewards(): RewardView[] {
    return [
      ...this.directRewards,
      ...this.teamRewards,
      ...this.premiumRewards,
      ...this.megaRewards,
      ...this.ultimateRewards
    ];
  }

  get totalRewards(): number {
    return this.allRewards.length;
  }

  get totalUnlocked(): number {
    let count = 0;
    this.directRewards.forEach(r => { if (this.isAchieved(r.count, 'direct')) count++; });
    [...this.teamRewards, ...this.premiumRewards, ...this.megaRewards, ...this.ultimateRewards]
      .forEach(r => { if (this.isAchieved(r.count, 'team')) count++; });
    return count;
  }

  /** Find the next reward the user can aim for */
  get nextMilestone(): { reward: RewardView; type: 'direct' | 'team'; remaining: number; progress: number } | null {
    // Check direct rewards first
    const nextDirect = this.directRewards.find(r => !this.isAchieved(r.count, 'direct'));
    const nextTeam = [...this.teamRewards, ...this.premiumRewards, ...this.megaRewards, ...this.ultimateRewards]
      .find(r => !this.isAchieved(r.count, 'team'));

    const candidates: Array<{ reward: RewardView; type: 'direct' | 'team'; gap: number }> = [];
    if (nextDirect) {
      candidates.push({ reward: nextDirect, type: 'direct', gap: nextDirect.count - this.myDirectReferrals });
    }
    if (nextTeam) {
      candidates.push({ reward: nextTeam, type: 'team', gap: nextTeam.count - this.myTeamSize });
    }
    if (candidates.length === 0) return null;

    // Pick the one with smallest gap (closest)
    candidates.sort((a, b) => a.gap - b.gap);
    const pick = candidates[0];
    return {
      reward: pick.reward,
      type: pick.type,
      remaining: pick.gap,
      progress: this.getProgress(pick.reward.count, pick.type)
    };
  }

  /** Overall journey completion percentage */
  get journeyProgress(): number {
    if (this.totalRewards === 0) return 0;
    return Math.round((this.totalUnlocked / this.totalRewards) * 100);
  }

  trackById(_: number, r: RewardView) { return r.id ?? r.count + r.reward; }

  /** Count of rewards achieved within a given list */
  countAchieved(list: RewardView[], type: 'direct' | 'team'): number {
    return list.filter(r => this.isAchieved(r.count, type)).length;
  }

  /** Section-level progress: % of rewards in the list that are achieved */
  sectionProgress(list: RewardView[], type: 'direct' | 'team'): number {
    if (!list || list.length === 0) return 0;
    return (this.countAchieved(list, type) / list.length) * 100;
  }

  claim(reward: RewardView) {
    if (reward.id == null) return;
    this.errorMessage = '';
    this.successMessage = '';
    const tierId = reward.id;
    this.networkService.claimReward(tierId, { notes: '' }).subscribe(
      () => {
        this.successMessage = `Claimed: ${reward.reward}`;
        this.toastr.success(`Claimed: ${reward.reward}`, '🎉 Reward claimed');
        this.claimedRewardIds.add(tierId);
        this.triggerCelebration(reward);
      },
      err => {
        this.errorMessage = err?.error?.message || 'Claim failed. Please try again.';
        this.toastr.error(this.errorMessage, 'Claim failed');
      }
    );
  }

  private triggerCelebration(reward: RewardView) {
    this.celebrationReward = reward;
    this.showCelebration = true;
    setTimeout(() => {
      this.showCelebration = false;
      this.celebrationReward = null;
    }, 4000);
  }

  closeCelebration() {
    this.showCelebration = false;
    this.celebrationReward = null;
  }
}
