import { UserAccount } from './userAccount';

export interface Like {
  id: number;
  userId: number;
  user: UserAccount;
  isLike: boolean;
}
