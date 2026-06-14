import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-admin-product-edit',
  templateUrl: './admin-product-edit.component.html',
  styleUrls: ['./admin-product-edit.component.css']
})
export class AdminProductEditComponent implements OnInit {
  productId!: number;
  form: FormGroup;
  loaded = false;
  successMessage = ''; errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private http: HttpClient,
    private api: BackendApiService,
    private toastr: ToastrService
  ) {
    this.form = this.fb.group({
      productName: ['', Validators.required],
      productDescription: ['', Validators.required],
      productActualPrice: ['', Validators.required],
      productDiscountedPrice: ['', Validators.required],
      packageType: [''],
      isActive: [true]
    });
  }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));
    this.http.get<any>(`${environment.BASE_URL}/api/getProductDetailsById/${this.productId}`).subscribe(
      res => { this.form.patchValue(res?.data || res); this.loaded = true; },
      () => {
        this.loaded = true;
        this.errorMessage = 'Could not load product.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  save() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    this.api.updateProduct(this.productId, this.form.value).subscribe(
      () => {
        this.successMessage = 'Product updated successfully.';
        this.toastr.success('Product updated successfully.', 'Saved');
      },
      err => {
        this.errorMessage = err?.error?.message || 'Update failed.';
        this.toastr.error(this.errorMessage, 'Update failed');
      }
    );
  }

  back() { this.router.navigate(['/showProductDetailes']); }
}
