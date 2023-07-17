package structure.twitterapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import structure.twitterapi.lib.FileUtil;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private static final String POST_PATH_REPO = "frontend/src/assets/images/";
    private static final String PATH_FOR_ANGULAR = "../assets/images/";
    private final PostRepository repository;
    private final UserAccountService accountService;
    private final LikeService likeService;
    private final FileUtil fileUtil;

    @Override
    public Post addPost(String username, String description, MultipartFile imageFile) {
        UserAccount user = accountService.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Can't find user with username: " + username));

        String fileName = fileUtil.generateUniqueFileName(imageFile.getOriginalFilename());
        String imagePath = POST_PATH_REPO + fileName;

        fileUtil.saveImage(imageFile, imagePath);

        Post post = new Post();
        post.setUser(user);
        post.setDescription(description);
        post.setImagePath(PATH_FOR_ANGULAR + fileName);
        post.setDateCreating(LocalDateTime.now());
        return repository.save(post);
    }

    @Override
    public boolean deletePost(String username, Long postId) {
        Post post = get(postId);

        if (username.equals(post.getUser().getUsername())) {
            fileUtil.deleteImage(post.getImagePath());
            repository.delete(post);
            return true;
        }
        throw new RuntimeException("You can't delete someone else's post!");
    }

    @Override
    public List<Post> findPostsByUserId(Long userId) {
        UserAccount user = accountService.get(userId);
        return repository.findAllByUser(user);
    }

    @Override
    public Post addLike(String username, Long postId) {
        UserAccount user = accountService.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Can't find user with username: " + username));
        Post post = get(postId);

        List<Like> likes = post.getLikes();
        Optional<Like> like = likeService.findByUserAndPost(user, post);

        if (like.isPresent()) {
            likes.remove(like.get());
            post.setLikes(likes);
            likeService.delete(like.get());
        } else {
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            likes.add(likeService.save(newLike));
            post.setLikes(likes);
        }

        return repository.save(post);
    }

    @Override
    public Post get(Long postId) {
        return repository.findById(postId).orElseThrow(() ->
                new RuntimeException("Can't find post by id: " + postId));
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

}
