import { Role } from './role';

export class User {
  name?: string;
  surname?: string;
  password?: string;
  email?: string;
  dateOfBirth?: string;
  phone?: string;
  role?: Role;
}
