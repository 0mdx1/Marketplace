import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterStuffComponent } from './register-stuff.component';

describe('CourierComponent', () => {
  let component: RegisterStuffComponent;
  let fixture: ComponentFixture<RegisterStuffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterStuffComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterStuffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
