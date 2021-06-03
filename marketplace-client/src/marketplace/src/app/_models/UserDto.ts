import { StaffMember } from './staff-member';

export class UserDto {
  users: StaffMember[] = [];
  pageNum: number = 1;
  currentPage: number = 1;
}
