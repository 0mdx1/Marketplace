import {Role} from './role';

export default interface Token {
  authorities: [Role];
  sub: string;
}
