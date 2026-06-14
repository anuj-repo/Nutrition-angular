import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../_services/user.service';

interface AdminTreeUser {
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
  packageTaken?: number | string;
  city?: string;
  state?: string;
  level?: number;
  parentName?: string;
  parentReferralCode?: string;
  children?: AdminTreeUser[];

  // UI-only state
  _open?: boolean;
  _matched?: boolean;        // true when the node itself matches the search
  _descendantMatch?: boolean; // true when something in the subtree matches
}

interface FilterState {
  startDate: string | null;
  endDate: string | null;
  specificDate: string | null;
  referralCode: string;
  mobileNumber: string;
  status: number | string;
}

@Component({
  selector: 'app-admin-user-tree',
  templateUrl: './admin-user-tree.component.html',
  styleUrls: ['./admin-user-tree.component.css']
})
export class AdminUserTreeComponent implements OnInit {

  loading = true;

  // Raw data from the backend (unmodified hierarchy)
  parents: AdminTreeUser[] = [];
  // Result after applying client-side search; same tree shape as `parents`
  filteredParents: AdminTreeUser[] = [];

  // Toolbar state
  searchTerm = '';
  statusFilter: 'all' | 'active' | 'inactive' = 'all';
  viewMode: 'cards' | 'tree' = 'cards';

  // Server-side filter (uses the same payload as /allTeam admin)
  filter: FilterState = {
    startDate: null,
    endDate: null,
    specificDate: null,
    referralCode: '',
    mobileNumber: '',
    status: ''
  };

  // Drawer for a single member
  selectedMember: AdminTreeUser | null = null;
  selectedAncestors: AdminTreeUser[] = [];

  // Avatar palette (matches My Team)
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
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  // ---------- Data loading ----------
  load(): void {
    this.loading = true;
    this.userService.getAllUserByAdmin(this.filter).subscribe(
      (res: any) => {
        const list: AdminTreeUser[] = Array.isArray(res?.data) ? res.data : [];
        this.parents = list.map(p => this.assignLevels(p, 1));
        this.applySearch();
        this.loading = false;
      },
      err => {
        console.error('Failed to load admin user tree', err);
        this.toastr.error('Could not load users. Please try again.', 'Error');
        this.loading = false;
      }
    );
  }

  resetFilters(): void {
    this.filter = {
      startDate: null,
      endDate: null,
      specificDate: null,
      referralCode: '',
      mobileNumber: '',
      status: ''
    };
    this.searchTerm = '';
    this.statusFilter = 'all';
    this.load();
  }

  applyServerFilters(): void {
    this.load();
  }

  private assignLevels(node: AdminTreeUser, level: number): AdminTreeUser {
    node.level = level;
    if (node.children?.length) {
      node.children = node.children.map(c => this.assignLevels(c, level + 1));
    }
    return node;
  }

  // ---------- Stats ----------
  get totalParents(): number {
    return this.parents.length;
  }
  get totalDownline(): number {
    return this.parents.reduce(
      (s, p) => s + (p.children?.length ? this.countAll(p.children) : 0),
      0
    );
  }
  get totalUsers(): number {
    return this.totalParents + this.totalDownline;
  }
  get totalActive(): number {
    return this.countActive(this.parents);
  }
  get totalInactive(): number {
    return this.totalUsers - this.totalActive;
  }

  private countAll(list: AdminTreeUser[]): number {
    return list.reduce(
      (sum, n) => sum + 1 + (n.children?.length ? this.countAll(n.children) : 0),
      0
    );
  }
  private countActive(list: AdminTreeUser[]): number {
    return list.reduce(
      (sum, n) =>
        sum + (this.isActive(n) ? 1 : 0) +
        (n.children?.length ? this.countActive(n.children) : 0),
      0
    );
  }

  // ---------- Search & status filter (client-side prune) ----------
  setStatusFilter(s: 'all' | 'active' | 'inactive'): void {
    this.statusFilter = s;
    this.applySearch();
  }

  applySearch(): void {
    const term = this.searchTerm.trim().toLowerCase();
    const status = this.statusFilter;

    if (!term && status === 'all') {
      // Clear flags so cards revert to user-controlled open state
      this.parents.forEach(p => this.clearMatchFlags(p));
      this.filteredParents = this.parents;
      return;
    }

    const matchesText = (m: AdminTreeUser): boolean => {
      if (!term) return true;
      const haystack = [m.fname, m.lname, m.email, m.mobileNumber, m.referralCode]
        .filter(Boolean).join(' ').toLowerCase();
      return haystack.includes(term);
    };
    const matchesStatus = (m: AdminTreeUser): boolean => {
      if (status === 'all') return true;
      return status === 'active' ? this.isActive(m) : !this.isActive(m);
    };

    const prune = (m: AdminTreeUser): AdminTreeUser | null => {
      const filteredChildren: AdminTreeUser[] = [];
      for (const c of m.children || []) {
        const fc = prune(c);
        if (fc) filteredChildren.push(fc);
      }
      const selfHits = matchesText(m) && matchesStatus(m);
      if (selfHits || filteredChildren.length > 0) {
        return {
          ...m,
          children: filteredChildren,
          _matched: selfHits,
          _descendantMatch: filteredChildren.length > 0,
          // Auto-expand when a search is in effect so matches are visible
          _open: term ? true : m._open
        };
      }
      return null;
    };

    const result: AdminTreeUser[] = [];
    for (const p of this.parents) {
      const f = prune(p);
      if (f) result.push(f);
    }
    this.filteredParents = result;
  }

  private clearMatchFlags(node: AdminTreeUser): void {
    node._matched = false;
    node._descendantMatch = false;
    (node.children || []).forEach(c => this.clearMatchFlags(c));
  }

  // ---------- Expand / collapse all ----------
  expandAll(): void {
    this.filteredParents.forEach(p => this.setOpen(p, true));
  }
  collapseAll(): void {
    this.filteredParents.forEach(p => this.setOpen(p, false));
  }
  private setOpen(node: AdminTreeUser, open: boolean): void {
    node._open = open;
    (node.children || []).forEach(c => this.setOpen(c, open));
  }

  // ---------- Selection (drawer) ----------
  selectMember(node: AdminTreeUser): void {
    this.selectedMember = node;
    this.selectedAncestors = this.findAncestors(this.parents, node) || [];
  }
  closeMember(): void {
    this.selectedMember = null;
    this.selectedAncestors = [];
  }

  private findAncestors(list: AdminTreeUser[], target: AdminTreeUser, trail: AdminTreeUser[] = []): AdminTreeUser[] | null {
    for (const n of list) {
      if (n.userId === target.userId) return trail;
      if (n.children?.length) {
        const found = this.findAncestors(n.children, target, [...trail, n]);
        if (found) return found;
      }
    }
    return null;
  }

  // ---------- Helpers used by the template ----------
  isActive(u: AdminTreeUser | null | undefined): boolean {
    if (!u) return false;
    const s = (u.status ?? '').toString().toLowerCase();
    return s === '1' || s === 'active';
  }

  initials(u: AdminTreeUser | null | undefined): string {
    if (!u) return '👤';
    const f = (u.fname || '').trim();
    const l = (u.lname || '').trim();
    if (!f && !l) return '👤';
    return ((f.charAt(0) || '') + (l.charAt(0) || '')).toUpperCase();
  }

  avatarColor(u: AdminTreeUser | null | undefined): string {
    const key = `${u?.fname || ''}${u?.lname || ''}${u?.email || ''}`;
    let hash = 0;
    for (let i = 0; i < key.length; i++) hash = (hash * 31 + key.charCodeAt(i)) >>> 0;
    return this.palette[hash % this.palette.length];
  }

  copyCode(code: string | undefined, ev?: Event): void {
    if (ev) ev.stopPropagation();
    if (!code) return;
    navigator.clipboard.writeText(code).then(
      () => this.toastr.success(code, 'Code copied'),
      () => this.toastr.error('Could not copy code')
    );
  }

  toggle(node: AdminTreeUser, ev?: Event): void {
    if (ev) ev.stopPropagation();
    node._open = !node._open;
  }

  childCount(u: AdminTreeUser | null | undefined): number {
    return u?.children?.length || 0;
  }

  // Recursive total (whole subtree size including self)
  subtreeCount(u: AdminTreeUser | null | undefined): number {
    if (!u) return 0;
    let total = 1;
    (u.children || []).forEach(c => total += this.subtreeCount(c));
    return total;
  }
}
