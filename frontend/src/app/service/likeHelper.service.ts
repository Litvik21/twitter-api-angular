import { Injectable } from '@angular/core';
import { LikeService } from './like.service';
import { forkJoin, map, Observable, switchMap } from 'rxjs';
import { UserService } from './user.service';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class LikeHelperService {
  constructor(private likeService: LikeService,
              private userService: UserService) {
  }

  setLikesForPost(post: any, posts: Post[]): Observable<Post[]> {
    const likeObservables = [];

    for (const likeId of post.likeIds) {
      likeObservables.push(
        this.likeService.getLike(likeId).pipe(
          switchMap(like => this.userService.getUser(like.userId).pipe(
            map(user => {
              like.user = user;
              return like;
            })
          ))
        )
      );
    }

    return forkJoin(likeObservables).pipe(
      map(likes => {
        post.likes = likes.map(like => ({
          id: like.id,
          user: like.user
        }));

        const postIndex = posts.findIndex(p => p.id === post.id);
        if (postIndex !== -1) {
          posts = posts.map(p => (p.id === post.id ? post : p));
        }

        return posts;
      })
    );
  }
}
