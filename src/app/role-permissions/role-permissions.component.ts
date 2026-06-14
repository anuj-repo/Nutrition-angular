import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-role-permissions',
  templateUrl: './role-permissions.component.html',
  styleUrls: ['./role-permissions.component.css']
})
export class RolePermissionsComponent implements OnInit {
  // Static role list (backend RoleName enum / common roles)
  roles = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'User' },
    { id: 3, name: 'SubUser' },
    { id: 4, name: 'Manager' }
  ];

  selectedRoleId: number | null = null;
  rolePermissions: any[] = [];
  allPermissions: any[] = [];
  loading = false;
  message = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.api.permissions().subscribe(
      (r: any) => this.allPermissions = r?.data || [],
      () => {
        this.allPermissions = [];
        this.toastr.error('Could not load permissions.', 'Failed');
      }
    );
  }

  selectRole(r: any) {
    this.selectedRoleId = r.id;
    this.loading = true;
    this.api.rolePermissions(r.id).subscribe(
      (res: any) => { this.rolePermissions = res?.data || []; this.loading = false; },
      () => {
        this.rolePermissions = [];
        this.loading = false;
        this.toastr.error(`Could not load permissions for role ${r.name}.`, 'Failed');
      }
    );
  }

  hasPermission(p: any): boolean {
    return this.rolePermissions.some((rp: any) => (rp.id || rp.permissionId) === p.id);
  }
}
