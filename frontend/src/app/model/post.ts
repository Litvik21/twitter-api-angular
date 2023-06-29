import { Like } from './like';
import { UserAccount } from './userAccount';

export interface Post {
  id: number;
  imagePath: string;
  likes: Like[];
  user: UserAccount;
  dateCreating: Date;
}
