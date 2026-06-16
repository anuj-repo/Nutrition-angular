import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

/** Backend Status enum: "0"=INACTIVE, "1"=ACTIVE, "2"=DELETED, "3"=ARCHIVED, "4"=DEMO. */
const STATUS_LABELS: Record<string, string> = {
  '0': 'Inactive',
  '1': 'Active',
  '2': 'Deleted',
  '3': 'Archived',
  '4': 'Demo'
};

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: any[] = [];
  selected: any = null;
  loadingDetails = false;
  searchKeyword = '';
  selectedStatus = '';
  page = 0; size = 50;
  message = '';

  // Payout popup
  payoutUser: any = null;
  payoutAmount: number | null = null;
  payoutUtr = '';
  payoutRemarks = '';
  payoutMsg = '';
  payoutOk = false;
  payoutBusy = false;

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void { this.load(); }

  load() {
    const params: any = { page: this.page, size: this.size };
    if (this.searchKeyword) params.search = this.searchKeyword;
    if (this.selectedStatus) params.status = this.selectedStatus;
    this.api.adminUsers(params).subscribe(
      (r: any) => this.users = r?.data || [],
      () => this.users = []
    );
  }

  open(u: any) {
    this.loadingDetails = true;
    this.selected = u; // show drawer immediately with row data
    this.api.adminUser(u.id).subscribe(
      (r: any) => { this.selected = r?.data || u; this.loadingDetails = false; },
      () => { this.loadingDetails = false; }
    );
  }

  close() { this.selected = null; }

  /** Toggle between ACTIVE ("1") and INACTIVE ("0"). */
  setStatus(u: any, status: string) {
    this.api.setUserStatus(u.id, status).subscribe(
      (r: any) => {
        u.status = r?.data?.status || status;
        if (this.selected && this.selected.id === u.id) this.selected.status = u.status;
        this.message = 'Status updated.';
        this.toastr.success('Status updated.', 'Success');
      },
      err => {
        this.message = err?.error?.message || 'Update failed.';
        this.toastr.error(this.message, 'Update failed');
      }
    );
  }

  toggleActive(u: any) {
    const next = u.status === '1' ? '0' : '1';
    this.setStatus(u, next);
  }

  // ---------- Payout popup ----------

  openPayout(u: any): void {
    this.payoutUser = u;
    this.payoutAmount = null;
    this.payoutUtr = '';
    this.payoutRemarks = '';
    this.payoutMsg = '';
    this.payoutOk = false;
  }

  closePayout(): void {
    this.payoutUser = null;
  }

  submitPayout(): void {
    if (!this.payoutUser || !this.payoutAmount || !this.payoutUtr) return;
    this.payoutBusy = true;
    this.payoutMsg = '';

    this.api.adminPayout(this.payoutUser.id, this.payoutAmount, this.payoutUtr, this.payoutRemarks || undefined).subscribe(
      (res: any) => {
        const data = res?.data || {};
        const newBal = data.newBalance != null ? this.inr(Number(data.newBalance)) : '₹0';
        this.payoutMsg = `✅ Payout of ₹${this.payoutAmount} recorded. New balance: ${newBal}. UTR: ${this.payoutUtr}`;
        this.payoutOk = true;
        this.payoutBusy = false;
        this.toastr.success(`Payout to ${this.payoutUser.fname} recorded.`, 'Success');
        this.load();
      },
      err => {
        this.payoutMsg = '❌ ' + (err?.error?.message || 'Payout failed.');
        this.payoutOk = false;
        this.payoutBusy = false;
      }
    );
  }

  // ---------- Display helpers ----------

  statusLabel(status: any): string {
    if (status == null || status === '') return '—';
    return STATUS_LABELS[String(status)] || String(status);
  }

  statusClass(status: any): string {
    return (this.statusLabel(status) || '').toLowerCase();
  }

  /** dob arrives as [yyyy, m, d]. */
  formatDob(dob: any): string {
    if (!dob) return '—';
    if (Array.isArray(dob) && dob.length >= 3) {
      const [y, m, d] = dob;
      return new Date(y, (m || 1) - 1, d || 1).toLocaleDateString();
    }
    if (typeof dob === 'string' || typeof dob === 'number') {
      const dt = new Date(dob);
      return isNaN(dt.getTime()) ? String(dob) : dt.toLocaleDateString();
    }
    return String(dob);
  }

  formatDate(value: any): string {
    if (value == null || value === '') return '—';
    const dt = new Date(value);
    return isNaN(dt.getTime()) ? String(value) : dt.toLocaleString();
  }

  fullName(u: any): string {
    if (!u) return '';
    return [u.salutation, u.fname, u.lname].filter(Boolean).join(' ').trim();
  }

  display(value: any): string {
    return (value == null || value === '') ? '—' : String(value);
  }

  formatCurrency(value: any): string {
    if (value == null || value === '') return '—';
    const num = Number(value);
    if (isNaN(num)) return String(value);
    return num.toLocaleString('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 });
  }

  isYes(value: any): boolean { return value === '1' || value === 1 || value === true || value === 'YES'; }
}
