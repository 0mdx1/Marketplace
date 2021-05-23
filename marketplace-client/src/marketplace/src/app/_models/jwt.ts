import {Role} from './role';

export default interface Token {
  email: string;
  role: Role;
}
