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
  editMode = false;
  editData: any = {};
  kycFiles: { [key: string]: { name: string; type: string; data: string } | null } = {
    PAN: null, AADHAAR: null, BANK_PROOF: null
  };
  kycFileError = '';
  private readonly MAX_KYC_FILE_SIZE = 1 * 1024 * 1024; // 1 MB
  successMessage = ''; errorMessage = '';
  reEntryLoading = false;
  loadingProfile = false;
  saving = false;

  constructor(
    private api: BackendApiService,
    private userService: UserService,
    private utils: UtililtyFunctions,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.user = this.utils.getUserMeData() || {};
    this.refreshProfile();
  }

  refreshProfile() {
    this.loadingProfile = true;
    this.userService.getCurrentUserData().subscribe(
      (res: any) => {
        this.loadingProfile = false;
        const data = res?.data || {};
        this.user = { ...this.user, ...data };
        this.addresses = Array.isArray(data.userAddressDTO) ? data.userAddressDTO : [];
        this.initEditData();
      },
      err => {
        this.loadingProfile = false;
        this.initEditData();
        console.warn('Could not refresh profile from server', err);
      }
    );
  }

  startEdit() {
    this.initEditData();
    this.editMode = true;
  }

  initEditData() {
    this.editData = {
      fname: this.user?.fname || '',
      lname: this.user?.lname || '',
      email: this.user?.email || '',
      mobileNumber: this.user?.mobileNumber || this.user?.contact || '',
      dob: this.user?.dob ? String(this.user.dob).substring(0, 10) : '',
      gender: this.user?.gender || 'Male',
      address: this.user?.address || '',
      city: this.user?.city || '',
      state: this.user?.state || '',
      country: this.user?.country || 'India',
      pincode: this.user?.pincode || '',
      panNumber: this.user?.panNumber || '',
      aadhaarNumber: this.user?.aadhaarNumber || '',
      accountHolderName: this.user?.accountHolderName || '',
      bankName: this.user?.bankName || '',
      accountNumber: this.user?.accountNumber || '',
      ifscCode: this.user?.ifscCode || ''
    };
  }

  saveProfile() {
    this.saving = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload: any = {
      fname: this.editData.fname?.trim(),
      lname: this.editData.lname?.trim(),
      email: this.editData.email?.trim(),
      mobileNumber: this.editData.mobileNumber?.trim(),
      dob: this.editData.dob,
      gender: this.editData.gender,
      address: this.editData.address?.trim(),
      city: this.editData.city,
      state: this.editData.state,
      country: this.editData.country,
      pincode: this.editData.pincode?.trim()
    };

    // Include KYC fields only if not yet approved
    if (this.user?.status !== 'ACTIVE') {
      payload.panNumber = (this.editData.panNumber || '').trim().toUpperCase();
      payload.aadhaarNumber = (this.editData.aadhaarNumber || '').trim();
      payload.accountHolderName = (this.editData.accountHolderName || '').trim();
      payload.bankName = (this.editData.bankName || '').trim();
      payload.accountNumber = (this.editData.accountNumber || '').trim();
      payload.ifscCode = (this.editData.ifscCode || '').trim().toUpperCase();

      // KYC document files
      if (this.kycFiles.PAN) {
        payload.panImage = this.kycFiles.PAN.data;
        payload.panImageName = this.kycFiles.PAN.name;
        payload.panImageType = this.kycFiles.PAN.type;
      }
      if (this.kycFiles.AADHAAR) {
        payload.aadhaarImage = this.kycFiles.AADHAAR.data;
        payload.aadhaarImageName = this.kycFiles.AADHAAR.name;
        payload.aadhaarImageType = this.kycFiles.AADHAAR.type;
      }
      if (this.kycFiles.BANK_PROOF) {
        payload.bankProofImage = this.kycFiles.BANK_PROOF.data;
        payload.bankProofImageName = this.kycFiles.BANK_PROOF.name;
        payload.bankProofImageType = this.kycFiles.BANK_PROOF.type;
      }
    }

    this.api.updateProfile(payload).subscribe(
      (res: any) => {
        this.saving = false;
        this.editMode = false;
        this.successMessage = 'Profile updated successfully!';
        this.toastr.success('Profile updated!', 'Success');
        this.refreshProfile();
      },
      err => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Failed to update profile.';
        this.toastr.error(this.errorMessage, 'Update failed');
      }
    );
  }

  onKycFile(docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF', event: any) {
    this.kycFileError = '';
    const file = event.target.files && event.target.files[0];
    if (!file) return;
    const allowed = ['image/jpeg', 'image/png', 'image/webp', 'application/pdf'];
    if (!allowed.includes(file.type)) {
      this.kycFileError = 'Only JPG, PNG, WEBP or PDF allowed.';
      return;
    }
    if (file.size > this.MAX_KYC_FILE_SIZE) {
      this.kycFileError = 'File is larger than 1 MB.';
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      this.kycFiles[docType] = { name: file.name, type: file.type, data: String(reader.result || '') };
    };
    reader.readAsDataURL(file);
  }

  clearKycFile(docType: 'PAN' | 'AADHAAR' | 'BANK_PROOF') {
    this.kycFiles[docType] = null;
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

  // Helpers
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
    return `₹${pkg}`;
  }
}
