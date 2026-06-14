import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-cart-v2',
  templateUrl: './cart-v2.component.html',
  styleUrls: ['./cart-v2.component.css']
})
export class CartV2Component implements OnInit {
  items: any[] = [];
  successMessage = ''; errorMessage = '';
  loading = true;

  constructor(private http: HttpClient, private api: BackendApiService, private router: Router, private toastr: ToastrService) {}

  ngOnInit(): void { this.load(); }

  load() {
    this.loading = true;
    this.http.get<any>(`${environment.BASE_URL}/api/getCartDetails`).subscribe(
      res => { this.items = res?.data || res || []; this.loading = false; },
      () => { this.items = []; this.loading = false; }
    );
  }

  changeQty(item: any, delta: number) {
    const newQty = (item.quantity || 1) + delta;
    if (newQty < 1) return;
    this.api.updateCartItem(item.cartId || item.id, { quantity: newQty }).subscribe(
      () => {
        item.quantity = newQty;
        this.successMessage = 'Quantity updated.';
        this.toastr.success('Quantity updated.', 'Success');
      },
      err => {
        this.errorMessage = err?.error?.message || 'Update failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  remove(item: any) {
    if (!confirm('Remove this item?')) return;
    this.http.delete<any>(`${environment.BASE_URL}/api/deleteCartItem/${item.cartId || item.id}`).subscribe(
      () => {
        this.successMessage = 'Item removed.';
        this.toastr.success('Item removed from cart.', 'Removed');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Remove failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  clearCart() {
    if (!confirm('Clear all items from cart?')) return;
    this.api.clearCart().subscribe(
      () => {
        this.successMessage = 'Cart cleared.';
        this.toastr.success('Cart cleared.', 'Success');
        this.items = [];
      },
      err => {
        this.errorMessage = err?.error?.message || 'Clear failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  total(): number {
    return this.items.reduce((s, i) => s + (Number(i.price || i.product?.productDiscountedPrice || 0) * Number(i.quantity || 1)), 0);
  }

  checkout() {
    this.router.navigate(['/orderConfirm']);
  }
}
