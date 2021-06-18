import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {FileMetadata} from "../_models/FileMetadata";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient){ }

  upload(file: File): Observable<FileMetadata> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    const httpOptions = {
      headers: new HttpHeaders()
    };
    return this.http.post<FileMetadata>(`${baseUrl}/media/`,formData,httpOptions);
  }
}
