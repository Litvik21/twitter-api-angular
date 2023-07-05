import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { AddPostComponent } from './add-post/add-post.component';
import { MyPostsComponent } from './my-posts/my-posts.component';
import { PostsByUserComponent } from './posts-by-user/posts-by-user.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'main', component: MainComponent },
  { path: 'addPost', component: AddPostComponent },
  { path: 'myPosts', component: MyPostsComponent },
  { path: 'postsByUser/:searchTerm', component: PostsByUserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
