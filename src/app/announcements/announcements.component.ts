import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit {
  list: any[] = [];

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    this.api.announcements().subscribe(
      (r: any) => this.list = r?.data || [],
      () => this.list = []
    );
  }
}
