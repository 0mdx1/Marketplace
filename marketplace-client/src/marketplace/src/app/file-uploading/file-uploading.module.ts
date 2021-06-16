import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ImageUploadingComponent} from "./_components/image-uploading/image-uploading.component";



@NgModule({
  declarations: [
    ImageUploadingComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ImageUploadingComponent
  ]
})
export class FileUploadingModule { }
