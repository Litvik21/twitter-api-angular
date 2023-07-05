package structure.twitterapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import structure.twitterapi.dto.PostResponseDto;
import structure.twitterapi.dto.mapper.PostMapper;
import structure.twitterapi.model.Post;
import structure.twitterapi.service.PostService;
import structure.twitterapi.service.UserAccountService;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserAccountService userService;

    @PostMapping("/add")
    public ResponseEntity<Object> addPost(@RequestParam("image") MultipartFile imageFile, Authentication auth) {
        postService.addPost(auth.getName(), imageFile);
        return ResponseEntity.ok().body("{\"message\": \"Image uploaded successfully\"}");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") Long postId,
                                             Authentication auth) {
        postService.deletePost(auth.getName(), postId);
        return ResponseEntity.ok().body("{\"message\": \"Post deleted successfully\"}");
    }

    @GetMapping("/username")
    public List<PostResponseDto> getAllByUser(@RequestParam(name = "username") String username) {
        return postService.findPostsByUserId(userService.getIdByUsername(username)).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @GetMapping("/my")
    public List<PostResponseDto> getAllByCurrentUser(Authentication auth) {
        return postService.findPostsByUserId(userService.getIdByUsername(auth.getName())).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @GetMapping("/like/{id}")
    public PostResponseDto addOrRemoveLike(@PathVariable("id") Long postId,
                                           Authentication auth) {
        Post post = postService.addLike(auth.getName(), postId);
        return postMapper.toDto(post);
    }

    @GetMapping
    public List<PostResponseDto> getAll() {
        return postService.findAll().stream()
                .map(postMapper::toDto)
                .toList();
    }
}
