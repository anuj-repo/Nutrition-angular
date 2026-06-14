import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-support-detail',
  templateUrl: './support-detail.component.html',
  styleUrls: ['./support-detail.component.css']
})
export class SupportDetailComponent implements OnInit {
  ticketId!: number;
  ticket: any = null;
  replies: any[] = [];
  replyForm: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private api: BackendApiService,
    private toastr: ToastrService
  ) {
    this.replyForm = this.fb.group({
      message: ['', Validators.required],
      attachmentUrl: ['']
    });
  }

  ngOnInit(): void {
    this.ticketId = Number(this.route.snapshot.paramMap.get('id'));
    this.load();
  }

  load() {
    forkJoin({
      t: this.api.getTicket(this.ticketId).pipe(catchError(() => of(null as any))),
      r: this.api.ticketReplies(this.ticketId).pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.ticket = res.t?.data;
      this.replies = res.r?.data || [];
    });
  }

  reply() {
    if (this.replyForm.invalid) { this.replyForm.markAllAsTouched(); return; }
    this.api.replyTicket(this.ticketId, this.replyForm.value).subscribe(
      () => {
        this.replyForm.reset();
        this.toastr.success('Reply posted.', 'Success');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Reply failed.';
        this.toastr.error(this.errorMessage, 'Reply failed');
      }
    );
  }

  closeTicket() {
    if (!confirm('Close this ticket?')) return;
    this.api.updateTicketStatus(this.ticketId, 'CLOSED').subscribe(
      () => {
        this.toastr.success('Ticket closed.', 'Success');
        this.load();
      },
      err => this.toastr.error(err?.error?.message || 'Could not close ticket.', 'Failed')
    );
  }

  back() { this.router.navigate(['/support']); }
}
