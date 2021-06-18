export class FileMetadata {
  filename: string;
  resourceUrl: string;

  constructor(filename: string, resourceUrl: string) {
    this.filename = filename;
    this.resourceUrl = resourceUrl;
  }
}
