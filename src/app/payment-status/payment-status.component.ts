import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-payment-status',
  templateUrl: './payment-status.component.html',
  styleUrls: ['./payment-status.component.css']
})
export class PaymentStatusComponent implements OnInit, OnDestroy {
  orderId!: number;
  status: any = null;
  polling = false;
  pollHandle: any = null;
  errorMessage = '';
  attempts = 0;
  maxAttempts = 30; // 30 polls × 5s = 2.5 minutes

  constructor(private route: ActivatedRoute, private router: Router, private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.orderId = Number(this.route.snapshot.paramMap.get('orderId') || this.route.snapshot.queryParamMap.get('orderId'));
    this.checkOnce();
    this.startPolling();
  }

  ngOnDestroy(): void { this.stopPolling(); }

  checkOnce() {
    this.api.paymentStatus(this.orderId).subscribe(
      (r: any) => {
        const prevStatus = (this.status?.status || this.status?.paymentStatus || '').toString().toUpperCase();
        this.status = r?.data || r;
        if (this.isFinal(this.status)) {
          this.stopPolling();
          const newStatus = (this.status?.status || this.status?.paymentStatus || '').toString().toUpperCase();
          if (newStatus !== prevStatus) {
            if (this.isSuccess(this.status)) {
              this.toastr.success('Payment successful!', '✅ Payment confirmed');
            } else {
              this.toastr.error(`Payment ${newStatus.toLowerCase()}.`, 'Payment failed');
            }
          }
        }
      },
      err => {
        this.errorMessage = err?.error?.message || 'Status check failed.';
        this.toastr.error(this.errorMessage, 'Status check failed');
        this.stopPolling();
      }
    );
  }

  isFinal(s: any): boolean {
    if (!s) return false;
    const st = (s.status || s.paymentStatus || '').toString().toUpperCase();
    return ['SUCCESS', 'COMPLETED', 'PAID', 'FAILED', 'CANCELLED'].includes(st);
  }

  isSuccess(s: any): boolean {
    if (!s) return false;
    const st = (s.status || s.paymentStatus || '').toString().toUpperCase();
    return ['SUCCESS', 'COMPLETED', 'PAID'].includes(st);
  }

  startPolling() {
    if (this.polling) return;
    this.polling = true;
    this.pollHandle = setInterval(() => {
      this.attempts++;
      if (this.attempts >= this.maxAttempts) { this.stopPolling(); return; }
      this.checkOnce();
    }, 5000);
  }

  stopPolling() {
    if (this.pollHandle) { clearInterval(this.pollHandle); this.pollHandle = null; }
    this.polling = false;
  }

  goToOrder() { this.router.navigate(['/orders-v2', this.orderId]); }
  retry() { this.attempts = 0; this.startPolling(); this.checkOnce(); }
}
