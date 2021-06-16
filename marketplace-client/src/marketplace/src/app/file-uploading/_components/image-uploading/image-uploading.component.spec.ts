import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageUploadingComponent } from './image-uploading.component';

describe('ImageUploadingComponent', () => {
  let component: ImageUploadingComponent;
  let fixture: ComponentFixture<ImageUploadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImageUploadingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageUploadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
