import { Component, OnDestroy, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

type RegistrationRow = {
  id: number;
  utrNumber: string;
  paymentStatus: 'PENDING' | 'COMPLETED' | 'FAILED' | string;
  createdAt: string;
  updatedAt: string;
  amountToPay?: number;
  amountPaid?: number;
  amountPending?: number;
  fullyPaid?: boolean;
  // UI-only - admin types the verified amount in this row
  _amountInput?: number | null;
  _busy?: boolean;
  _kycOpen?: boolean;
  user: {
    id?: number;
    fname?: string;
    lname?: string;
    email?: string;
    mobileNumber?: string;
    referralCode?: string;
    status?: string;
    active?: boolean;
    kyc?: {
      status?: string;
      rejectionReason?: string;
      panNumber?: string;
      aadhaarNumber?: string;
      accountHolderName?: string;
      accountNumber?: string;
      ifscCode?: string;
      bankName?: string;
      docTypes?: string[];
    };
  };
};

@Component({
  selector: 'app-admin-approvals',
  templateUrl: './admin-approvals.component.html',
  styleUrls: ['./admin-approvals.component.css']
})
export class AdminApprovalsComponent implements OnInit, OnDestroy {
  selectedTab: 'registrations' | 'withdrawals' | 'pending' = 'registrations';

  // Withdrawals
  withdrawals: any[] = [];
  pending: any[] = [];

  // Registrations
  registrations: RegistrationRow[] = [];
  registrationFilter: '' | 'PENDING' | 'COMPLETED' | 'FAILED' = 'PENDING';
  loadingRegs = false;

  // Verify-by-UTR widget
  utrInput = '';
  utrAmountInput: number | null = null;
  utrBusy = false;

  message = '';

  // KYC document preview cache. The KYC endpoint is guarded by an admin role
  // and the AuthInterceptor only attaches the Bearer token to HttpClient
  // requests, so plain <img src>/<a href> hits get 401. We fetch each doc
  // once through HttpClient, build a blob: URL, and reuse it.
  kycDocs: {
    [key: string]: {
      objectUrl: string;
      safeUrl: SafeUrl;
      contentType: string;
      isPdf: boolean;
      loading: boolean;
      error?: string;
    };
  } = {};

  constructor(
    private api: BackendApiService,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.load();
  }

  switchTab(t: 'registrations' | 'withdrawals' | 'pending') {
    this.selectedTab = t;
    this.load();
  }

  load() {
    if (this.selectedTab === 'registrations') {
      this.loadRegistrations();
    } else if (this.selectedTab === 'withdrawals') {
      this.loadWithdrawals();
    } else if (this.selectedTab === 'pending') {
      this.loadPendingPayouts();
    }
  }

  loadWithdrawals() {
    this.api.allWithdrawals().subscribe(
      (r: any) => this.withdrawals = r?.data || [],
      () => this.withdrawals = []
    );
  }

  loadPendingPayouts() {
    // Load only PENDING withdrawal requests for payout processing
    this.api.allWithdrawals().subscribe(
      (r: any) => {
        const all = r?.data || [];
        this.pending = all.filter((w: any) => w.requestStatus === 'PENDING');
      },
      () => this.pending = []
    );
  }

  // ===== Registrations =====
  loadRegistrations() {
    this.loadingRegs = true;
    const obs = this.registrationFilter
      ? this.api.allRegistrations(this.registrationFilter)
      : this.api.allRegistrations();
    obs.subscribe(
      (r: any) => {
        this.registrations = r?.data || [];
        this.loadingRegs = false;
      },
      err => {
        this.loadingRegs = false;
        this.toastr.error(err?.error?.message || 'Could not load registrations.', 'Failed');
      }
    );
  }

  setRegFilter(f: '' | 'PENDING' | 'COMPLETED' | 'FAILED') {
    this.registrationFilter = f;
    this.loadRegistrations();
  }

  approveRegistration(row: RegistrationRow) {
    if (!confirm(`Approve registration of ${row.user?.fname || ''} ${row.user?.lname || ''}?\n\nThe user will be marked ACTIVE and the payment will be flagged COMPLETED.`)) {
      return;
    }
    this.api.approveRegistration(row.id).subscribe(
      (res: any) => {
        this.toastr.success(
          `${row.user?.fname || 'User'} is now active.`,
          '✅ Registration approved'
        );
        this.loadRegistrations();
      },
      err => this.toastr.error(err?.error?.message || 'Could not approve.', 'Failed')
    );
  }

  rejectRegistration(row: RegistrationRow) {
    const reason = prompt('Reason for rejection (optional):') || '';
    if (reason === null) return;
    this.api.rejectRegistration(row.id, reason).subscribe(
      () => {
        this.toastr.warning(
          `${row.user?.fname || 'User'} kept inactive.`,
          'Registration rejected'
        );
        this.loadRegistrations();
      },
      err => this.toastr.error(err?.error?.message || 'Could not reject.', 'Failed')
    );
  }

  // Verify by UTR (admin types/pastes the UTR after confirming bank credit)
  verifyByUtr() {
    const utr = (this.utrInput || '').trim();
    if (!utr) {
      this.toastr.warning('Enter the UTR / Transaction ID to verify.', 'UTR required');
      return;
    }
    const amount = this.utrAmountInput;
    if (amount == null || isNaN(Number(amount))) {
      this.toastr.warning('Enter the verified amount paid.', 'Amount required');
      return;
    }
    if (Number(amount) < 0) {
      this.toastr.warning('Amount cannot be negative.', 'Invalid amount');
      return;
    }
    this.utrBusy = true;
    this.api.approveRegistrationByUtr(utr, Number(amount)).subscribe(
      (res: any) => {
        this.utrBusy = false;
        const row = res?.data || {};
        const u = row.user;
        const fullName = u ? `${u.fname || ''} ${u.lname || ''}`.trim() : 'User';
        if (row.fullyPaid) {
          this.toastr.success(
            `${fullName} is now active. Amount credited: ₹${Number(row.amountPaid || 0).toLocaleString()}`,
            '✅ Payment verified'
          );
        } else {
          this.toastr.warning(
            `Recorded ₹${Number(row.amountPaid || 0).toLocaleString()} for ${fullName}. ₹${Number(row.amountPending || 0).toLocaleString()} still pending — user kept inactive.`,
            'Partial payment',
            { timeOut: 6000 }
          );
        }
        this.utrInput = '';
        this.utrAmountInput = null;
        this.loadRegistrations();
      },
      err => {
        this.utrBusy = false;
        this.toastr.error(
          err?.error?.message || 'Could not verify this UTR.',
          'Verification failed'
        );
      }
    );
  }

  /**
   * Inline amount edit on a card. Admin enters the actually-credited amount,
   * we update the registration; if it covers the dues the user is activated.
   */
  saveRowAmount(row: RegistrationRow) {
    const amount = row._amountInput;
    if (amount == null || isNaN(Number(amount))) {
      this.toastr.warning('Enter the verified amount paid.', 'Amount required');
      return;
    }
    if (Number(amount) < 0) {
      this.toastr.warning('Amount cannot be negative.', 'Invalid amount');
      return;
    }
    row._busy = true;
    this.api.updateRegistrationPayment({
      utrNumber: row.utrNumber,
      amountPaid: Number(amount)
    }).subscribe(
      (res: any) => {
        row._busy = false;
        const updated = res?.data || {};
        const fullyPaid = !!updated.fullyPaid;
        if (fullyPaid) {
          this.toastr.success(
            `Payment fully verified. ${row.user?.fname || 'User'} is now active.`,
            '✅ Activated'
          );
        } else {
          this.toastr.warning(
            `Recorded ₹${Number(updated.amountPaid || 0).toLocaleString()}. ₹${Number(updated.amountPending || 0).toLocaleString()} still pending.`,
            'Partial payment',
            { timeOut: 5000 }
          );
        }
        this.loadRegistrations();
      },
      err => {
        row._busy = false;
        this.toastr.error(err?.error?.message || 'Could not update payment.', 'Failed');
      }
    );
  }

  // Active / inactive toggle on the user (works on any user even after approval)
  toggleActive(row: RegistrationRow, event: any) {
    if (!row.user?.id) return;
    const desired = !!event?.target?.checked;
    const previous = !!row.user.active;
    // optimistic update
    row.user.active = desired;
    this.api.toggleUserActive(row.user.id, desired).subscribe(
      () => {
        this.toastr.success(
          desired ? 'User marked Active.' : 'User marked Inactive.',
          'Status updated'
        );
      },
      err => {
        row.user.active = previous; // revert
        this.toastr.error(err?.error?.message || 'Could not update status.', 'Failed');
      }
    );
  }

  // ===== Withdrawals =====
  approve(w: any) {
    const utr = prompt('Enter UTR / Transaction Number for this payout:');
    if (!utr) return;
    this.api.approveWithdrawal(w.id, utr).subscribe(
      () => {
        this.message = 'Approved.';
        this.toastr.success('Withdrawal approved.', 'Success');
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed.';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }
  reject(w: any) {
    const reason = prompt('Reason for rejection:');
    if (reason === null) return;
    this.api.rejectWithdrawal(w.id, reason || 'Rejected by admin').subscribe(
      () => {
        this.message = 'Rejected.';
        this.toastr.warning('Withdrawal rejected.', 'Rejected');
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed.';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }
  processPayouts() {
    this.api.processPayouts().subscribe(
      () => {
        this.message = 'Payout job triggered.';
        this.toastr.success('Payout job triggered.', 'Success');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not trigger payout job.', 'Failed')
    );
  }

  approvePayout(p: any) {
    const utr = (p._utr || '').trim();
    if (!utr) {
      this.toastr.warning('Enter the UTR / Transaction number.', 'UTR required');
      return;
    }
    this.api.approveWithdrawal(p.id, utr).subscribe(
      () => {
        this.toastr.success(`Payout approved for ₹${p.netAmount || p.amount}.`, '✅ Paid');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not approve.', 'Failed')
    );
  }

  rejectPayout(p: any) {
    const reason = prompt('Reason for rejection:');
    if (reason === null) return;
    this.api.rejectWithdrawal(p.id, reason || 'Rejected by admin').subscribe(
      () => {
        this.toastr.warning('Payout rejected and amount refunded to wallet.', 'Rejected');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not reject.', 'Failed')
    );
  }

  reviewKyc(userId: number, status: string, reason?: string) {
    const obs = status === 'APPROVED'
      ? this.api.approveKyc(userId)
      : this.api.rejectKyc(userId, reason);
    obs.subscribe(
      () => {
        this.message = `KYC ${status.toLowerCase()}.`;
        this.toastr.success(`KYC ${status.toLowerCase()}.`, 'Updated');
        this.loadRegistrations();
      },
      err => this.toastr.error(err?.error?.message || 'Failed.', 'KYC update failed')
    );
  }

  /** Direct URL for an admin to view the KYC doc image inline / in a new tab. */
  kycDocUrl(userId: number, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF'): string {
    return this.api.kycDocUrl(userId, docType);
  }

  /**
   * Cache key for a KYC doc preview.
   */
  private kycKey(userId: number, docType: string): string {
    return `${userId}:${docType}`;
  }

  /**
   * Lazily fetch a KYC doc through HttpClient (so the AuthInterceptor
   * attaches the admin Bearer token), turn the response Blob into a
   * blob: object URL and cache it. Called from the template via
   * (mouseenter) / explicit "Load" button so we don't hammer the API
   * for every row on page load.
   */
  loadKycDoc(userId: number | undefined, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF'): void {
    if (!userId) return;
    const key = this.kycKey(userId, docType);
    const existing = this.kycDocs[key];
    if (existing && (existing.objectUrl || existing.loading)) return;

    this.kycDocs[key] = {
      objectUrl: '',
      safeUrl: '' as any,
      contentType: '',
      isPdf: false,
      loading: true
    };

    this.api.kycDocBlob(userId, docType).subscribe(
      (blob: Blob) => {
        const objectUrl = URL.createObjectURL(blob);
        const isPdf = (blob.type || '').toLowerCase().includes('pdf');
        this.kycDocs[key] = {
          objectUrl,
          safeUrl: this.sanitizer.bypassSecurityTrustUrl(objectUrl),
          contentType: blob.type || '',
          isPdf,
          loading: false
        };
      },
      err => {
        const msg = err?.status === 401 || err?.status === 403
          ? 'Not authorised to view this document.'
          : err?.status === 404
            ? 'Document not found.'
            : 'Could not load this document.';
        this.kycDocs[key] = {
          objectUrl: '',
          safeUrl: '' as any,
          contentType: '',
          isPdf: false,
          loading: false,
          error: msg
        };
        this.toastr.error(msg, 'KYC document');
      }
    );
  }

  /** Convenience accessors used by the template. */
  kycDocState(userId: number | undefined, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF') {
    if (!userId) return null;
    return this.kycDocs[this.kycKey(userId, docType)] || null;
  }

  /**
   * Open a fetched KYC doc in a new tab. We can't use a plain <a href>
   * for the secured endpoint, so we open the cached blob: URL instead.
   * If it hasn't been loaded yet, fetch it first and then open.
   */
  openKycDoc(userId: number | undefined, docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF', ev?: Event): void {
    if (ev) { ev.preventDefault(); ev.stopPropagation(); }
    if (!userId) return;
    const key = this.kycKey(userId, docType);
    const cached = this.kycDocs[key];
    if (cached?.objectUrl) {
      window.open(cached.objectUrl, '_blank', 'noopener');
      return;
    }
    this.api.kycDocBlob(userId, docType).subscribe(
      (blob: Blob) => {
        const objectUrl = URL.createObjectURL(blob);
        const isPdf = (blob.type || '').toLowerCase().includes('pdf');
        this.kycDocs[key] = {
          objectUrl,
          safeUrl: this.sanitizer.bypassSecurityTrustUrl(objectUrl),
          contentType: blob.type || '',
          isPdf,
          loading: false
        };
        window.open(objectUrl, '_blank', 'noopener');
      },
      err => {
        const msg = err?.status === 401 || err?.status === 403
          ? 'Not authorised to view this document.'
          : err?.status === 404
            ? 'Document not found.'
            : 'Could not load this document.';
        this.toastr.error(msg, 'KYC document');
      }
    );
  }

  /** Toggle a row's KYC accordion and prefetch its docs the first time. */
  toggleKyc(row: RegistrationRow): void {
    row._kycOpen = !row._kycOpen;
    if (row._kycOpen && row.user?.id) {
      const types = (row.user.kyc?.docTypes || []) as Array<'PAN' | 'AADHAAR' | 'BANK_PROOF'>;
      types.forEach(t => this.loadKycDoc(row.user!.id!, t));
    }
  }

  ngOnDestroy(): void {
    // Revoke any blob: URLs we created to avoid leaks.
    Object.values(this.kycDocs).forEach(d => {
      if (d?.objectUrl) {
        try { URL.revokeObjectURL(d.objectUrl); } catch { /* ignore */ }
      }
    });
    this.kycDocs = {};
  }

  rejectKycPrompt(row: RegistrationRow) {
    const reason = prompt('Reason for KYC rejection:') || '';
    if (!reason || !row.user?.id) return;
    this.reviewKyc(row.user.id, 'REJECTED', reason);
  }

  updateClaim(id: number, delivered: boolean) {
    this.api.updateClaimStatus(id, delivered, '').subscribe(
      () => {
        this.message = 'Claim status updated.';
        this.toastr.success('Claim status updated.', 'Success');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not update claim.', 'Failed')
    );
  }
}
