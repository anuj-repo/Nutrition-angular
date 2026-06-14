import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-compliance',
  templateUrl: './compliance.component.html',
  styleUrls: ['./compliance.component.css']
})
export class ComplianceComponent implements OnInit {
  selectedTab = 'dsa';
  dsaInfo: any = {};
  myGrievances: any[] = [];
  grievanceForm: FormGroup;
  successMessage = ''; errorMessage = '';
  downloading = false;

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.grievanceForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      mobile: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    forkJoin({
      dsa: this.api.dsa().pipe(catchError(() => of(null as any))),
      gri: this.api.myGrievances().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.dsaInfo = res.dsa?.data || {};
      this.myGrievances = res.gri?.data || [];
    });
  }

  switchTab(t: string) { this.selectedTab = t; this.successMessage = ''; this.errorMessage = ''; }

  acceptDsa() {
    this.api.acceptDsa().subscribe(
      () => {
        this.successMessage = 'DSA accepted.';
        this.toastr.success('DSA accepted.', 'Success');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Action failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  downloadIncomeCertificate() {
    this.downloading = true;
    this.api.incomeCertificate().subscribe(
      (blob: Blob) => {
        this.downloading = false;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url; a.download = 'income-certificate.pdf'; a.click();
        window.URL.revokeObjectURL(url);
        this.toastr.success('Income certificate downloaded.', 'Success');
      },
      err => {
        this.downloading = false;
        this.errorMessage = 'Download failed.';
        this.toastr.error(this.errorMessage, 'Download failed');
      }
    );
  }

  fileGrievance() {
    if (this.grievanceForm.invalid) { this.grievanceForm.markAllAsTouched(); return; }
    this.api.fileGrievance(this.grievanceForm.value).subscribe(
      () => {
        this.successMessage = 'Grievance filed successfully.';
        this.toastr.success('Grievance filed successfully.', 'Success');
        this.grievanceForm.reset();
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Filing failed.';
        this.toastr.error(this.errorMessage, 'Filing failed');
      }
    );
  }
}
