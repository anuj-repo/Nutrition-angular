import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';
import { UserService } from '../_services/user.service';
import { UtililtyFunctions } from '../utils/utils';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  user: any = {};
  addresses: any[] = [];
  prefs: any = {
    emailEnabled: true, smsEnabled: true, pushEnabled: true,
    whatsappEnabled: false, preferredLang: 'EN'
  };
  successMessage = ''; errorMessage = '';
  reEntryLoading = false;
  loadingProfile = false;
  loadingPrefs = false;
  savingPrefs = false;

  constructor(
    private api: BackendApiService,
    private userService: UserService,
    private utils: UtililtyFunctions,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    // Show whatever we have cached immediately so the page isn't blank.
    this.user = this.utils.getUserMeData() || {};
    // Then refresh from /api/user/me so we get every field saved at registration.
    this.refreshProfile();
    this.loadPrefs();
  }

  refreshProfile() {
    this.loadingProfile = true;
    this.userService.getCurrentUserData().subscribe(
      (res: any) => {
        this.loadingProfile = false;
        const data = res?.data || {};
        this.user = { ...this.user, ...data };
        this.addresses = Array.isArray(data.userAddressDTO) ? data.userAddressDTO : [];
      },
      err => {
        this.loadingProfile = false;
        // Don't error-toast here — fall back to cached data quietly.
        console.warn('Could not refresh profile from server', err);
      }
    );
  }

  loadPrefs() {
    this.loadingPrefs = true;
    this.api.getNotifPrefs().subscribe(
      (res: any) => {
        this.loadingPrefs = false;
        if (res?.data) this.prefs = { ...this.prefs, ...res.data };
      },
      () => { this.loadingPrefs = false; }
    );
  }

  savePrefs() {
    this.savingPrefs = true;
    this.api.saveNotifPrefs(this.prefs).subscribe(
      (res: any) => {
        this.savingPrefs = false;
        if (res?.data) this.prefs = { ...this.prefs, ...res.data };
        this.toastr.success('Preferences saved.', 'Success');
      },
      err => {
        this.savingPrefs = false;
        this.toastr.error(err?.error?.message || 'Could not save preferences.', 'Save failed');
      }
    );
  }

  onAvatarUpdated(url: string) {
    this.user.userImage = url;
    const stored = this.utils.getUserMeData();
    if (stored) {
      stored.userImage = url;
      sessionStorage.setItem('loginedUserData', JSON.stringify(stored));
    }
    this.toastr.success('Profile photo updated.', 'Success');
  }

  triggerReEntry() {
    if (!confirm('Re-enter into the network at the next available level? This action is final.')) return;
    this.reEntryLoading = true;
    this.errorMessage = '';
    this.api.reEntry().subscribe(
      (r: any) => {
        this.reEntryLoading = false;
        this.successMessage = r?.data || r?.message || 'Re-entry processed successfully.';
        this.toastr.success(this.successMessage, 'Re-entry');
      },
      err => {
        this.reEntryLoading = false;
        this.errorMessage = err?.error?.message || 'Re-entry failed.';
        this.toastr.error(this.errorMessage, 'Re-entry failed');
      }
    );
  }

  // Helpers used in template
  fullName(): string {
    return [this.user?.fname, this.user?.lname].filter(Boolean).join(' ').trim() || '—';
  }

  defaultAddress(): any {
    if (!this.addresses?.length) return null;
    return this.addresses.find(a => a.isDefault === '1' || a.isDefault === 1 || a.isDefault === true)
      || this.addresses[0];
  }

  formatDob(): string {
    const d = this.user?.dob;
    if (!d) return '—';
    try {
      const dt = new Date(d);
      if (isNaN(dt.getTime())) return String(d);
      return dt.toLocaleDateString();
    } catch { return String(d); }
  }

  packageLabel(): string {
    const pkg = this.user?.packageTaken;
    if (!pkg) return '—';
    // backend returns numeric string from @JsonValue, e.g. "2280"
    return `₹${pkg}`;
  }
}
