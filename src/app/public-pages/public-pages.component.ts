import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-public-pages',
  templateUrl: './public-pages.component.html',
  styleUrls: ['./public-pages.component.css']
})
export class PublicPagesComponent implements OnInit {
  page: 'landing' | 'about' | 'contact' | 'why-choose-us' | 'faq' = 'landing';
  content: any = {};
  faqs: any[] = [];
  contactForm: FormGroup;
  faqForm: FormGroup;
  newsletterForm: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.contactForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      mobile: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      subject: ['', Validators.required],
      message: ['', Validators.required]
    });
    this.faqForm = this.fb.group({
      question: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
    this.newsletterForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.route.data.subscribe(d => {
      this.page = d.page || 'landing';
      this.load();
    });
  }

  load() {
    const handler = this.page === 'about' ? this.api.publicAbout()
                  : this.page === 'contact' ? this.api.publicContact()
                  : this.page === 'why-choose-us' ? this.api.publicWhyUs()
                  : this.page === 'faq' ? this.api.publicFaq()
                  : this.api.publicLanding();
    handler.subscribe(
      (r: any) => {
        if (this.page === 'faq') this.faqs = r?.data || [];
        else this.content = r?.data || {};
      },
      () => {}
    );
  }

  submitContact() {
    if (this.contactForm.invalid) { this.contactForm.markAllAsTouched(); return; }
    this.api.contactInquiry(this.contactForm.value).subscribe(
      () => {
        this.successMessage = 'Thanks! We\'ll get back to you soon.';
        this.toastr.success(this.successMessage, 'Message sent');
        this.contactForm.reset();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Submission failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  submitFaq() {
    if (this.faqForm.invalid) { this.faqForm.markAllAsTouched(); return; }
    this.api.submitFaq(this.faqForm.value).subscribe(
      () => {
        this.successMessage = 'Question submitted.';
        this.toastr.success('Question submitted.', 'Success');
        this.faqForm.reset();
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Submission failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }

  subscribeNewsletter() {
    if (this.newsletterForm.invalid) return;
    this.api.newsletterSubscribe(this.newsletterForm.value).subscribe(
      () => {
        this.successMessage = 'Subscribed!';
        this.toastr.success('Subscribed!', 'Success');
        this.newsletterForm.reset();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Subscribe failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }
}
