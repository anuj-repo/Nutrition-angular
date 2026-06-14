import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { BackendApiService } from '../_services/backend-api.service';

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.css']
})
export class TrainingComponent implements OnInit {
  courses: any[] = [];
  progress: any[] = [];
  collateral: any[] = [];
  banners: any[] = [];
  selectedTab = 'courses';
  successMessage = ''; errorMessage = '';

  constructor(private api: BackendApiService, private toastr: ToastrService) {}

  ngOnInit(): void { this.load(); }

  load() {
    forkJoin({
      c: this.api.courses().pipe(catchError(() => of(null as any))),
      p: this.api.myProgress().pipe(catchError(() => of(null as any))),
      m: this.api.collateral().pipe(catchError(() => of(null as any))),
      b: this.api.banners().pipe(catchError(() => of(null as any)))
    }).subscribe((res: any) => {
      this.courses = res.c?.data || [];
      this.progress = res.p?.data || [];
      this.collateral = res.m?.data || [];
      this.banners = res.b?.data || [];
    });
  }

  switchTab(t: string) { this.selectedTab = t; }

  isCompleted(courseId: number): boolean {
    return this.progress.some((p: any) => (p.course?.id || p.courseId) === courseId && (p.completed || p.completedDate));
  }

  complete(course: any) {
    this.api.completeCourse(course.id).subscribe(
      () => {
        this.successMessage = `Marked "${course.title}" complete.`;
        this.toastr.success(this.successMessage, 'Course completed');
        this.load();
      },
      err => {
        this.errorMessage = err?.error?.message || 'Action failed.';
        this.toastr.error(this.errorMessage, 'Failed');
      }
    );
  }
}
