import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContartRenouvComponent } from './contart-renouv.component';

describe('ContartRenouvComponent', () => {
  let component: ContartRenouvComponent;
  let fixture: ComponentFixture<ContartRenouvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContartRenouvComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContartRenouvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
