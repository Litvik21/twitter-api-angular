import { Component, OnInit } from '@angular/core';
import { Post } from '../model/post';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-my-posts',
  templateUrl: './my-posts.component.html',
  styleUrls: ['./my-posts.component.scss']
})
export class MyPostsComponent implements OnInit {
  posts: Post[] = [];

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts(): void {
    this.postService.getPostOfCurrentUser()
      .subscribe(posts => {
        this.posts = posts
      });
  }

  deletePost(id: any) {
    this.postService.removePost(id).subscribe(() => {
      this.posts = this.posts.filter(post => post.id !== id);
    });
  }

}
