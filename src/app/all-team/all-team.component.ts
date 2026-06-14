import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { ToastrService } from 'ngx-toastr';
import { UtililtyFunctions } from '../utils/utils';

interface TeamUser {
  userId?: string | number;
  fname?: string;
  lname?: string;
  email?: string;
  mobileNumber?: string;
  referralCode?: string;
  status?: number | string;
  totalAccountBalance?: number | string;
  paidBalance?: number | string;
  accountBalance?: number | string;
  children?: TeamUser[];
  // UI helpers (not from backend)
  _open?: boolean;
  _activating?: boolean;
  _amountInput?: number | null;
  _showActivate?: boolean;
}

@Component({
  selector: 'app-all-team',
  templateUrl: './all-team.component.html',
  styleUrls: ['./all-team.component.css']
})
export class AllTeamComponent implements OnInit {

  userHierarchy: any;
  parents: TeamUser[] = [];
  loading = false;
  welcomeName = '';

  filter: {
    startDate: string | null;
    endDate: string | null;
    specificDate: string | null;
    referralCode: string;
    mobileNumber: string;
    status: number | string;
  } = {
    startDate: null,
    endDate: null,
    specificDate: null,
    referralCode: '',
    mobileNumber: '',
    status: 1
  };

  // Stable colour palette for avatars
  private palette = [
    'linear-gradient(135deg, #4caf50, #2e7d32)',
    'linear-gradient(135deg, #42a5f5, #1565c0)',
    'linear-gradient(135deg, #ab47bc, #6a1b9a)',
    'linear-gradient(135deg, #ffd700, #ff9800)',
    'linear-gradient(135deg, #ef5350, #c62828)',
    'linear-gradient(135deg, #00d4aa, #00897b)',
    'linear-gradient(135deg, #ff7043, #d84315)',
    'linear-gradient(135deg, #5c6bc0, #283593)'
  ];

  constructor(
    private userService: UserService,
    private toastr: ToastrService,
    private utils: UtililtyFunctions
  ) { }

  ngOnInit(): void {
    const me = this.utils.getUserMeData();
    this.welcomeName = (me?.fname || me?.firstName || me?.name || 'Admin').toString().trim();
    this.getMyTeam();
  }

  getMyTeam() {
    this.loading = true;
    this.userService.getAllUserByAdmin(this.filter).subscribe(
      (response: any) => {
        this.userHierarchy = response;
        this.parents = Array.isArray(response?.data) ? response.data : [];
        this.loading = false;
      },
      (error) => {
        console.log(error);
        this.loading = false;
        this.toastr.error('Could not load team. Please try again.', 'Error');
      }
    );
  }

  resetFilters() {
    this.filter = {
      startDate: null,
      endDate: null,
      specificDate: null,
      referralCode: '',
      mobileNumber: '',
      status: 1
    };
    this.getMyTeam();
  }

  // ===== Stats =====
  get totalParents(): number {
    return this.parents?.length || 0;
  }

  get totalDownline(): number {
    return this.parents.reduce(
      (s, p) => s + (p.children?.length ? this.countAll(p.children) : 0),
      0
    );
  }

  get totalActive(): number {
    return this.countActive(this.parents);
  }

  get totalInactive(): number {
    const all = this.countAll(this.parents);
    return all - this.totalActive;
  }

  private countAll(list: TeamUser[]): number {
    return list.reduce(
      (sum, n) => sum + 1 + (n.children?.length ? this.countAll(n.children) : 0),
      0
    );
  }

  private countActive(list: TeamUser[]): number {
    return list.reduce(
      (sum, n) =>
        sum + (this.isActive(n) ? 1 : 0) +
        (n.children?.length ? this.countActive(n.children) : 0),
      0
    );
  }

  // ===== Helpers =====
  isActive(u: TeamUser): boolean {
    if (!u) return false;
    const s = (u.status ?? '').toString().toLowerCase();
    return s === '1' || s === 'active';
  }

  initials(u: TeamUser): string {
    const f = (u?.fname || '').trim();
    const l = (u?.lname || '').trim();
    if (!f && !l) return '👤';
    return ((f.charAt(0) || '') + (l.charAt(0) || '')).toUpperCase();
  }

  avatarColor(u: TeamUser): string {
    const key = `${u?.fname || ''}${u?.lname || ''}${u?.email || ''}`;
    let hash = 0;
    for (let i = 0; i < key.length; i++) hash = (hash * 31 + key.charCodeAt(i)) >>> 0;
    return this.palette[hash % this.palette.length];
  }

  copyCode(code: string | undefined) {
    if (!code) return;
    navigator.clipboard.writeText(code).then(
      () => this.toastr.success(code, 'Code copied'),
      () => this.toastr.error('Could not copy code')
    );
  }

  toggleActivatePanel(user: TeamUser, event?: Event) {
    if (event) event.stopPropagation();
    user._showActivate = !user._showActivate;
    if (user._showActivate && (user._amountInput == null || isNaN(Number(user._amountInput)))) {
      user._amountInput = 1000;
    }
  }

  activateUserWithAmount(user: TeamUser) {
    const amount = Number(user._amountInput);
    if (!amount || amount < 1000) {
      this.toastr.warning('Please enter an amount of at least ₹1000.', 'Invalid amount');
      return;
    }

    user._activating = true;
    const params = {
      userId: user.userId,
      amount: amount
    };

    this.userService.updatePaymentByAdmin(params).subscribe(
      () => {
        const fullName = `${user.fname || ''} ${user.lname || ''}`.trim() || 'User';
        this.toastr.success(`${fullName} activated with ₹${amount}.`, 'Success');
        user._activating = false;
        user._showActivate = false;
        user._amountInput = null;
        this.getMyTeam();
      },
      (error) => {
        console.log(error);
        user._activating = false;
        this.toastr.error('Could not activate user. Please try again.', 'Error');
      }
    );
  }
}
