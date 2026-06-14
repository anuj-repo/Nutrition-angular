import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-settings',
  templateUrl: './admin-settings.component.html',
  styleUrls: ['./admin-settings.component.css']
})
export class AdminSettingsComponent implements OnInit {
  selectedTab = 'permissions';
  permissions: any[] = [];
  systemConfig: any[] = [];

  permForm: FormGroup;
  cfgForm: FormGroup;

  editingPermId: number | null = null;
  editingCfgId: number | null = null;
  message = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.permForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      module: ['']
    });
    this.cfgForm = this.fb.group({
      configKey: ['', Validators.required],
      configValue: ['', Validators.required],
      description: ['']
    });
  }

  ngOnInit(): void { this.load(); }

  switchTab(t: string) { this.selectedTab = t; }

  load() {
    this.api.permissions().subscribe(
      (r: any) => this.permissions = r?.data || [],
      () => this.permissions = []
    );
    this.api.systemConfig().subscribe(
      (r: any) => this.systemConfig = r?.data || [],
      () => this.systemConfig = []
    );
  }

  // Permissions
  newPerm() { this.editingPermId = null; this.permForm.reset(); }
  editPerm(p: any) { this.editingPermId = p.id; this.permForm.patchValue(p); }
  savePerm() {
    if (this.permForm.invalid) { this.permForm.markAllAsTouched(); return; }
    const handler = this.editingPermId
      ? this.api.updatePermission(this.editingPermId, this.permForm.value)
      : this.api.createPermission(this.permForm.value);
    handler.subscribe(
      () => {
        this.toastr.success(this.editingPermId ? 'Permission updated.' : 'Permission created.', 'Success');
        this.editingPermId = null;
        this.permForm.reset();
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Save failed.', 'Failed')
    );
  }
  removePerm(id: number) {
    if (!confirm('Delete this permission?')) return;
    this.api.deletePermission(id).subscribe(
      () => { this.toastr.success('Permission deleted.', 'Removed'); this.load(); },
      err => this.toastr.error(err?.error?.message || 'Delete failed.', 'Failed')
    );
  }

  // System Config
  newCfg() { this.editingCfgId = null; this.cfgForm.reset(); }
  editCfg(c: any) { this.editingCfgId = c.id; this.cfgForm.patchValue(c); }
  saveCfg() {
    if (this.cfgForm.invalid) { this.cfgForm.markAllAsTouched(); return; }
    const handler = this.editingCfgId
      ? this.api.updateSystemConfig(this.editingCfgId, this.cfgForm.value)
      : this.api.createSystemConfig(this.cfgForm.value);
    handler.subscribe(
      () => {
        this.toastr.success(this.editingCfgId ? 'Config updated.' : 'Config created.', 'Success');
        this.editingCfgId = null;
        this.cfgForm.reset();
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Save failed.', 'Failed')
    );
  }
}
