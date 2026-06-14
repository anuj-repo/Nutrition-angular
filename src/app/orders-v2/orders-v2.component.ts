import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-orders-v2',
  templateUrl: './orders-v2.component.html',
  styleUrls: ['./orders-v2.component.css']
})
export class OrdersV2Component implements OnInit {
  orders: any[] = [];
  repurchaseDue: any[] = [];

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    this.api.myOrders().subscribe(
      (r: any) => this.orders = r?.data || [],
      () => this.orders = []
    );
    this.api.repurchaseDue().subscribe(
      (r: any) => this.repurchaseDue = r?.data || [],
      () => this.repurchaseDue = []
    );
  }
}
