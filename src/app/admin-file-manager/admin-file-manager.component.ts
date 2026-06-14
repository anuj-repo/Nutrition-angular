import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-file-manager',
  templateUrl: './admin-file-manager.component.html',
  styleUrls: ['./admin-file-manager.component.css']
})
export class AdminFileManagerComponent {
  category = 'general';
  fileName = '';
  uploadedFiles: { category: string; name: string; url: string }[] = [];
  uploading = false;
  successMessage = ''; errorMessage = '';

  categories = ['general', 'kyc', 'products', 'support', 'orders', 'avatar'];

  constructor(private api: BackendApiService, private toastr: ToastrService) {
    const stored = localStorage.getItem('uploaded_files');
    if (stored) this.uploadedFiles = JSON.parse(stored);
  }

  onFile(event: any) {
    const f: File = event.target.files?.[0]; if (!f) return;
    this.uploading = true;
    this.errorMessage = '';
    this.api.uploadFile(f, this.category).subscribe(
      (res: any) => {
        this.uploading = false;
        const name = res?.data?.fileName || res?.data?.name || f.name;
        const url = res?.data?.fileUrl || res?.data?.url || this.api.fileUrl(this.category, name);
        const entry = { category: this.category, name, url };
        this.uploadedFiles.unshift(entry);
        localStorage.setItem('uploaded_files', JSON.stringify(this.uploadedFiles.slice(0, 50)));
        this.successMessage = `Uploaded: ${name}`;
        this.toastr.success(`Uploaded: ${name}`, 'Success');
      },
      err => {
        this.uploading = false;
        this.errorMessage = err?.error?.message || 'Upload failed.';
        this.toastr.error(this.errorMessage, 'Upload failed');
      }
    );
  }

  preview(file: any) { window.open(file.url, '_blank'); }

  delete(file: any, idx: number) {
    if (!confirm(`Delete ${file.name}?`)) return;
    this.api.deleteFile(file.category, file.name).subscribe(
      () => {
        this.uploadedFiles.splice(idx, 1);
        localStorage.setItem('uploaded_files', JSON.stringify(this.uploadedFiles));
        this.successMessage = `Deleted: ${file.name}`;
        this.toastr.success(`Deleted: ${file.name}`, 'Removed');
      },
      err => {
        this.errorMessage = err?.error?.message || 'Delete failed.';
        this.toastr.error(this.errorMessage, 'Delete failed');
      }
    );
  }
}
