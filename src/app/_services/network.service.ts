import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { API_PATH } from '../utils/constants/api.constants';
import { map } from 'rxjs/operators';

/**
 * Backend has two URL patterns because of `server.servlet.contextPath=/api`:
 *
 *   Legacy controllers   (e.g. UserController @RequestMapping(""))
 *      → relative paths like "/signUp" are reached via BASE_URL + "/api" + "/signUp"
 *      → use {BASE_URL}{API_VERSION_V1}{path}
 *
 *   New MLM controllers  (e.g. DashboardController @RequestMapping("/api/dashboard"))
 *      → already include "/api/..." in their mapping, on top of contextPath /api
 *      → use {BASE_URL}{path}   (where path already starts with "/api/...")
 *
 * The constants in api.constants.ts encode this distinction.
 */
@Injectable({ providedIn: 'root' })
export class NetworkService {

  constructor(private http: HttpClient) {}

  private url(path: string): string {
    return `${environment.BASE_URL}${path}`;
  }

  // ===== Dashboard =====
  getDashboardSummary() {
    return this.http.get<any>(this.url(API_PATH.DASHBOARD_SUMMARY));
  }

  getEarningsGraph(range: string = 'monthly') {
    const params = new HttpParams().set('range', range);
    return this.http.get<any>(this.url(API_PATH.DASHBOARD_EARNINGS_GRAPH), { params });
  }

  getTeamGrowth() {
    return this.http.get<any>(this.url(API_PATH.DASHBOARD_TEAM_GROWTH));
  }

  getIncomeBreakdown() {
    return this.http.get<any>(this.url(API_PATH.DASHBOARD_INCOME_BREAKDOWN));
  }

  getUpcomingRewards() {
    return this.http.get<any>(this.url(API_PATH.DASHBOARD_UPCOMING_REWARDS));
  }

  // ===== Network / Team =====
  getReferralLink() {
    return this.http.get<any>(this.url(API_PATH.NETWORK_REFERRAL_LINK));
  }

  getSponsor() {
    return this.http.get<any>(this.url(API_PATH.NETWORK_SPONSOR));
  }

  getDirectReferrals() {
    return this.http.get<any>(this.url(API_PATH.NETWORK_DIRECT));
  }

  getMyTree(levels: number = 15) {
    const params = new HttpParams().set('levels', String(levels));
    return this.http.get<any>(this.url(API_PATH.NETWORK_TREE), { params });
  }

  getTeamByLevel(level: number) {
    return this.http.get<any>(this.url(`${API_PATH.NETWORK_LEVEL}/${level}`));
  }

  getNetworkStats() {
    return this.http.get<any>(this.url(API_PATH.NETWORK_STATS));
  }

  getMemberDetail(userId: number) {
    return this.http.get<any>(this.url(`${API_PATH.NETWORK_MEMBER}/${userId}`));
  }

  /** Full subtree for a downline member (deeper than 1 level). */
  getMemberTree(userId: number, levels: number = 15) {
    const params = new HttpParams().set('levels', String(levels));
    return this.http.get<any>(this.url(`${API_PATH.NETWORK_USER_TREE}/${userId}/tree`), { params });
  }

  getNetworkLeaderboard() {
    return this.http.get<any>(this.url(API_PATH.NETWORK_LEADERBOARD));
  }

  // ===== Commissions =====
  getCommissionConfig() {
    return this.http.get<any>(this.url(API_PATH.COMMISSION_CONFIG));
  }

  getCommissionLedger(page: number = 0, size: number = 20) {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.http.get<any>(this.url(API_PATH.COMMISSION_LEDGER), { params });
  }

  getCommissionSummary() {
    return this.http.get<any>(this.url(API_PATH.COMMISSION_SUMMARY));
  }

  getCommissionMonthly(month: string) {
    const params = new HttpParams().set('month', month);
    return this.http.get<any>(this.url(API_PATH.COMMISSION_MONTHLY), { params });
  }

  getCommissionEarningsTable() {
    return this.http.get<any>(this.url(API_PATH.COMMISSION_EARNINGS_TABLE));
  }

  // ===== Repurchase =====
  getRepurchaseConfig() {
    return this.http.get<any>(this.url(API_PATH.REPURCHASE_CONFIG));
  }

  getRepurchaseCurrentCycle() {
    return this.http.get<any>(this.url(API_PATH.REPURCHASE_CURRENT_CYCLE));
  }

  getRepurchaseHistory() {
    return this.http.get<any>(this.url(API_PATH.REPURCHASE_HISTORY));
  }

  // ===== Ranks =====
  getAllRanks() {
    return this.http.get<any>(this.url(API_PATH.RANKS_ALL));
  }

  getMyRank() {
    return this.http.get<any>(this.url(API_PATH.RANKS_ME));
  }

  getMyRankProgress() {
    return this.http.get<any>(this.url(API_PATH.RANKS_ME_PROGRESS));
  }

  getRanksLeaderboard() {
    return this.http.get<any>(this.url(API_PATH.RANKS_LEADERBOARD));
  }

  // ===== Rewards =====
  getRewardTiers(category?: string) {
    let params = new HttpParams();
    if (category) {
      params = params.set('category', category);
    }
    return this.http.get<any>(this.url(API_PATH.REWARDS_TIERS), { params });
  }

  getMyEligibleRewards() {
    return this.http.get<any>(this.url(API_PATH.REWARDS_ELIGIBLE));
  }

  getMyEarnedRewards() {
    return this.http.get<any>(this.url(API_PATH.REWARDS_EARNED));
  }

  getMyRewardClaims() {
    return this.http.get<any>(this.url(API_PATH.REWARDS_CLAIMS));
  }

  claimReward(tierId: number, payload: any = {}) {
    return this.http.post<any>(
      this.url(`${API_PATH.REWARDS_CLAIM}/${tierId}/claim`),
      payload
    );
  }

  getMyTrips() {
    return this.http.get<any>(this.url(API_PATH.REWARDS_TRIPS));
  }

  bookTrip(payload: any) {
    return this.http.post<any>(this.url(API_PATH.REWARDS_TRIP_BOOK), payload);
  }

  // ===== Investments =====
  getInvestmentPlans() {
    return this.http.get<any>(this.url(API_PATH.INVESTMENT_PLANS));
  }

  calculateInvestment(amount: number, rate?: number, months?: number) {
    let params = new HttpParams().set('amount', String(amount));
    if (rate != null) params = params.set('rate', String(rate));
    if (months != null) params = params.set('months', String(months));
    return this.http.get<any>(this.url(API_PATH.INVESTMENT_CALCULATOR), { params });
  }

  subscribeInvestment(payload: { planId: number; amount: number; tenureMonths: number; utrNumber?: string; screenshotData?: string | null; screenshotName?: string | null; screenshotType?: string | null }) {
    return this.http.post<any>(this.url(API_PATH.INVESTMENT_SUBSCRIBE), payload);
  }

  getMyInvestments() {
    return this.http.get<any>(this.url(API_PATH.INVESTMENT_MINE));
  }

  getInvestmentSchedule(investmentId: number) {
    return this.http.get<any>(this.url(`${API_PATH.INVESTMENT_SCHEDULE}/${investmentId}/schedule`));
  }

  withdrawInvestment(investmentId: number) {
    return this.http.post<any>(
      this.url(`${API_PATH.INVESTMENT_WITHDRAW}/${investmentId}/withdraw`),
      {}
    );
  }

  // ===== Wallet =====
  getWalletBalance() {
    return this.http.get<any>(this.url(API_PATH.WALLET_BALANCE));
  }

  getWalletTransactions(page: number = 0, size: number = 20) {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.http.get<any>(this.url(API_PATH.WALLET_TRANSACTIONS), { params });
  }

  requestWithdrawal(payload: { amount: number; remarks?: string }) {
    return this.http.post<any>(this.url(API_PATH.WALLET_WITHDRAW), payload);
  }

  getMyWithdrawals() {
    return this.http.get<any>(this.url(API_PATH.WALLET_WITHDRAWALS));
  }

  // ===== Adding new member (uses legacy /signUp) =====
  addMember(memberData: any) {
    return this.http.post<any>(
      `${environment.BASE_URL}${API_PATH.API_VERSION_V1}${API_PATH.ADD_USER}`,
      memberData
    );
  }
}
