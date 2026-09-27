import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { NetworkService } from '../_services/network.service';

@Component({
  selector: 'app-admin-repurchase',
  templateUrl: './admin-repurchase.component.html',
  styleUrls: ['./admin-repurchase.component.css']
})
export class AdminRepurchaseComponent implements OnInit {

  selectedTab = 'transactions';

  // Record form
  recordForm: FormGroup;
  recording = false;

  // All transactions
  transactions: any[] = [];
  filteredTransactions: any[] = [];
  searchText = '';
  loadingTxns = true;

  // Slab config
  slabs: any[] = [];
  loadingSlabs = true;
  slabForm: FormGroup;
  editingSlabId: number | null = null;

  // Actions
  calculating = false;
  payingOut = false;

  displayedColumns = ['id', 'userName', 'amount', 'repurchaseDate', 'bonusPeriod', 'bonusCalculated'];

  constructor(
    private fb: FormBuilder,
    private networkService: NetworkService,
    private toastr: ToastrService
  ) {
    this.recordForm = this.fb.group({
      userId: ['', [Validators.required, Validators.min(1)]],
      amount: ['', [Validators.required, Validators.min(1)]]
    });
    this.slabForm = this.fb.group({
      minAmount: ['', [Validators.required, Validators.min(0)]],
      maxAmount: ['', [Validators.required, Validators.min(1)]],
      bonusPercentage: ['', [Validators.required, Validators.min(0.1)]]
    });
  }

  ngOnInit(): void {
    this.loadTransactions();
    this.loadSlabs();
  }

  switchTab(tab: string): void {
    this.selectedTab = tab;
  }

  // ===== Transactions =====
  loadTransactions(): void {
    this.loadingTxns = true;
    this.networkService.adminGetAllRepurchases().subscribe(
      (res: any) => {
        this.transactions = res?.data || [];
        this.filteredTransactions = [...this.transactions];
        this.loadingTxns = false;
      },
      () => {
        this.transactions = [];
        this.filteredTransactions = [];
        this.loadingTxns = false;
      }
    );
  }

  filterTransactions(): void {
    const q = this.searchText.toLowerCase();
    this.filteredTransactions = this.transactions.filter(t =>
      (t.user?.firstName || '').toLowerCase().includes(q) ||
      (t.user?.lastName || '').toLowerCase().includes(q) ||
      (t.user?.email || '').toLowerCase().includes(q) ||
      (t.bonusPeriod || '').includes(q) ||
      String(t.user?.id || '').includes(q)
    );
  }

  getUserName(t: any): string {
    if (!t.user) return 'N/A';
    return (t.user.firstName || '') + ' ' + (t.user.lastName || '') + ' (ID: ' + t.user.id + ')';
  }

  // ===== Record Repurchase =====
  recordRepurchase(): void {
    if (this.recordForm.invalid) {
      this.recordForm.markAllAsTouched();
      return;
    }
    this.recording = true;
    const { userId, amount } = this.recordForm.value;
    this.networkService.adminRecordRepurchase(Number(userId), Number(amount)).subscribe(
      (res: any) => {
        this.toastr.success('Repurchase of ₹' + amount + ' recorded for user ' + userId, 'Success');
        this.recordForm.reset();
        this.recording = false;
        this.loadTransactions();
      },
      (err: any) => {
        this.toastr.error(err?.error?.message || 'Failed to record repurchase', 'Error');
        this.recording = false;
      }
    );
  }

  // ===== Slab Config =====
  loadSlabs(): void {
    this.loadingSlabs = true;
    this.networkService.getRepurchaseConfig().subscribe(
      (res: any) => {
        this.slabs = (res?.data || []).sort((a: any, b: any) => a.minAmount - b.minAmount);
        this.loadingSlabs = false;
      },
      () => {
        this.slabs = [];
        this.loadingSlabs = false;
      }
    );
  }

  editSlab(slab: any): void {
    this.editingSlabId = slab.id;
    this.slabForm.patchValue({
      minAmount: slab.minAmount,
      maxAmount: slab.maxAmount,
      bonusPercentage: slab.bonusPercentage
    });
  }

  cancelEdit(): void {
    this.editingSlabId = null;
    this.slabForm.reset();
  }

  saveSlab(): void {
    if (this.slabForm.invalid) {
      this.slabForm.markAllAsTouched();
      return;
    }
    const payload = this.slabForm.value;
    if (this.editingSlabId) {
      this.networkService.getRepurchaseConfig().subscribe(); // placeholder
      // Use backend-api service for update
    }
    this.cancelEdit();
    this.loadSlabs();
  }

  // ===== Actions =====
  calculateBonus(): void {
    this.calculating = true;
    this.networkService.calculateRepurchaseBonus().subscribe(
      (res: any) => {
        this.toastr.success(res?.data || 'Bonus calculated and credited', 'Calculation Complete');
        this.calculating = false;
        this.loadTransactions();
      },
      (err: any) => {
        this.toastr.error(err?.error?.message || 'Calculation failed', 'Error');
        this.calculating = false;
      }
    );
  }

  markPayout(): void {
    this.payingOut = true;
    this.networkService.repurchasePayout().subscribe(
      (res: any) => {
        this.toastr.success(res?.data || 'Payout cycle marked', 'Payout');
        this.payingOut = false;
      },
      (err: any) => {
        this.toastr.error(err?.error?.message || 'Payout marking failed', 'Error');
        this.payingOut = false;
      }
    );
  }
}
