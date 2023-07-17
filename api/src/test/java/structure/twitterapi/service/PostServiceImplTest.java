package structure.twitterapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import structure.twitterapi.lib.FileUtil;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.repository.PostRepository;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository repository;
    @Mock
    private UserAccountServiceImpl accountService;
    @Mock
    private FileUtil fileUtil;
    @Mock
    private LikeServiceImpl likeService;
    private UserAccount user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new UserAccount();
        user.setId(1L);
        user.setUsername("litvik");

        post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setLikes(new ArrayList<>());
    }

    @Test
    public void shouldAddPost() throws Exception {
        String description = "Test post";
        String fileName = "test.jpg";
        byte[] imageContent = new byte[]{1, 2, 3};

        when(accountService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(fileUtil.generateUniqueFileName(any())).thenReturn(fileName);
        when(repository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(fileUtil).saveImage(any(), any());

        MockMultipartFile imageFile = new MockMultipartFile("imageFile", fileName, "image/jpeg",
                new ByteArrayInputStream(imageContent));

        Post actual = postService.addPost(user.getUsername(), description, imageFile);

        assertEquals(user, actual.getUser());
        assertEquals(description, actual.getDescription());
        assertEquals("../assets/images/" + fileName, actual.getImagePath());
        assertEquals(LocalDateTime.now().getDayOfYear(), actual.getDateCreating().getDayOfYear());
    }

    @Test
    void shouldDeletePost() {
        when(repository.findById(post.getId())).thenReturn(Optional.of(post));
        doNothing().when(repository).delete(any());
        doNothing().when(fileUtil).deleteImage(any());

        boolean actual = postService.deletePost(user.getUsername(), post.getId());

        assertTrue(actual);
    }

    @Test
    void shouldAddLikeToPost() {
        Like newLike = new Like();
        newLike.setUser(user);
        newLike.setPost(post);
        when(accountService.findByUsername("litvik")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(post));
        when(likeService.findByUserAndPost(user, post)).thenReturn(Optional.empty());
        when(repository.save(post)).thenReturn(post);

        Post actual = postService.addLike("litvik", 1L);

        assertEquals(1, actual.getLikes().size());
        assertEquals(1L, actual.getId());
        assertEquals("litvik", actual.getUser().getUsername());
    }
}