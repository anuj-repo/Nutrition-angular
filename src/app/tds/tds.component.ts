import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-tds',
  templateUrl: './tds.component.html',
  styleUrls: ['./tds.component.css']
})
export class TdsComponent implements OnInit {
  summary: any = {};
  selectedYear = new Date().getFullYear();
  years: number[] = [];
  downloading = false;
  errorMessage = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {
    const cur = new Date().getFullYear();
    for (let y = cur; y >= cur - 5; y--) this.years.push(y);
  }

  ngOnInit(): void {
    this.api.tdsSummary().subscribe(
      (r: any) => this.summary = r?.data || {},
      () => this.summary = {}
    );
  }

  downloadForm16a() {
    this.downloading = true;
    this.errorMessage = '';
    this.api.form16a(this.selectedYear).subscribe(
      (blob: Blob) => {
        this.downloading = false;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url; a.download = `Form16A-${this.selectedYear}.pdf`; a.click();
        window.URL.revokeObjectURL(url);
        this.toastr.success(`Form16A for ${this.selectedYear} downloaded.`, 'Download ready');
      },
      err => {
        this.downloading = false;
        this.errorMessage = 'Download failed: ' + (err?.error?.message || '');
        this.toastr.error(this.errorMessage, 'Download failed');
      }
    );
  }
}
