import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';
import { NetworkService } from '../_services/network.service';

@Component({
  selector: 'app-admin-configs',
  templateUrl: './admin-configs.component.html',
  styleUrls: ['./admin-configs.component.css']
})
export class AdminConfigsComponent implements OnInit {
  selectedTab = 'commission';
  commissionConfig: any[] = [];
  repurchaseConfig: any[] = [];
  rewardTiers: any[] = [];

  commForm: FormGroup;
  repForm: FormGroup;
  rewForm: FormGroup;

  editingRepId: number | null = null;
  editingRewId: number | null = null;
  message = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private network: NetworkService, private toastr: ToastrService) {
    this.commForm = this.fb.group({
      level: ['', Validators.required],
      percentage: ['', Validators.required],
      rankTitle: ['']
    });
    this.repForm = this.fb.group({
      minAmount: ['', Validators.required],
      maxAmount: ['', Validators.required],
      bonusPercentage: ['', Validators.required],
      isActive: [true]
    });
    this.rewForm = this.fb.group({
      rewardName: ['', Validators.required],
      rewardCategory: ['DIRECT', Validators.required],
      rewardType: ['GIFT'],
      requiredCount: ['', Validators.required],
      rewardValue: [''],
      rewardDescription: [''],
      isActive: [true]
    });
  }

  ngOnInit(): void { this.load(); }

  switchTab(t: string) { this.selectedTab = t; }

  load() {
    this.network.getCommissionConfig().subscribe((r: any) => this.commissionConfig = r?.data || []);
    this.network.getRepurchaseConfig().subscribe((r: any) => this.repurchaseConfig = r?.data || []);
    this.network.getRewardTiers().subscribe((r: any) => this.rewardTiers = r?.data || []);
  }

  saveCommission() {
    if (this.commForm.invalid) { this.commForm.markAllAsTouched(); return; }
    this.api.createCommissionConfig(this.commForm.value).subscribe(
      () => {
        this.message = 'Saved';
        this.toastr.success('Commission config saved.', 'Success');
        this.commForm.reset();
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }

  updateCommissionRow(c: any) {
    this.api.updateCommissionConfig(c.level, Number(c.percentage)).subscribe(
      () => {
        this.message = `Level ${c.level} updated`;
        this.toastr.success(`Level ${c.level} updated.`, 'Success');
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }

  editRep(r: any) { this.editingRepId = r.id; this.repForm.patchValue(r); }
  saveRepurchase() {
    if (this.repForm.invalid) { this.repForm.markAllAsTouched(); return; }
    const handler = this.editingRepId ? this.api.updateRepurchaseSlab(this.editingRepId, this.repForm.value) : this.api.createRepurchaseSlab(this.repForm.value);
    handler.subscribe(
      () => {
        this.message = 'Saved';
        this.toastr.success('Repurchase slab saved.', 'Success');
        this.editingRepId = null;
        this.repForm.reset({ isActive: true });
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }
  newRep() { this.editingRepId = null; this.repForm.reset({ isActive: true }); }

  editRew(r: any) { this.editingRewId = r.id; this.rewForm.patchValue(r); }
  saveReward() {
    if (this.rewForm.invalid) { this.rewForm.markAllAsTouched(); return; }
    const handler = this.editingRewId ? this.api.updateRewardTier(this.editingRewId, this.rewForm.value) : this.api.createRewardTier(this.rewForm.value);
    handler.subscribe(
      () => {
        this.message = 'Saved';
        this.toastr.success('Reward tier saved.', 'Success');
        this.editingRewId = null;
        this.rewForm.reset({ rewardCategory: 'DIRECT', rewardType: 'GIFT', isActive: true });
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }
  newRew() { this.editingRewId = null; this.rewForm.reset({ rewardCategory: 'DIRECT', rewardType: 'GIFT', isActive: true }); }
  deleteReward(id: number) {
    if (!confirm('Delete this reward tier?')) return;
    this.api.deleteRewardTier(id).subscribe(
      () => { this.toastr.success('Reward tier deleted.', 'Removed'); this.load(); },
      err => this.toastr.error(err?.error?.message || 'Delete failed.', 'Failed')
    );
  }

  runRepurchaseCalc() {
    this.api.calculateRepurchase().subscribe(
      (r: any) => {
        this.message = r?.data || 'Done';
        this.toastr.success(this.message, 'Repurchase calc');
      },
      err => this.toastr.error(err?.error?.message || 'Calc failed.', 'Failed')
    );
  }
  triggerPayout() {
    this.api.repurchasePayout().subscribe(
      (r: any) => {
        this.message = r?.data || 'Done';
        this.toastr.success(this.message, 'Payout triggered');
      },
      err => this.toastr.error(err?.error?.message || 'Payout failed.', 'Failed')
    );
  }
}
