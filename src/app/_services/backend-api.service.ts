import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

/**
 * master API service for all "new" backend controllers (those whose @RequestMapping starts with "/api/...").
 * Backend has server.servlet.contextPath=/api, so URLs become http://host:5000/api/api/...
 * For these we use BASE_URL + path (path already starts with /api/...).
 *
 * Legacy endpoints (UserController, CartController, etc.) are unchanged in user.service.ts.
 */
@Injectable({ providedIn: 'root' })
export class BackendApiService {

  constructor(private http: HttpClient) {}

  private url(p: string): string { return `${environment.BASE_URL}${p}`; }

  // ============ User Address ============
  addresses()                              { return this.http.get<any>(this.url('/api/user/me/address')); }
  addAddress(p: any)                       { return this.http.post<any>(this.url('/api/user/me/address'), p); }
  updateAddress(id: number, p: any)        { return this.http.put<any>(this.url(`/api/user/me/address/${id}`), p); }
  deleteAddress(id: number)                { return this.http.delete<any>(this.url(`/api/user/me/address/${id}`)); }

  // ============ Avatar ============
  uploadAvatar(file: File) {
    const fd = new FormData();
    fd.append('file', file);
    return this.http.post<any>(this.url('/api/user/me/avatar'), fd);
  }

  // ============ Wallet ============
  walletBalance()                          { return this.http.get<any>(this.url('/api/wallet/me/balance')); }
  walletTxns(page = 0, size = 20)          { return this.http.get<any>(this.url('/api/wallet/me/transactions'), { params: new HttpParams().set('page', page).set('size', size) }); }
  withdraw(p: { amount: number; remarks?: string }) { return this.http.post<any>(this.url('/api/wallet/me/withdraw'), p); }
  myWithdrawals()                          { return this.http.get<any>(this.url('/api/wallet/me/withdrawals')); }
  approveWithdrawal(id: number, utr?: string)   { return this.http.post<any>(this.url(`/api/wallet/withdrawals/${id}/approve`), {}, { params: new HttpParams().set('utr', utr || '') }); }
  rejectWithdrawal(id: number, reason?: string) { return this.http.post<any>(this.url(`/api/wallet/withdrawals/${id}/reject`), {}, { params: new HttpParams().set('reason', reason || '') }); }
  allWithdrawals()                              { return this.http.get<any>(this.url('/api/wallet/withdrawals/all')); }
  processPayouts()                         { return this.http.post<any>(this.url('/api/wallet/payouts/process'), {}); }
  tdsSummary()                             { return this.http.get<any>(this.url('/api/wallet/me/tds-summary')); }
  form16a(year: number)                    { return this.http.get(this.url(`/api/wallet/me/tds/${year}/form-16a`), { responseType: 'blob' }); }

  // ============ Payments ============
  initiatePayment(p: any)                  { return this.http.post<any>(this.url('/api/payments/initiate'), p); }
  paymentStatus(orderId: number)           { return this.http.get<any>(this.url(`/api/payments/${orderId}/status`)); }
  refund(orderId: number, p: any)          { return this.http.post<any>(this.url(`/api/payments/refund/${orderId}`), p); }
  paymentHistory()                         { return this.http.get<any>(this.url('/api/payments/me/history')); }

  // ============ Investments ============
  investmentPlans()                        { return this.http.get<any>(this.url('/api/investments/plans')); }
  createInvestmentPlan(p: any)             { return this.http.post<any>(this.url('/api/investments/plans'), p); }
  invCalc(amount: number, rate?: number, months?: number) {
    let params = new HttpParams().set('amount', String(amount));
    if (rate != null) params = params.set('rate', String(rate));
    if (months != null) params = params.set('months', String(months));
    return this.http.get<any>(this.url('/api/investments/calculator'), { params });
  }
  invSubscribe(p: any)                     { return this.http.post<any>(this.url('/api/investments/subscribe'), p); }
  myInvestments()                          { return this.http.get<any>(this.url('/api/investments/me')); }
  invSchedule(id: number)                  { return this.http.get<any>(this.url(`/api/investments/me/${id}/schedule`)); }
  invStatement(id: number)                 { return this.http.get<any>(this.url(`/api/investments/me/${id}/statement`)); }
  invWithdraw(id: number)                  { return this.http.post<any>(this.url(`/api/investments/me/${id}/withdraw`), {}); }
  invPayout()                              { return this.http.post<any>(this.url('/api/investments/payout'), {}); }

  // ============ Insurance ============
  insPlans()                               { return this.http.get<any>(this.url('/api/insurance/plans')); }
  insCreatePlan(p: any)                    { return this.http.post<any>(this.url('/api/insurance/plans'), p); }
  myInsurance()                            { return this.http.get<any>(this.url('/api/insurance/me')); }
  insAutoEnroll()                          { return this.http.post<any>(this.url('/api/insurance/me/auto-enroll'), {}); }
  insClaim(p: any)                         { return this.http.post<any>(this.url('/api/insurance/me/claim'), p); }
  myInsuranceClaims()                      { return this.http.get<any>(this.url('/api/insurance/me/claims')); }
  saveNominee(p: any)                      { return this.http.post<any>(this.url('/api/insurance/me/nominee'), p); }
  insEligibility()                         { return this.http.get<any>(this.url('/api/insurance/me/eligibility')); }

  // ============ Compliance ============
  dsa()                                    { return this.http.get<any>(this.url('/api/compliance/dsa')); }
  acceptDsa()                              { return this.http.post<any>(this.url('/api/compliance/dsa/accept'), {}); }
  incomeCertificate()                      { return this.http.get(this.url('/api/compliance/me/income-certificate'), { responseType: 'blob' }); }
  coolingOffStatus(orderId: number)        { return this.http.get<any>(this.url(`/api/compliance/cooling-off/${orderId}`)); }
  exerciseCoolingOff(orderId: number, p: any) { return this.http.post<any>(this.url(`/api/compliance/cooling-off/${orderId}/exercise`), p); }
  myGrievances()                           { return this.http.get<any>(this.url('/api/compliance/grievance/me')); }
  allGrievances()                          { return this.http.get<any>(this.url('/api/compliance/grievance')); }
  fileGrievance(p: any)                    { return this.http.post<any>(this.url('/api/compliance/grievance'), p); }

  // ============ Notifications ============
  notifications()                          { return this.http.get<any>(this.url('/api/notifications/me')); }
  markRead(id: number)                     { return this.http.patch<any>(this.url(`/api/notifications/${id}/read`), {}); }
  markAllRead()                            { return this.http.patch<any>(this.url('/api/notifications/me/read-all'), {}); }
  getNotifPrefs()                          { return this.http.get<any>(this.url('/api/notifications/preferences')); }
  saveNotifPrefs(p: any)                   { return this.http.post<any>(this.url('/api/notifications/preferences'), p); }
  registerDevice(p: any)                   { return this.http.post<any>(this.url('/api/notifications/devices/register'), p); }

  // ============ Support ============
  myTickets()                              { return this.http.get<any>(this.url('/api/support/tickets/me')); }
  getTicket(id: number)                    { return this.http.get<any>(this.url(`/api/support/tickets/${id}`)); }
  createTicket(p: any)                     { return this.http.post<any>(this.url('/api/support/tickets'), p); }
  replyTicket(id: number, p: any)          { return this.http.post<any>(this.url(`/api/support/tickets/${id}/reply`), p); }
  adminReplyTicket(id: number, p: any)     { return this.http.post<any>(this.url(`/api/support/tickets/${id}/admin-reply`), p); }
  ticketReplies(id: number)                { return this.http.get<any>(this.url(`/api/support/tickets/${id}/replies`)); }
  updateTicketStatus(id: number, status: string) { return this.http.put<any>(this.url(`/api/support/tickets/${id}/status?status=${status}`), {}); }

  // ============ Feedback ============
  submitFeedback(p: any)                   { return this.http.post<any>(this.url('/api/feedback'), p); }
  myFeedback()                             { return this.http.get<any>(this.url('/api/feedback/me')); }
  allFeedback()                            { return this.http.get<any>(this.url('/api/feedback')); }

  // ============ Training & Marketing ============
  courses()                                { return this.http.get<any>(this.url('/api/training/courses')); }
  createCourse(p: any)                     { return this.http.post<any>(this.url('/api/training/courses'), p); }
  updateCourse(id: number, p: any)         { return this.http.put<any>(this.url(`/api/training/courses/${id}`), p); }
  completeCourse(id: number)               { return this.http.post<any>(this.url(`/api/training/courses/${id}/complete`), {}); }
  myProgress()                             { return this.http.get<any>(this.url('/api/training/me/progress')); }
  collateral()                             { return this.http.get<any>(this.url('/api/marketing/collateral')); }
  banners()                                { return this.http.get<any>(this.url('/api/marketing/banners')); }

  // ============ Announcements ============
  announcements()                          { return this.http.get<any>(this.url('/api/announcements')); }
  createAnnouncement(p: any)               { return this.http.post<any>(this.url('/api/announcements'), p); }
  updateAnnouncement(id: number, p: any)   { return this.http.put<any>(this.url(`/api/announcements/${id}`), p); }
  deleteAnnouncement(id: number)           { return this.http.delete<any>(this.url(`/api/announcements/${id}`)); }

  // ============ Network (extras) ============
  networkTree(levels = 15)                 { return this.http.get<any>(this.url('/api/network/me/tree'), { params: new HttpParams().set('levels', levels) }); }
  networkLevel(level: number)              { return this.http.get<any>(this.url(`/api/network/me/level/${level}`)); }
  networkSponsor()                         { return this.http.get<any>(this.url('/api/network/me/sponsor')); }
  networkDirect()                          { return this.http.get<any>(this.url('/api/network/me/direct')); }
  networkStats()                           { return this.http.get<any>(this.url('/api/network/me/stats')); }
  networkLeaderboard()                     { return this.http.get<any>(this.url('/api/network/me/leaderboard')); }
  rankLeaderboard()                        { return this.http.get<any>(this.url('/api/ranks/leaderboard')); }
  memberDetail(id: number)                 { return this.http.get<any>(this.url(`/api/network/member/${id}`)); }

  // ============ Rewards (extras) ============
  rewardClaims()                           { return this.http.get<any>(this.url('/api/rewards/me/claims')); }
  bookTrip(p: any)                         { return this.http.post<any>(this.url('/api/rewards/me/trip/book'), p); }
  myTrips()                                { return this.http.get<any>(this.url('/api/rewards/me/trips')); }
  enrollProfitSharing()                    { return this.http.post<any>(this.url('/api/rewards/me/profit-sharing/enroll'), {}); }
  allocateVilla(userId: number)            { return this.http.post<any>(this.url(`/api/rewards/me/villa/allocate?userId=${userId}`), {}); }
  updateClaimStatus(id: number, delivered: boolean, notes?: string) {
    let params = new HttpParams().set('delivered', String(delivered));
    if (notes) params = params.set('notes', notes);
    return this.http.put<any>(this.url(`/api/rewards/claims/${id}/status`), {}, { params });
  }
  createRewardTier(p: any)                 { return this.http.post<any>(this.url('/api/rewards/tiers'), p); }
  updateRewardTier(id: number, p: any)     { return this.http.put<any>(this.url(`/api/rewards/tiers/${id}`), p); }
  deleteRewardTier(id: number)             { return this.http.delete<any>(this.url(`/api/rewards/tiers/${id}`)); }

  // ============ Orders V2 ============
  createOrder(p: any)                      { return this.http.post<any>(this.url('/api/orders'), p); }
  myOrders()                               { return this.http.get<any>(this.url('/api/orders/me')); }
  getOrder(id: number)                     { return this.http.get<any>(this.url(`/api/orders/${id}`)); }
  cancelOrder(id: number, reason?: string) { return this.http.post<any>(this.url(`/api/orders/${id}/cancel`), { reason }); }
  returnOrder(id: number, p: any)          { return this.http.post<any>(this.url(`/api/orders/${id}/return`), p); }
  trackOrder(id: number)                   { return this.http.get<any>(this.url(`/api/orders/${id}/track`)); }
  invoice(id: number)                      { return this.http.get(this.url(`/api/orders/${id}/invoice`), { responseType: 'blob' }); }
  repurchaseDue()                          { return this.http.get<any>(this.url('/api/orders/me/repurchase-due')); }
  adminAllOrders()                         { return this.http.get<any>(this.url('/api/orders/admin/all')); }

  // ============ Public ============
  publicLanding()                          { return this.http.get<any>(this.url('/api/public/landing')); }
  publicAbout()                            { return this.http.get<any>(this.url('/api/public/about')); }
  publicContact()                          { return this.http.get<any>(this.url('/api/public/contact')); }
  publicWhyUs()                            { return this.http.get<any>(this.url('/api/public/why-choose-us')); }
  publicFaq()                              { return this.http.get<any>(this.url('/api/public/faq')); }
  submitFaq(p: any)                        { return this.http.post<any>(this.url('/api/public/faq'), p); }
  contactInquiry(p: any)                   { return this.http.post<any>(this.url('/api/public/contact/inquiry'), p); }
  newsletterSubscribe(p: any)              { return this.http.post<any>(this.url('/api/public/newsletter/subscribe'), p); }
  validateSponsor(code: string)            { return this.http.get<any>(this.url(`/api/public/sponsor/${code}/validate`)); }
  pincodeServiceability(pin: string)       { return this.http.get<any>(this.url(`/api/public/pincode/${pin}/serviceability`)); }
  i18n(lang: string)                       { return this.http.get<any>(this.url(`/api/public/i18n/${lang}`)); }
  cookieConsent(p: any)                    { return this.http.post<any>(this.url('/api/public/cookie-consent'), p); }

  // ============ Auth Extension ============
  checkUsername(u: string)                 { return this.http.get<any>(this.url(`/api/auth/check-username?username=${encodeURIComponent(u)}`)); }
  checkMobile(m: string)                   { return this.http.get<any>(this.url(`/api/auth/check-mobile?mobile=${encodeURIComponent(m)}`)); }
  otpSend(p: any)                          { return this.http.post<any>(this.url('/api/auth/otp/send'), p); }
  otpVerify(p: any)                        { return this.http.post<any>(this.url('/api/auth/otp/verify'), p); }
  otpResend(p: any)                        { return this.http.post<any>(this.url('/api/auth/otp/resend'), p); }
  refreshToken(p: any)                     { return this.http.post<any>(this.url('/api/auth/refresh-token'), p); }

  // ============ Password ============
  // Backend takes ?username=... and accepts either the username or the email
  // (PasswordServiceImpl falls back to findByEmail when username doesn't match).
  getPwdRecoveryOtp(usernameOrEmail: string) { return this.http.get<any>(this.url(`/api/get-password-recovery-otp?username=${encodeURIComponent(usernameOrEmail)}`)); }
  verifyPwdRecoveryOtp(p: any)             { return this.http.post<any>(this.url('/api/verify-password-recovery-otp'), p); }
  resetPassword(p: any)                    { return this.http.post<any>(this.url('/api/reset-password'), p); }
  changePassword(p: any)                   { return this.http.post<any>(this.url('/api/change-password'), p); }

  // ============ Admin Reports ============
  reportBV(from?: string, to?: string)     { return this.http.get<any>(this.url('/api/admin/reports/business-volume'), { params: this.range(from, to) }); }
  reportCommission(from?: string, to?: string) { return this.http.get<any>(this.url('/api/admin/reports/commission-summary'), { params: this.range(from, to) }); }
  reportRepurchase(from?: string, to?: string) { return this.http.get<any>(this.url('/api/admin/reports/repurchase-summary'), { params: this.range(from, to) }); }
  reportRewards()                          { return this.http.get<any>(this.url('/api/admin/reports/rewards-fulfillment')); }
  reportTds(year?: number)                 {
    let params = new HttpParams();
    if (year != null) params = params.set('year', year);
    return this.http.get<any>(this.url('/api/admin/reports/tds'), { params });
  }
  reportGst(from?: string, to?: string)    { return this.http.get<any>(this.url('/api/admin/reports/gst'), { params: this.range(from, to) }); }
  reportInactive()                         { return this.http.get<any>(this.url('/api/admin/reports/inactive-users')); }
  cronRepurchase()                         { return this.http.post<any>(this.url('/api/admin/cron/repurchase'), {}); }
  cronRanks()                              { return this.http.post<any>(this.url('/api/admin/cron/ranks'), {}); }
  auditLogs()                              { return this.http.get<any>(this.url('/api/admin/audit-logs')); }
  pendingPayouts()                         { return this.http.get<any>(this.url('/api/admin/payouts/pending')); }

  private range(from?: string, to?: string): HttpParams {
    let p = new HttpParams();
    if (from) p = p.set('from', from);
    if (to) p = p.set('to', to);
    return p;
  }

  // ============ Admin: Registration Approvals ============
  pendingRegistrations() {
    return this.http.get<any>(this.url('/api/admin/registrations/pending'));
  }

  allRegistrations(status?: string) {
    let params = new HttpParams();
    if (status) params = params.set('status', status);
    return this.http.get<any>(this.url('/api/admin/registrations'), { params });
  }

  approveRegistration(id: number) {
    return this.http.post<any>(this.url(`/api/admin/registrations/${id}/approve`), {});
  }

  rejectRegistration(id: number, reason?: string) {
    let params = new HttpParams();
    if (reason) params = params.set('reason', reason);
    return this.http.post<any>(this.url(`/api/admin/registrations/${id}/reject`), {}, { params });
  }

  /** Verify and approve a registration by its UTR / Transaction ID. */
  approveRegistrationByUtr(utrNumber: string, amountPaid?: number) {
    const body: any = { utrNumber };
    if (amountPaid != null) body.amountPaid = amountPaid;
    return this.http.post<any>(
      this.url('/api/admin/registrations/approve-by-utr'),
      body
    );
  }

  /**
   * Record the verified paid amount against a registration UTR.
   * Activates the user automatically when amountPaid >= amountToPay.
   */
  updateRegistrationPayment(payload: {
    utrNumber: string;
    amountPaid: number;
    amountToPay?: number;
  }) {
    return this.http.post<any>(
      this.url('/api/admin/registrations/update-payment'),
      payload
    );
  }

  /** Toggle a user between ACTIVE and INACTIVE. */
  toggleUserActive(userId: number, active: boolean) {
    const params = new HttpParams().set('active', String(active));
    return this.http.post<any>(
      this.url(`/api/admin/registrations/users/${userId}/toggle-active`),
      {},
      { params }
    );
  }

  /** Direct URL to a user's KYC doc image (for inline preview / new tab). */
  kycDocUrl(userId: number, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF'): string {
    return this.url(`/api/admin/registrations/users/${userId}/kyc-doc/${docType}`);
  }

  /**
   * Fetch a KYC doc as a Blob through HttpClient so the AuthInterceptor
   * attaches the admin Bearer token. The returned blob can then be turned
   * into a blob: URL for inline <img> preview or "open in new tab".
   */
  kycDocBlob(userId: number, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF') {
    return this.http.get(
      this.url(`/api/admin/registrations/users/${userId}/kyc-doc/${docType}`),
      { responseType: 'blob' }
    );
  }

  approveKyc(userId: number) {
    return this.http.post<any>(
      this.url(`/api/admin/registrations/users/${userId}/kyc/approve`),
      {}
    );
  }

  rejectKyc(userId: number, reason?: string) {
    let params = new HttpParams();
    if (reason) params = params.set('reason', reason);
    return this.http.post<any>(
      this.url(`/api/admin/registrations/users/${userId}/kyc/reject`),
      {},
      { params }
    );
  }

  // ============ Admin Users ============
  adminUsers(params: any = {})             { return this.http.get<any>(this.url('/api/admin/users'), { params }); }
  adminUser(id: number)                    { return this.http.get<any>(this.url(`/api/admin/users/${id}`)); }
  setUserStatus(id: number, status: string) { return this.http.patch<any>(this.url(`/api/admin/users/${id}/status?status=${status}`), {}); }

  // ============ Email Templates (Admin) ============
  emailTemplates()                         { return this.http.get<any>(this.url('/api/admin/email-templates')); }
  emailTemplate(id: number)                { return this.http.get<any>(this.url(`/api/admin/email-templates/${id}`)); }
  createEmailTemplate(p: any)              { return this.http.post<any>(this.url('/api/admin/email-templates'), p); }
  updateEmailTemplate(id: number, p: any)  { return this.http.put<any>(this.url(`/api/admin/email-templates/${id}`), p); }
  deleteEmailTemplate(id: number)          { return this.http.delete<any>(this.url(`/api/admin/email-templates/${id}`)); }

  // ============ Permissions / Roles ============
  permissions()                            { return this.http.get<any>(this.url('/api/admin/permissions')); }
  permission(id: number)                   { return this.http.get<any>(this.url(`/api/admin/permissions/${id}`)); }
  createPermission(p: any)                 { return this.http.post<any>(this.url('/api/admin/permissions'), p); }
  updatePermission(id: number, p: any)     { return this.http.put<any>(this.url(`/api/admin/permissions/${id}`), p); }
  deletePermission(id: number)             { return this.http.delete<any>(this.url(`/api/admin/permissions/${id}`)); }
  rolePermissions(roleId: number)          { return this.http.get<any>(this.url(`/api/admin/roles/${roleId}/permissions`)); }

  // ============ System Config ============
  systemConfig()                           { return this.http.get<any>(this.url('/api/admin/system-config')); }
  systemConfigByKey(key: string)           { return this.http.get<any>(this.url(`/api/admin/system-config/${key}`)); }
  createSystemConfig(p: any)               { return this.http.post<any>(this.url('/api/admin/system-config'), p); }
  updateSystemConfig(id: number, p: any)   { return this.http.put<any>(this.url(`/api/admin/system-config/${id}`), p); }

  // ============ Commission Config (Admin) ============
  createCommissionConfig(p: any)           { return this.http.post<any>(this.url('/api/commissions/config'), p); }
  updateCommissionConfig(level: number, percentage: number) {
    return this.http.put<any>(this.url(`/api/commissions/config/${level}?percentage=${percentage}`), {});
  }
  triggerCommission(userId: number, vol: number, type: string) {
    return this.http.post<any>(this.url(`/api/commissions/calculate/${userId}?businessVolume=${vol}&type=${type}`), {});
  }

  // ============ Repurchase Config (Admin) ============
  createRepurchaseSlab(p: any)             { return this.http.post<any>(this.url('/api/repurchase/config'), p); }
  updateRepurchaseSlab(id: number, p: any) { return this.http.put<any>(this.url(`/api/repurchase/config/${id}`), p); }
  calculateRepurchase()                    { return this.http.post<any>(this.url('/api/repurchase/calculate'), {}); }
  repurchasePayout()                       { return this.http.post<any>(this.url('/api/repurchase/payout'), {}); }

  // ============ Expense Pools (Admin) ============
  expensePools()                                       { return this.http.get<any>(this.url('/api/expense-pools')); }
  expensePool(code: string)                            { return this.http.get<any>(this.url(`/api/expense-pools/${code}`)); }
  createExpensePool(p: any)                            { return this.http.post<any>(this.url('/api/expense-pools'), p); }
  updateExpensePoolPercentage(code: string, percentage: number) {
    const params = new HttpParams().set('percentage', String(percentage));
    return this.http.put<any>(this.url(`/api/expense-pools/${code}/percentage`), {}, { params });
  }
  setExpensePoolActive(code: string, active: boolean) {
    const params = new HttpParams().set('active', String(active));
    return this.http.put<any>(this.url(`/api/expense-pools/${code}/active`), {}, { params });
  }
  spendExpensePool(code: string, amount: number, reference?: string, description?: string) {
    let params = new HttpParams().set('amount', String(amount));
    if (reference) params = params.set('reference', reference);
    if (description) params = params.set('description', description);
    return this.http.post<any>(this.url(`/api/expense-pools/${code}/spend`), {}, { params });
  }
  expensePoolLedger(code: string, page = 0, size = 20) {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.http.get<any>(this.url(`/api/expense-pools/${code}/ledger`), { params });
  }

  // ============ Sub-User / Role ============
  addSubUser(p: any)                       { return this.http.post<any>(this.url('/api/role/addUser'), p); }
  resendOnboardingLink(p: any)             { return this.http.post<any>(this.url('/api/role/resend-user-onboarding-link'), p); }
  subUserDetail()                          { return this.http.get<any>(this.url('/api/role/sub-user-registered-detail')); }
  updateSubUserProfile(uid: number, p: any){ return this.http.put<any>(this.url(`/api/role/update-sub-user-profile/${uid}`), p); }
  updateSubUserDetails(uid: number, p: any){ return this.http.put<any>(this.url(`/api/role/update-sub-user-details/${uid}`), p); }
  patchRole(p: any)                        { return this.http.patch<any>(this.url('/api/role'), p); }
  getOrderCount()                          { return this.http.get<any>(this.url('/api/role/get-order-count')); }

  // ============ Products extras ============
  productBenefits(productId: number)       { return this.http.get<any>(this.url(`/api/products/${productId}/benefits`)); }
  productIngredients(productId: number)    { return this.http.get<any>(this.url(`/api/products/${productId}/ingredients`)); }
  addIngredient(productId: number, p: any) { return this.http.post<any>(this.url(`/api/products/${productId}/ingredients`), p); }
  updateIngredient(id: number, p: any)     { return this.http.put<any>(this.url(`/api/products/ingredients/${id}`), p); }
  deleteIngredient(id: number)             { return this.http.delete<any>(this.url(`/api/products/ingredients/${id}`)); }
  productReviews(productId: number)        { return this.http.get<any>(this.url(`/api/products/${productId}/reviews`)); }
  postReview(productId: number, p: any)    { return this.http.post<any>(this.url(`/api/products/${productId}/reviews`), p); }
  approveReview(id: number)                { return this.http.put<any>(this.url(`/api/products/reviews/${id}/approve`), {}); }

  // ============ Files ============
  uploadFile(file: File, category = 'general') {
    const fd = new FormData();
    fd.append('file', file);
    fd.append('category', category);
    return this.http.post<any>(this.url('/api/files/upload'), fd);
  }
  fileUrl(category: string, name: string)  { return this.url(`/api/files/${category}/${name}`); }
  deleteFile(category: string, name: string) { return this.http.delete<any>(this.url(`/api/files/${category}/${name}`)); }

  // ============ Ranks (admin) ============
  promoteRanks()                           { return this.http.post<any>(this.url('/api/ranks/me/promote'), {}); }


  // ============ Extra endpoints (for full coverage) ============

  // Cart management (extended)
  clearCart() {
    return this.http.delete<any>(this.url('/api/cart/clear'));
  }
  updateCartItem(cartId: number, payload: any) {
    return this.http.patch<any>(this.url(`/api/cart/items/${cartId}`), payload);
  }

  // Product management
  updateProduct(productId: number, payload: any) {
    return this.http.put<any>(this.url(`/api/products/${productId}`), payload);
  }

  // Commission detail
  commissionTransaction(txnId: number) {
    return this.http.get<any>(this.url(`/api/commissions/${txnId}`));
  }

  // Cooling off
  coolingOffStatusByOrder(orderId: number) {
    return this.http.get<any>(this.url(`/api/compliance/cooling-off/${orderId}`));
  }
  exerciseCoolingOffByOrder(orderId: number, payload: any) {
    return this.http.post<any>(this.url(`/api/compliance/cooling-off/${orderId}/exercise`), payload);
  }

  // Re-entry
  reEntry() {
    return this.http.get<any>(this.url('/api/reEntry'));
  }

  // Upload payment receipt (admin/registration)
  uploadPaymentReceipt(registrationPaymentRequest: any, files: File[]) {
    const fd = new FormData();
    fd.append('registrationPaymentRequest', new Blob([JSON.stringify(registrationPaymentRequest)], { type: 'application/json' }));
    files.forEach(f => fd.append('imageFile', f));
    return this.http.post<any>(this.url('/api/uploadPaymentReceipt'), fd);
  }
}
