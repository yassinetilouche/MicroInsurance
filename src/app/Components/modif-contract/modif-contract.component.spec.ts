import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifContractComponent } from './modif-contract.component';

describe('ModifContractComponent', () => {
  let component: ModifContractComponent;
  let fixture: ComponentFixture<ModifContractComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifContractComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifContractComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
