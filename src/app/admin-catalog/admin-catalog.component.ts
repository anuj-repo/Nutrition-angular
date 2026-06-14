import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-catalog',
  templateUrl: './admin-catalog.component.html',
  styleUrls: ['./admin-catalog.component.css']
})
export class AdminCatalogComponent implements OnInit {
  selectedTab = 'investment';

  investmentPlans: any[] = [];
  insurancePlans: any[] = [];
  announcements: any[] = [];

  invForm: FormGroup;
  insForm: FormGroup;
  annForm: FormGroup;
  editingAnnId: number | null = null;
  message = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.invForm = this.fb.group({
      planName: ['', Validators.required],
      interestRate: ['', Validators.required],
      tenureMonths: ['', Validators.required],
      minInvestment: [10000, Validators.required],
      maxInvestment: [1000000],
      isActive: [true]
    });
    this.insForm = this.fb.group({
      planName: ['', Validators.required],
      coverageAmount: ['', Validators.required],
      eligibilityCriteria: [''],
      description: [''],
      isActive: [true]
    });
    this.annForm = this.fb.group({
      title: ['', Validators.required],
      message: ['', Validators.required],
      priority: ['NORMAL'],
      isActive: [true]
    });
  }

  ngOnInit(): void { this.load(); }

  switchTab(t: string) { this.selectedTab = t; }

  load() {
    this.api.investmentPlans().subscribe((r: any) => this.investmentPlans = r?.data || []);
    this.api.insPlans().subscribe((r: any) => this.insurancePlans = r?.data || []);
    this.api.announcements().subscribe((r: any) => this.announcements = r?.data || []);
  }

  saveInvPlan() {
    if (this.invForm.invalid) { this.invForm.markAllAsTouched(); return; }
    this.api.createInvestmentPlan(this.invForm.value).subscribe(
      () => {
        this.message = 'Investment plan created.';
        this.toastr.success(this.message, 'Created');
        this.invForm.reset({ minInvestment: 10000, isActive: true });
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Save failed.', 'Failed')
    );
  }
  saveInsPlan() {
    if (this.insForm.invalid) { this.insForm.markAllAsTouched(); return; }
    this.api.insCreatePlan(this.insForm.value).subscribe(
      () => {
        this.message = 'Insurance plan created.';
        this.toastr.success(this.message, 'Created');
        this.insForm.reset({ isActive: true });
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Save failed.', 'Failed')
    );
  }

  newAnn() { this.editingAnnId = null; this.annForm.reset({ priority: 'NORMAL', isActive: true }); }
  editAnn(a: any) { this.editingAnnId = a.id; this.annForm.patchValue(a); }
  saveAnn() {
    if (this.annForm.invalid) { this.annForm.markAllAsTouched(); return; }
    const handler = this.editingAnnId ? this.api.updateAnnouncement(this.editingAnnId, this.annForm.value) : this.api.createAnnouncement(this.annForm.value);
    handler.subscribe(
      () => {
        this.message = 'Saved.';
        this.toastr.success('Announcement saved.', 'Success');
        this.editingAnnId = null;
        this.annForm.reset({ priority: 'NORMAL', isActive: true });
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Save failed.', 'Failed')
    );
  }
  deleteAnn(id: number) {
    if (!confirm('Delete this announcement?')) return;
    this.api.deleteAnnouncement(id).subscribe(
      () => { this.toastr.success('Announcement deleted.', 'Removed'); this.load(); },
      err => this.toastr.error(err?.error?.message || 'Delete failed.', 'Failed')
    );
  }
}
