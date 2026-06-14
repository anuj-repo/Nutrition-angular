import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrls: ['./trips.component.css']
})
export class TripsComponent implements OnInit {
  trips: any[] = [];
  trip_eligible_tiers: any[] = [];
  form: FormGroup;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      rewardTierId: ['', Validators.required],
      destination: ['', Validators.required],
      preferredDate: ['', Validators.required],
      travellers: [1, [Validators.required, Validators.min(1)]],
      remarks: ['']
    });
  }

  ngOnInit(): void { this.load(); }

  load() {
    forkJoin({
      trips: this.api.myTrips().pipe(catchError(() => of(null as any))),
      tiers: this.api.rewardClaims().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.trips = res.trips?.data || [];
      // Trip-eligible tiers are typically those of category TRIP / TRAVEL
      const claims = res.tiers?.data || [];
      this.trip_eligible_tiers = claims
        .map((c: any) => c.rewardTier)
        .filter((t: any) => t && (t.rewardCategory || '').toString().toUpperCase().includes('TRIP'));
    });
  }

  bookTrip() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.successMessage = ''; this.errorMessage = '';
    this.api.bookTrip(this.form.value).subscribe(
      () => {
        this.successMessage = 'Trip booked!';
        this.toastr.success('Trip booked!', 'Success');
        this.form.reset({ travellers: 1 });
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Booking failed.';
        this.toastr.error(this.errorMessage, 'Booking failed');
      }
    );
  }
}
