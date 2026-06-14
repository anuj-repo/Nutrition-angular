import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTeamComponent } from './all-team.component';

describe('AllTeamComponent', () => {
  let component: AllTeamComponent;
  let fixture: ComponentFixture<AllTeamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTeamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
