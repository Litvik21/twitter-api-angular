import { Component, OnInit } from '@angular/core';
import { PostService } from '../service/post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.scss']
})
export class AddPostComponent implements OnInit {
  selectedImage: File | null = null;

  constructor(private postService: PostService, private router: Router) {}

  onImageSelected(event: any): void {
    this.selectedImage = event.target.files[0];
  }

  createPost(): void {
    if (!this.selectedImage) {
      return;
    }

    const formData = new FormData();
    formData.append('image', this.selectedImage);

    this.postService.addPost(formData).subscribe(
      response => {
        console.log('Post created successfully');
        this.router.navigate(['/main']);
      },
      error => {
        console.error('Error creating post:', error);
      }
    );
  }

  ngOnInit(): void {
  }

}
