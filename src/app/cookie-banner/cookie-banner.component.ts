import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-cookie-banner',
  templateUrl: './cookie-banner.component.html',
  styleUrls: ['./cookie-banner.component.css']
})
export class CookieBannerComponent implements OnInit {
  visible = false;
  showCustom = false;
  prefs = { essential: true, analytics: false, marketing: false };

  constructor(private api: BackendApiService) {}

  ngOnInit(): void {
    if (!localStorage.getItem('cookie_consent')) this.visible = true;
  }

  acceptAll() {
    this.prefs = { essential: true, analytics: true, marketing: true };
    this.save();
  }

  rejectAll() {
    this.prefs = { essential: true, analytics: false, marketing: false };
    this.save();
  }

  save() {
    this.api.cookieConsent(this.prefs).subscribe(
      () => { localStorage.setItem('cookie_consent', JSON.stringify(this.prefs)); this.visible = false; },
      () => { localStorage.setItem('cookie_consent', JSON.stringify(this.prefs)); this.visible = false; }
    );
  }
}
