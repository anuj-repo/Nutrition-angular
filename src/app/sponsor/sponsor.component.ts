import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-sponsor',
  templateUrl: './sponsor.component.html',
  styleUrls: ['./sponsor.component.css']
})
export class SponsorComponent implements OnInit {
  sponsor: any = null;
  directs: any[] = [];
  loading = true;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    forkJoin({
      sp: this.api.networkSponsor().pipe(catchError(() => of(null as any))),
      dir: this.api.networkDirect().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.sponsor = res.sp?.data;
      this.directs = res.dir?.data || [];
      this.loading = false;
    });
  }
}
