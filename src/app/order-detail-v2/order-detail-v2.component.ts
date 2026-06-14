import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';
import { UtililtyFunctions } from '../utils/utils';

@Component({
  selector: 'app-order-detail-v2',
  templateUrl: './order-detail-v2.component.html',
  styleUrls: ['./order-detail-v2.component.css']
})
export class OrderDetailV2Component implements OnInit {
  orderId!: number;
  order: any = null;
  tracking: any = null;
  successMessage = ''; errorMessage = '';
  downloading = false;

  loading = true;

  // Cooling-off
  coolingOff: any = null;
  // Admin
  isAdmin = false;
  refundAmount = 0;
  refundReason = '';

  constructor(private route: ActivatedRoute, private router: Router, private api: BackendApiService, private utils: UtililtyFunctions, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.orderId = Number(this.route.snapshot.paramMap.get('id'));
    const u = this.utils.getUserMeData();
    this.isAdmin = u?.role === 'Admin';
    this.load();
  }

  load() {
    forkJoin({
      o: this.api.getOrder(this.orderId).pipe(catchError(() => of(null as any))),
      t: this.api.trackOrder(this.orderId).pipe(catchError(() => of(null as any))),
      cool: this.api.coolingOffStatusByOrder(this.orderId).pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.order = res.o?.data;
      this.tracking = res.t?.data;
      this.coolingOff = res.cool?.data;
      this.loading = false;
    });
  }

  exerciseCoolingOff() {
    const reason = prompt('Reason for cooling-off cancellation:');
    if (!reason) return;
    this.api.exerciseCoolingOffByOrder(this.orderId, { reason }).subscribe(
      () => {
        this.successMessage = 'Cooling-off exercised. Order will be cancelled and refunded.';
        this.toastr.success(this.successMessage, 'Cooling-off');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Cooling-off failed.';
        this.toastr.error(this.errorMessage, 'Cooling-off failed');
      }
    );
  }

  doRefund() {
    if (!this.refundAmount || this.refundAmount <= 0) {
      this.errorMessage = 'Enter a valid refund amount.';
      this.toastr.warning(this.errorMessage, 'Invalid amount');
      return;
    }
    if (!confirm(`Refund ₹${this.refundAmount} for this order?`)) return;
    this.api.refund(this.orderId, { amount: this.refundAmount, reason: this.refundReason }).subscribe(
      () => {
        this.successMessage = 'Refund initiated.';
        this.toastr.success('Refund initiated.', 'Success');
        this.refundAmount = 0;
        this.refundReason = '';
      },
      err => {
        this.errorMessage = err?.error?.message || 'Refund failed.';
        this.toastr.error(this.errorMessage, 'Refund failed');
      }
    );
  }

  cancel() {
    const reason = prompt('Reason for cancellation:');
    if (!reason) return;
    this.api.cancelOrder(this.orderId, reason).subscribe(
      () => {
        this.successMessage = 'Order cancelled.';
        this.toastr.success('Order cancelled.', 'Success');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Cancellation failed.';
        this.toastr.error(this.errorMessage, 'Cancellation failed');
      }
    );
  }

  return_() {
    const reason = prompt('Reason for return:');
    if (!reason) return;
    this.api.returnOrder(this.orderId, { reason }).subscribe(
      () => {
        this.successMessage = 'Return initiated.';
        this.toastr.success('Return initiated.', 'Success');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Return failed.';
        this.toastr.error(this.errorMessage, 'Return failed');
      }
    );
  }

  downloadInvoice() {
    this.downloading = true;
    this.api.invoice(this.orderId).subscribe(
      (blob: Blob) => {
        this.downloading = false;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url; a.download = `invoice-${this.orderId}.pdf`; a.click();
        window.URL.revokeObjectURL(url);
        this.toastr.success('Invoice downloaded.', 'Success');
      },
      () => {
        this.downloading = false;
        this.errorMessage = 'Download failed.';
        this.toastr.error(this.errorMessage, 'Download failed');
      }
    );
  }

  back() { this.router.navigate(['/orders-v2']); }
}
