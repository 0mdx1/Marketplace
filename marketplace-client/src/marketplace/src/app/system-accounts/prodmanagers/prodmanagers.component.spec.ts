import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdmanagersComponent } from './prodmanagers.component';

describe('ProdmanagersComponent', () => {
  let component: ProdmanagersComponent;
  let fixture: ComponentFixture<ProdmanagersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProdmanagersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProdmanagersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
