import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderDetails } from '../_model/order-details.model';
import { MyOrderDetails } from '../_model/order.model';
import { Product } from '../_model/product.model';
import { environment } from './../../environments/environment';
import { API_PATH } from './../utils/constants/api.constants';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  PATH_OF_API = environment.BASE_URL;

  constructor(private http: HttpClient) { }

  // public getAllOrderDetailsForAdmin() : Observable<MyOrderDetails[]>{
  //   return this.httpClient.get<MyOrderDetails[]>(PATH_OF_API+/'getAllOrderDetails');
  //  }

   getAllOrderDetailsForAdmin() {
    return this.http.get<any>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.ALL_ORDER_DETAILS}`)
      .pipe(map(data => {
        return data;
      }));
  }

  public getMyOrders() : Observable<MyOrderDetails[]>{
    return this.http.get<MyOrderDetails[]>(`${environment.BASE_URL+ API_PATH.API_VERSION_V1 +API_PATH.GET_ORDER_DETAILS}`)
   }

  public deleteCartItem(cartId){
    return this.http.delete(`${environment.BASE_URL+ API_PATH.API_VERSION_V1 +API_PATH.DELETE_CART_ITEM+cartId}`)
   }

  public addProduct(product: FormData){
    return this.http.post<Product>(`${environment.BASE_URL + API_PATH.API_VERSION_V1 + API_PATH.ADD_PRODUCT}`, product)
  }

  public getAllProducts(pageNumber, searchKeyword: string= ""){
    return this.http.get<Product[]>(
      `${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.GET_ALL_PRODUCTS}?pageNumber=${pageNumber}&searchKey=${searchKeyword}`
    );
    
  }

  public getProductDetailsById(productId){
    return this.http.get<Product>(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.GET_PRODUCT_DETAILS_BY_ID+productId}`)
   }

  public deleteProduct(productId: number){
   return this.http.delete(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.DELETE_PRODUCT_DETAILS+productId}`)
  }

  public getProductDetails(isSingeProductCheckout,productId){
    return this.http.get<Product[]>(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.GET_PRODUCT_DETAILS}`+isSingeProductCheckout+"/"+productId);
   }


   public placeOrder(orderDetails: OrderDetails, isCartCheckout){
    return this.http.post(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.PLACE_ORDER}`+isCartCheckout, orderDetails);
   }

   public addToCart(productId){
    return this.http.get(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.ADD_TO_CART}`+productId);
   }

   public getCartDetails(){
    return this.http.get(`${environment.BASE_URL+API_PATH.API_VERSION_V1+API_PATH.GET_CART_DETAILS}`);
    
   }


}
