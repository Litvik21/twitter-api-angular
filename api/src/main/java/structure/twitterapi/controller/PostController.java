package structure.twitterapi.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Create new post")
    public ResponseEntity<Object> addPost(@RequestParam("image") MultipartFile imageFile,
                                          @RequestParam("desc") String description, Authentication auth) {
        postService.addPost(auth.getName(), description, imageFile);
        return ResponseEntity.ok().body("{\"message\": \"Post uploaded successfully\"}");
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "Remove post by id")
    public ResponseEntity<Object> deletePost(@PathVariable("id") Long postId,
                                             Authentication auth) {
        postService.deletePost(auth.getName(), postId);
        return ResponseEntity.ok().body("{\"message\": \"Post deleted successfully\"}");
    }

    @GetMapping("/username")
    @ApiOperation(value = "Find posts of searching user")
    public List<PostResponseDto> getAllByUser(@RequestParam(name = "username") String username) {
        return postService.findPostsByUserId(userService.getIdByUsername(username)).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @GetMapping("/my")
    @ApiOperation(value = "Find posts of current user")
    public List<PostResponseDto> getAllByCurrentUser(Authentication auth) {
        return postService.findPostsByUserId(userService.getIdByUsername(auth.getName())).stream()
                .map(postMapper::toDto)
                .toList();
    }

    @GetMapping("/like/{id}")
    @ApiOperation(value = "Set or remove like of post")
    public PostResponseDto addOrRemoveLike(@PathVariable("id") Long postId,
                                           Authentication auth) {
        Post post = postService.addLike(auth.getName(), postId);
        return postMapper.toDto(post);
    }

    @GetMapping
    @ApiOperation(value = "Get all posts")
    public List<PostResponseDto> getAll() {
        return postService.findAll().stream()
                .map(postMapper::toDto)
                .toList();
    }
}
