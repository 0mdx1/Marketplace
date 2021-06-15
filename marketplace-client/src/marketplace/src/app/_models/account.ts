import {Role} from './role';

export class Account {
  email?: string;
  role?: Role;

  constructor(email?: string, role?: Role) {
    this.email = email;
    this.role = role;
  }
}
