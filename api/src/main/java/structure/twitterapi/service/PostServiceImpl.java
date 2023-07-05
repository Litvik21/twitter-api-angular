package structure.twitterapi.service;

import static structure.twitterapi.lib.FileUtil.generateUniqueFileName;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.repository.PostRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private static final String POST_PATH_REPO = "frontend/src/assets/images/";
    private static final String PATH_FOR_ANGULAR = "../assets/images/";
    private final PostRepository repository;
    private final UserAccountService accountService;
    private final LikeService likeService;

    public PostServiceImpl(PostRepository repository, UserAccountService accountService,
                           LikeService likeService) {
        this.repository = repository;
        this.accountService = accountService;
        this.likeService = likeService;
    }

    @Override
    public Post addPost(String username, MultipartFile imageFile) {
        UserAccount user = getUser(username);

        String fileName = generateUniqueFileName(imageFile.getOriginalFilename());
        String imagePath = POST_PATH_REPO + fileName;

        Path targetPath = Paths.get(imagePath);
        try {
            Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Post post = new Post();
        post.setUser(user);
        post.setImagePath(PATH_FOR_ANGULAR + fileName);
        post.setDateCreating(LocalDateTime.now());
        return repository.save(post);
    }

    @Override
    public boolean deletePost(String username, Long postId) {
        Post post = get(postId);

        if (username.equals(post.getUser().getUsername())) {
            try {
                Files.deleteIfExists(Paths.get(post.getImagePath()));
            } catch (IOException e) {
                throw new RuntimeException("Can't delete image by path: " + post.getImagePath());
            }
            repository.delete(post);
            return true;
        }
        throw  new RuntimeException("You can't delete someone else's post!");
    }

    @Override
    public List<Post> findPostsByUserId(Long userId) {
        UserAccount user = accountService.get(userId);
        return repository.findAllByUser(user);
    }

    @Override
    public Post addLike(String username, Long postId) {
        UserAccount user = getUser(username);
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

    private UserAccount getUser(String username) {
        return accountService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Can't find user by username: " + username));
    }
}
