import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-payment-history',
  templateUrl: './payment-history.component.html',
  styleUrls: ['./payment-history.component.css']
})
export class PaymentHistoryComponent implements OnInit {
  payments: any[] = [];
  loading = true;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    this.api.paymentHistory().subscribe(
      (r: any) => { this.payments = r?.data || []; this.loading = false; },
      () => { this.loading = false; this.payments = []; }
    );
  }

  total(): number { return this.payments.reduce((s, p) => s + Number(p.amount || 0), 0); }
}
