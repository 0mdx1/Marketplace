import { Role } from './role';

export class Courier {
  id?: number;
  name?: string;
  surname?: string;
  password?: string;
  email?: string;
  dateOfBirth?: string;
  phone?: string;
  role?: Role;
  active?: boolean;
  enabled?: boolean;
}
