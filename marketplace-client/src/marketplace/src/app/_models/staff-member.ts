import {Role} from './role';

export interface StaffMember {
  name: string;
  surname: string;
  email: string;
  dateOfBirth: string;
  phone: string;
  role: Role;
  status: string;
}
