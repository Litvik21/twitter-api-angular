import { Role } from './role';

export interface UserAccount {
  id: number;
  username: string;
  password: string;
  role: Role[];
}
