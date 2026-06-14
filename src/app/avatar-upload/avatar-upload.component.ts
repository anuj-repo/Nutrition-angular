import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-avatar-upload',
  templateUrl: './avatar-upload.component.html',
  styleUrls: ['./avatar-upload.component.css']
})
export class AvatarUploadComponent {
  @Input() avatarUrl = '';
  @Output() updated = new EventEmitter<string>();
  uploading = false;
  errorMessage = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  onFile(event: any) {
    const f: File = event.target.files?.[0]; if (!f) return;
    if (f.size > 5 * 1024 * 1024) {
      this.errorMessage = 'File too large (max 5 MB).';
      this.toastr.warning(this.errorMessage, 'File too large');
      return;
    }
    this.uploading = true;
    this.errorMessage = '';
    this.api.uploadAvatar(f).subscribe(
      (res: any) => {
        this.uploading = false;
        const url = res?.data?.url || res?.data?.fileUrl || res?.data;
        this.avatarUrl = url;
        this.updated.emit(url);
        this.toastr.success('Profile photo uploaded.', 'Success');
      },
      err => {
        this.uploading = false;
        this.errorMessage = err?.error?.message || 'Upload failed.';
        this.toastr.error(this.errorMessage, 'Upload failed');
      }
    );
  }
}
