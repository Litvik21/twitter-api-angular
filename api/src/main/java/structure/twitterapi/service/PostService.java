package structure.twitterapi.service;

import org.springframework.web.multipart.MultipartFile;
import structure.twitterapi.model.Post;
import java.util.List;

public interface PostService {
    Post addPost(String username, String description, MultipartFile imageFile);

    boolean deletePost(String username, Long postId);

    List<Post> findPostsByUserId(Long userId);

    Post addLike(String username, Long postId);

    Post get(Long postId);

    List<Post> findAll();
}
