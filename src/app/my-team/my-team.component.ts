import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import { NestedTreeControl } from '@angular/cdk/tree';
import { ToastrService } from 'ngx-toastr';

export interface UserNode {
  userId?: string;
  fname?: string;
  lname?: string;
  email?: string;
  mobileNumber?: string;
  totalAccountBalance?: number | string;
  paidBalance?: number | string;
  accountBalance?: number | string;
  referralCode?: string;
  status?: string;
  level?: number;
  children?: UserNode[];
  // UI-only flag, not from backend
  _open?: boolean;
}

@Component({
  selector: 'app-my-team',
  templateUrl: './my-team.component.html',
  styleUrls: ['./my-team.component.css']
})
export class MyTeamComponent implements OnInit {

  // Original (raw) and filtered lists for the card view
  allMembers: UserNode[] = [];
  filteredMembers: UserNode[] = [];

  // Tree view (uses children property recursively)
  treeControl = new NestedTreeControl<UserNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<UserNode>();

  // UI state
  loading = true;
  viewMode: 'cards' | 'tree' = 'cards';
  searchTerm = '';
  statusFilter: 'all' | 'active' | 'inactive' = 'all';

  // Stable colour palette for avatars (hashed from name)
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

  hasChild = (_: number, node: UserNode) =>
    !!node.children && node.children.length > 0;

  ngOnInit(): void {
    this.loadTeam();
  }

  loadTeam() {
    this.loading = true;
    this.userService.getAllUser().subscribe(
      (response: any) => {
        const data = response?.data;
        let list: UserNode[] = [];

        if (Array.isArray(data)) {
          list = data;
        } else if (data?.children) {
          // Legacy shape: top-level user with children — show only the children
          // (the logged-in user is themselves, not really a "team member").
          list = data.children;
        } else if (data) {
          list = [data];
        }

        // Tag levels (1 = direct, 2 = downline-of-downline, ...)
        this.allMembers = list.map(m => this.assignLevels(m, 1));
        this.dataSource.data = this.allMembers;
        this.applyFilters();
        this.loading = false;
      },
      err => {
        console.error('Failed to load team', err);
        this.toastr.error('Could not load your team. Please try again.', 'Error');
        this.loading = false;
      }
    );
  }

  private assignLevels(node: UserNode, level: number): UserNode {
    node.level = level;
    if (node.children?.length) {
      node.children = node.children.map(c => this.assignLevels(c, level + 1));
    }
    return node;
  }

  // ===== Stats =====
  get totalMembers(): number {
    return this.countAll(this.allMembers);
  }

  get activeMembers(): number {
    return this.countActive(this.allMembers);
  }

  private countAll(list: UserNode[]): number {
    return list.reduce(
      (sum, n) => sum + 1 + (n.children?.length ? this.countAll(n.children) : 0),
      0
    );
  }

  private countActive(list: UserNode[]): number {
    return list.reduce(
      (sum, n) =>
        sum + (this.isActive(n) ? 1 : 0) + (n.children?.length ? this.countActive(n.children) : 0),
      0
    );
  }

  // ===== Filters =====
  setStatusFilter(f: 'all' | 'active' | 'inactive') {
    this.statusFilter = f;
    this.applyFilters();
  }

  applyFilters() {
    const term = this.searchTerm.trim().toLowerCase();
    const status = this.statusFilter;

    // No search term and "all" status → show the full tree as-is so the
    // user's existing _open / collapse state is preserved.
    if (!term && status === 'all') {
      this.filteredMembers = this.allMembers;
      return;
    }

    const matchesText = (m: UserNode): boolean => {
      if (!term) return true;
      const haystack = [m.fname, m.lname, m.email, m.mobileNumber, m.referralCode]
        .filter(Boolean)
        .join(' ')
        .toLowerCase();
      return haystack.includes(term);
    };

    const matchesStatus = (m: UserNode): boolean => {
      if (status === 'all') return true;
      return status === 'active' ? this.isActive(m) : !this.isActive(m);
    };

    // Recursive prune: keep a node if it matches itself, or if any descendant
    // matches. Non-matching branches are dropped. Surviving subtrees auto-open
    // when a search term is active so the matched person is actually visible.
    const filterNode = (m: UserNode): UserNode | null => {
      const filteredChildren: UserNode[] = [];
      for (const c of m.children || []) {
        const fc = filterNode(c);
        if (fc) filteredChildren.push(fc);
      }

      const selfHits = matchesText(m) && matchesStatus(m);
      if (selfHits || filteredChildren.length > 0) {
        return {
          ...m,
          children: filteredChildren,
          _open: term ? true : m._open
        };
      }
      return null;
    };

    const result: UserNode[] = [];
    for (const m of this.allMembers) {
      const f = filterNode(m);
      if (f) result.push(f);
    }
    this.filteredMembers = result;
  }

  // ===== Helpers used by template =====
  isActive(m: UserNode): boolean {
    if (!m) return false;
    const s = (m.status ?? '').toString().toLowerCase();
    return s === '1' || s === 'active';
  }

  initials(m: UserNode): string {
    const f = (m?.fname || '').trim();
    const l = (m?.lname || '').trim();
    if (!f && !l) return '👤';
    const a = f.charAt(0).toUpperCase();
    const b = l.charAt(0).toUpperCase();
    return (a + b) || a;
  }

  avatarColor(m: UserNode): string {
    const key = `${m?.fname || ''}${m?.lname || ''}${m?.email || ''}`;
    let hash = 0;
    for (let i = 0; i < key.length; i++) hash = (hash * 31 + key.charCodeAt(i)) >>> 0;
    return this.palette[hash % this.palette.length];
  }

  copyCode(code: string) {
    if (!code) return;
    navigator.clipboard.writeText(code).then(
      () => this.toastr.success(code, 'Code copied'),
      () => this.toastr.error('Could not copy code')
    );
  }
}
