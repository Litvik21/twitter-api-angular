import { Role } from './role';

export interface UserAccount {
  id: number;
  username: string;
  role: Role[];
}
