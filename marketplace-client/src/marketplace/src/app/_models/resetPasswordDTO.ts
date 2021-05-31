export class ResetPasswordDTO {
  link?: string;
  password?: string;

  constructor(link: string, password: string) {
    this.link = link;
    this.password = password;
  }
}
