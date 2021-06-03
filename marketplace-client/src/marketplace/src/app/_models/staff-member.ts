import { Role } from './role';

export interface StaffMember {
  id: number;
  name: string;
  surname: string;
  email: string;
  dateOfBirth: string;
  phone: string;
  role: Role;
  status: string;
}
