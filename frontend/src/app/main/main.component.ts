import { Component, OnInit } from '@angular/core';
import { PostService } from '../service/post.service';
import { Post } from '../model/post';
import { UserService } from '../service/user.service';
import { forkJoin, map, switchMap } from 'rxjs';
import { Router } from '@angular/router';
import { LikeService } from '../service/like.service';
import { LikeHelperService } from '../service/likeHelper.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  searchTerm: string = '';
  posts: Post[] = [];
  isLiked: boolean = false;
  showFullDescription: boolean = false;

  constructor(private postService: PostService,
              private userService: UserService,
              private likeService: LikeService,
              private router: Router) { }

  ngOnInit(): void {
    this.getPosts();
  }

  toggleLike(id: any) {
    this.postService.addOrRemoveLike(id).subscribe(post => {
      this.getPosts();
    });
  }

  toggleDescription(post: any) {
    this.showFullDescription = !this.showFullDescription;
  }

  getPosts(): void {
    this.postService.getPosts().subscribe(
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
            user: like.user,
            isLike: like.user.username === localStorage.getItem("user")
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
    this.router.navigate(['/postsByUser', this.searchTerm]);
  }
}
