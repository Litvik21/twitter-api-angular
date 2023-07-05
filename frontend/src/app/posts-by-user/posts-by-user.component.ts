import { Component, OnInit } from '@angular/core';
import { Post } from '../model/post';
import { PostService } from '../service/post.service';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, map, switchMap } from 'rxjs';
import { LikeService } from '../service/like.service';

@Component({
  selector: 'app-posts-by-user',
  templateUrl: './posts-by-user.component.html',
  styleUrls: ['./posts-by-user.component.scss']
})
export class PostsByUserComponent implements OnInit {
  searchTerm: string = '';
  posts: Post[] = [];
  isLiked: boolean = false;
  username = this.route.snapshot.paramMap.get('searchTerm');
  constructor(private postService: PostService,
              private userService: UserService,
              private likeService: LikeService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getPosts();
  }

  toggleLike(id: any) {
    this.isLiked = !this.isLiked;
    this.postService.addOrRemoveLike(id).subscribe(post => {
      this.getPosts();
    });
  }

  getPosts(): void {
    if (this.username != null) {
      this.postService.getPostsOfUser(this.username).subscribe(
        posts => {
          if (posts.length < 0) {
            console.log("Don't have posts yet!")
            return;
          }
          const getUserObservables = [];

          for (const post of posts) {
            getUserObservables.push(this.userService.getUser(post.userId));
            if (post.likeIds.length > 0) {
              this.setLikesForPost(post);
            }
          }

          forkJoin(getUserObservables).subscribe(
            users => {
              for (let i = 0; i < posts.length; i++) {
                posts[i].user = users[i];
              }
              this.posts = posts;
            }
          );
        }
      );
    }
  }

  setLikesForPost(post: any): void {
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

    forkJoin(likeObservables).subscribe(
      likes => {
        post.likes = likes.map(like => {
          return {
            id: like.id,
            user: like.user
          };
        });

        const postIndex = this.posts.findIndex(p => p.id === post.id);
        if (postIndex !== -1) {
          this.posts = this.posts.map(p => (p.id === post.id ? post : p));
        }
      }
    );
  }

  searchByUsername(): void {
    this.username = this.searchTerm;
    this.getPosts();
  }

  clearSearching(): void {
    this.router.navigate(['/main']);
  }

}
