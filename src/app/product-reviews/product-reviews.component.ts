import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-product-reviews',
  templateUrl: './product-reviews.component.html',
  styleUrls: ['./product-reviews.component.css']
})
export class ProductReviewsComponent implements OnInit {
  productId!: number;
  reviews: any[] = [];
  benefits: any[] = [];
  ingredients: any[] = [];
  reviewForm: FormGroup;
  successMessage = ''; errorMessage = '';

  // Static product info shown when no productId or backend returns empty
  readonly defaultBenefits = [
    { name: 'Boosts Stamina & Energy', description: 'Clinically tested formula improves endurance and reduces fatigue naturally.' },
    { name: 'Enhances Immunity', description: 'Rich in antioxidants and Vitamin C to strengthen your immune system.' },
    { name: 'Supports Muscle Growth', description: 'Protein-rich blend helps build lean muscle mass effectively.' },
    { name: 'Improves Digestion', description: 'Natural enzymes and probiotics aid better nutrient absorption.' },
    { name: 'Stress & Anxiety Relief', description: 'Ashwagandha and Brahmi reduce cortisol and promote mental clarity.' },
    { name: 'Better Sleep Quality', description: 'Natural extracts help regulate sleep cycles for deeper rest.' },
    { name: 'Joint & Bone Support', description: 'Calcium, Vitamin D3 and Glucosamine for strong bones and flexible joints.' },
    { name: 'Heart Health', description: 'Omega-3 and CoQ10 support cardiovascular wellness.' }
  ];

  readonly defaultIngredients = [
    { name: 'Ashwagandha Extract', quantity: '500', unit: 'mg', benefit: 'Reduces stress, improves stamina and vitality' },
    { name: 'Shilajit (Purified)', quantity: '250', unit: 'mg', benefit: 'Boosts energy, enhances testosterone naturally' },
    { name: 'Safed Musli', quantity: '300', unit: 'mg', benefit: 'Improves endurance and physical performance' },
    { name: 'Gokshura (Tribulus)', quantity: '200', unit: 'mg', benefit: 'Supports muscle growth and vitality' },
    { name: 'Vitamin D3', quantity: '1000', unit: 'IU', benefit: 'Strengthens bones, supports immunity' },
    { name: 'Zinc', quantity: '15', unit: 'mg', benefit: 'Boosts immunity, supports hormonal balance' },
    { name: 'Vitamin B12', quantity: '5', unit: 'mcg', benefit: 'Reduces fatigue, supports nervous system' },
    { name: 'Black Pepper Extract (Piperine)', quantity: '10', unit: 'mg', benefit: 'Enhances absorption of all nutrients by 30%' }
  ];

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.reviewForm = this.fb.group({
      rating: [5, [Validators.required, Validators.min(1), Validators.max(5)]],
      title: ['', Validators.required],
      review: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId') || this.route.snapshot.queryParamMap.get('productId'));
    if (this.productId && !isNaN(this.productId)) {
      this.load();
    } else {
      // No productId - show default Stamina Core Plus info
      this.benefits = this.defaultBenefits;
      this.ingredients = this.defaultIngredients;
    }
  }

  load() {
    this.api.productReviews(this.productId).subscribe(
      (r: any) => this.reviews = r?.data || [],
      () => this.reviews = []
    );
    this.api.productBenefits(this.productId).subscribe(
      (r: any) => {
        const data = r?.data || [];
        this.benefits = data.length ? data : this.defaultBenefits;
      },
      () => this.benefits = this.defaultBenefits
    );
    this.api.productIngredients(this.productId).subscribe(
      (r: any) => {
        const data = r?.data || [];
        this.ingredients = data.length ? data : this.defaultIngredients;
      },
      () => this.ingredients = this.defaultIngredients
    );
  }

  setRating(n: number) { this.reviewForm.patchValue({ rating: n }); }

  submit() {
    if (this.reviewForm.invalid) { this.reviewForm.markAllAsTouched(); return; }
    if (!this.productId || isNaN(this.productId)) {
      this.toastr.info('Product reviews will be available soon.', 'Coming soon');
      return;
    }
    this.api.postReview(this.productId, this.reviewForm.value).subscribe(
      () => {
        this.successMessage = 'Review submitted, awaiting approval.';
        this.toastr.success('Thanks! Your review is awaiting approval.', 'Review submitted');
        this.reviewForm.reset({ rating: 5 });
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Submission failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }
}
