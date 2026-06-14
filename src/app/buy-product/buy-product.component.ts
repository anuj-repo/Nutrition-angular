import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { OrderDetails } from '../_model/order-details.model';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {

  isSingleProductCheckout : string = "";
  productDetails : Product[]=[];
  orderDetails: OrderDetails={
    fullName : '',
	  fullAddress: '',
	  contactNumber : '',
	  alternateContactNumber : '',
	  orderProductQuantityList : []
  }
  constructor( private activatedRoute: ActivatedRoute,
    private productService : ProductService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.productDetails= this.activatedRoute.snapshot.data['productDetails'];

    this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");
    this.productDetails.forEach(
      x => this.orderDetails.orderProductQuantityList.push(
        {productId: x.productId, quantity: 1
        }
      )
    );
    console.log(this.productDetails);
    console.log(this.orderDetails);
  }

  public placeOrder(orderForm : NgForm){
    this.productService.placeOrder(this.orderDetails, this.isSingleProductCheckout).subscribe(
      (resp) => {
        console.log(resp);
        this.toastr.success('Order placed successfully!', '🎉 Order placed');
        orderForm.reset();
        this.router.navigate(["/orderConfirm"])
      },
      (err) => {
        console.log(err);
        this.toastr.error(err?.error?.message || 'Could not place order. Please try again.', 'Order failed');
      }
    );

  }

  getQuantityForProduct(productId){
    const filterProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filterProduct[0].quantity;

  }

  getCalculatedTotal(productId, productDiscountedPrice){
    const filterProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filterProduct[0].quantity*productDiscountedPrice;

  }

  onQuantityChanged(q, productId){
    this.orderDetails.orderProductQuantityList.filter(
      (orderProduct) => orderProduct.productId === productId
    )[0].quantity=q;
  }

  getCalculatedGrandTotal(){
    let grandTotal = 0;
    this.orderDetails.orderProductQuantityList.forEach(
      (productQuantity) => {
        const price=this.productDetails.filter(product => product.productId === productQuantity.productId)[0].productDiscountedPrice
        grandTotal+=price*productQuantity.quantity;
      }
    );
    return grandTotal;
  }

}
