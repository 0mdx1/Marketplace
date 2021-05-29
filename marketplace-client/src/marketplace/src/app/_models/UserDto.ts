import { User } from './user';

export class UserDto {
  users: User[] = [];
  pageNum: number = 1;
  currentPage: number = 1;
}
