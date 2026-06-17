import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ImageProcessingService } from '../image-processing.service';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  pageNumber: number = 0;
  productDetails=[];
  showLoadButton = false;

  // Banner slider
  bannerSlides = [
    { bg: "url('assets/images/banner/bnr1.jpg') center/cover no-repeat" },
    { bg: "url('assets/images/banner/bnr2.jpg') center/cover no-repeat" },
    { bg: "url('assets/images/banner/bnr3.jpg') center/cover no-repeat" },
    { bg: "url('assets/images/banner/bnr4.jpg') center/cover no-repeat" },
    { bg: "url('assets/images/banner/bnr5.jpg') center/cover no-repeat" }
  ];
  currentSlide = 0;
  private sliderInterval: any;

  constructor(private productService: ProductService,
    private imageProcessingService: ImageProcessingService,
    private router : Router) { }

  ngOnInit(): void {
    this.getAllProducts();
    this.sliderInterval = setInterval(() => {
      this.currentSlide = (this.currentSlide + 1) % this.bannerSlides.length;
    }, 4000);
  }

  ngOnDestroy(): void {
    if (this.sliderInterval) {
      clearInterval(this.sliderInterval);
    }
  }

  searchByKeyword(searchkeyword){

    this.pageNumber= 0;
    this.productDetails= [];
    this.getAllProducts(searchkeyword);

  }

  public getAllProducts(searchKey: string =""){
    this.productService.getAllProducts(this.pageNumber, searchKey)
    .pipe(
      map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe(
      (resp: Product[]) =>{
        console.log(resp);
        if(resp.length == 8){
          this.showLoadButton = true;
        }else{this.showLoadButton = false}
        resp.forEach(p => this.productDetails.push(p));
        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }

    );
  }

  public loadMoreProduct(){

    this.pageNumber = this.pageNumber+1;
    this.getAllProducts();
  }

  showProductDetails(productId){
    this.router.navigate(['/productViewDetails' , {productId: productId}]);

  }

  

}
