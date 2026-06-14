import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-email-templates',
  templateUrl: './admin-email-templates.component.html',
  styleUrls: ['./admin-email-templates.component.css']
})
export class AdminEmailTemplatesComponent implements OnInit {
  templates: any[] = [];
  showForm = false;
  editingId: number | null = null;
  form: FormGroup;
  message = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      templateKey: ['', Validators.required],
      subject: ['', Validators.required],
      body: ['', Validators.required],
      isActive: [true]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    this.api.emailTemplates().subscribe(
      (r: any) => this.templates = r?.data || [],
      () => this.templates = []
    );
  }

  newOne() { this.showForm = true; this.editingId = null; this.form.reset({ isActive: true }); }
  edit(t: any) { this.showForm = true; this.editingId = t.id; this.form.patchValue(t); }
  cancel() { this.showForm = false; this.editingId = null; }

  save() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    const handler = this.editingId ? this.api.updateEmailTemplate(this.editingId, this.form.value) : this.api.createEmailTemplate(this.form.value);
    handler.subscribe(
      () => {
        this.message = this.editingId ? 'Updated' : 'Created';
        this.toastr.success(this.editingId ? 'Template updated.' : 'Template created.', 'Success');
        this.showForm = false;
        this.load();
      },
      err => {
        this.message = err?.error?.message || 'Failed';
        this.toastr.error(this.message, 'Failed');
      }
    );
  }

  remove(id: number) {
    if (!confirm('Delete this template?')) return;
    this.api.deleteEmailTemplate(id).subscribe(
      () => { this.toastr.success('Template deleted.', 'Removed'); this.load(); },
      err => this.toastr.error(err?.error?.message || 'Delete failed.', 'Failed')
    );
  }
}
