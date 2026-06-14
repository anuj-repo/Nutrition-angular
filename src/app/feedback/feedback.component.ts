import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {
  form: FormGroup;
  myFeedback: any[] = [];
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      rating: [5, [Validators.required, Validators.min(1), Validators.max(5)]],
      category: ['GENERAL', Validators.required],
      message: ['', Validators.required]
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    this.api.myFeedback().subscribe(
      (r: any) => this.myFeedback = r?.data || [],
      () => this.myFeedback = []
    );
  }

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    this.api.submitFeedback(this.form.value).subscribe(
      () => {
        this.successMessage = 'Thanks for your feedback!';
        this.toastr.success('Thanks for your feedback!', 'Submitted');
        this.form.reset({ rating: 5, category: 'GENERAL' });
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Submission failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  setRating(r: number) { this.form.patchValue({ rating: r }); }
}
