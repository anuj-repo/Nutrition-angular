import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-network-tree',
  templateUrl: './network-tree.component.html',
  styleUrls: ['./network-tree.component.css']
})
export class NetworkTreeComponent implements OnInit {
  tree: any = null;
  selectedMember: any = null;
  loading = true;
  expanded = new Set<number>();
  selectedLevel = 15;

  constructor(private api: BackendApiService) {}

  ngOnInit(): void { this.load(); }

  load() {
    this.loading = true;
    this.api.networkTree(this.selectedLevel).subscribe(
      (res: any) => { this.tree = res?.data; this.loading = false; if (this.tree?.id) this.expanded.add(this.tree.id); },
      () => { this.loading = false; this.tree = null; }
    );
  }

  toggle(node: any) {
    if (this.expanded.has(node.id)) this.expanded.delete(node.id);
    else this.expanded.add(node.id);
  }

  isExpanded(node: any): boolean { return this.expanded.has(node.id); }

  expandAll() {
    this.collectIds(this.tree).forEach(id => this.expanded.add(id));
  }
  collapseAll() { this.expanded.clear(); if (this.tree?.id) this.expanded.add(this.tree.id); }

  private collectIds(node: any): number[] {
    if (!node) return [];
    let ids = [node.id];
    (node.children || []).forEach((c: any) => ids = ids.concat(this.collectIds(c)));
    return ids;
  }

  selectMember(node: any) {
    this.api.memberDetail(node.id).subscribe(
      (res: any) => { this.selectedMember = res?.data; },
      () => { this.selectedMember = node; }
    );
  }

  closeMember() { this.selectedMember = null; }
}
