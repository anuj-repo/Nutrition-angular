import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

/**
 * Backend `NotificationPreference` defaults to:
 *   emailEnabled=true, smsEnabled=true, pushEnabled=true,
 *   whatsappEnabled=false, preferredLang="EN" (uppercase).
 *
 * The UI normalises preferredLang to uppercase so the dropdown actually
 * binds to the saved value when the page is reopened.
 */
@Component({
  selector: 'app-notification-preferences',
  templateUrl: './notification-preferences.component.html',
  styleUrls: ['./notification-preferences.component.css']
})
export class NotificationPreferencesComponent implements OnInit {
  form: FormGroup;
  loading = false;
  saving = false;
  successMessage = ''; errorMessage = '';

  constructor(private fb: FormBuilder, private api: BackendApiService, private toastr: ToastrService) {
    this.form = this.fb.group({
      emailEnabled: [true],
      smsEnabled: [true],
      pushEnabled: [true],
      whatsappEnabled: [false],
      preferredLang: ['EN']
    });
  }

  ngOnInit(): void {
    this.loading = true;
    this.api.getNotifPrefs().subscribe(
      (res: any) => {
        this.loading = false;
        if (res?.data) this.applyPrefs(res.data);
      },
      () => { this.loading = false; }
    );
  }

  private applyPrefs(data: any) {
    this.form.patchValue({
      emailEnabled: data.emailEnabled !== false,
      smsEnabled: data.smsEnabled !== false,
      pushEnabled: data.pushEnabled !== false,
      whatsappEnabled: data.whatsappEnabled === true,
      preferredLang: (data.preferredLang || 'EN').toString().toUpperCase()
    });
  }

  save() {
    this.successMessage = ''; this.errorMessage = '';
    this.saving = true;
    const payload = {
      ...this.form.value,
      preferredLang: (this.form.value.preferredLang || 'EN').toString().toUpperCase()
    };
    this.api.saveNotifPrefs(payload).subscribe(
      (res: any) => {
        this.saving = false;
        if (res?.data) this.applyPrefs(res.data);
        this.successMessage = 'Preferences saved.';
        this.toastr.success('Preferences saved.', 'Success');
      },
      err => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Save failed.';
        this.toastr.error(this.errorMessage, 'Save failed');
      }
    );
  }
}
