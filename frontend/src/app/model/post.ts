import { Like } from './like';
import { UserAccount } from './userAccount';

export interface Post {
  id: number;
  userId: number;
  imagePath: string;
  description: string;
  likeIds: number[];
  likes: Like[];
  user: UserAccount;
  dateCreating: Date;
}
