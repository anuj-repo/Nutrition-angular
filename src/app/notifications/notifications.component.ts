import { Component, OnInit } from '@angular/core';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications: any[] = [];
  filter: 'all' | 'unread' = 'all';

  constructor(private api: BackendApiService) {}

  ngOnInit(): void { this.load(); }

  load() {
    this.api.notifications().subscribe(
      (res: any) => { this.notifications = res?.data || []; },
      () => { this.notifications = []; }
    );
  }

  get filtered() {
    if (this.filter === 'unread') return this.notifications.filter(n => !n.isRead);
    return this.notifications;
  }

  get unreadCount() {
    return this.notifications.filter(n => !n.isRead).length;
  }

  markRead(n: any) {
    if (n.isRead) return;
    this.api.markRead(n.id).subscribe(() => { n.isRead = true; });
  }

  markAllRead() {
    this.api.markAllRead().subscribe(() => {
      this.notifications.forEach(n => n.isRead = true);
    });
  }

  iconFor(type: string): string {
    const t = (type || '').toLowerCase();
    if (t.includes('commission')) return '💰';
    if (t.includes('reward')) return '🎁';
    if (t.includes('rank')) return '🏆';
    if (t.includes('order')) return '📦';
    if (t.includes('withdraw') || t.includes('payout')) return '💸';
    if (t.includes('kyc')) return '📋';
    return '🔔';
  }
}
