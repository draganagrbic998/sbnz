import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RenewalDialogComponent } from './renewal-dialog.component';

describe('RenewalDialogComponent', () => {
  let component: RenewalDialogComponent;
  let fixture: ComponentFixture<RenewalDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RenewalDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RenewalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
