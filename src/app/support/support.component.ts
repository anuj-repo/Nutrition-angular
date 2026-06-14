import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {
  tickets: any[] = [];
  showForm = false;
  form: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      subject: ['', Validators.required],
      description: ['', Validators.required],
      category: ['GENERAL', Validators.required],
      priority: ['NORMAL', Validators.required]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    this.api.myTickets().subscribe(
      (r: any) => this.tickets = r?.data || [],
      () => this.tickets = []
    );
  }

  toggleForm() { this.showForm = !this.showForm; this.form.reset({ category: 'GENERAL', priority: 'NORMAL' }); }

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    this.api.createTicket(this.form.value).subscribe(
      () => {
        this.successMessage = 'Ticket created.';
        this.toastr.success('Ticket created.', 'Success');
        this.showForm = false;
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Failed to create ticket.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }
}
