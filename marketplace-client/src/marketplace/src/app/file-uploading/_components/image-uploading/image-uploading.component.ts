import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Observable} from "rxjs";
import {FileService} from "../../../_services/file.service";

@Component({
  selector: 'app-image-uploading',
  templateUrl: './image-uploading.component.html',
  styleUrls: ['./image-uploading.component.css']
})
export class ImageUploadingComponent implements OnChanges{

  @Input() imageUrl: string = '';

  @Output() imageFilenameEvent: EventEmitter<string> = new EventEmitter<string>();

  public isLoading: boolean = false;

  constructor(private fileService: FileService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.imageFilenameEvent.emit(this.getFilename(changes.imageUrl.currentValue))
  }

  private getFilename(url:string): string {
    return <string>url.split('/').pop();
  }

  // @ts-ignore
  handleFileInput(event) {
    let files: FileList = event.target.files;
    let fileToUpload = files.item(0);
    if (fileToUpload) {
      this.isLoading = true;
      this.fileService.upload(fileToUpload).subscribe({
        next: fileMetadata => {
          this.isLoading=false;
          this.imageUrl = fileMetadata.resourceUrl;
          this.imageFilenameEvent.emit(fileMetadata.filename)
          console.log(fileMetadata);
        },
        error: e=>{
          this.isLoading=false;
          console.log(e)
        }
      });
    }
  }

}
