package structure.twitterapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import structure.twitterapi.dto.PostResponseDto;
import structure.twitterapi.dto.mapper.PostMapper;
import structure.twitterapi.model.Post;
import structure.twitterapi.service.PostService;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestParam("image") MultipartFile imageFile,
                                          Authentication auth) {
        postService.addPost(auth.getName(), imageFile);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId,
                                             Authentication auth) {
        postService.deletePost(auth.getName(), postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/all/{id}")
    public List<PostResponseDto> getAllByUser(@PathVariable("id") Long userId) {
        return postService.findPostsByUserId(userId).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @GetMapping("/like/{id}")
    public PostResponseDto addOrRemoveLike(@PathVariable("id") Long postId,
                                           Authentication auth) {
        Post post = postService.addLike(auth.getName(), postId);
        return postMapper.toDto(post);
    }
}
