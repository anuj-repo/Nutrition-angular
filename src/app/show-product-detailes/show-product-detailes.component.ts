import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { ImageProcessingService } from '../image-processing.service';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-show-product-detailes',
  templateUrl: './show-product-detailes.component.html',
  styleUrls: ['./show-product-detailes.component.css']
})
export class ShowProductDetailesComponent implements OnInit {

  showLoadMoreProductButton = false;
  showTable = false;
  pageNumber: number = 0;
  productDetails : Product[] =[];
  displayedColumns: string[] = ['Id', 'Product Name', 'Product Description', 'Product Discounted Price', 'Product Actual Price' ,'Actions'];
  constructor(private productService: ProductService ,
    public imagesDialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllProducts();
  }

  searchByKeyword(searchkeyword){

    this.pageNumber= 0;
    this.productDetails= [];
    this.getAllProducts(searchkeyword);

  }

  public getAllProducts(searchKey: string =""){
    this.showTable = false;
    this.productService.getAllProducts(this.pageNumber, searchKey)
    .pipe(
      map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe(
      (resp: Product[]) =>{
        console.log(resp);
        resp.forEach(product => this.productDetails.push(product));
        this.showTable=true;
        if(resp.length==2){
          this.showLoadMoreProductButton=true;
        }else{
          this.showLoadMoreProductButton=false;
        }
        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error(error?.error?.message || 'Could not load products.', 'Failed');
      }

    );
  }

  loadMoreProduct(){
    this.pageNumber= this.pageNumber+1;
    this.getAllProducts();
  }

  deleteProduct(productId){
    this.productService.deleteProduct(productId).subscribe(
      (resp)=> {
        this.toastr.success('Product deleted.', 'Removed');
        this.getAllProducts();
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error(error?.error?.message || 'Could not delete product.', 'Failed');
      }
    );    
  }

  showImages(product: Product){
    console.log(product);
    this.imagesDialog.open(ShowProductImagesDialogComponent, {
      data: {
        images: product.productImages
      },
      height: '500px',
      width: '800px'
    });

  }

  editProductDetails(productId){
    this.router.navigate(['/addNewProduct', {productId: productId}])
  }

}
