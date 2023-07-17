package structure.twitterapi.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.PostService;
import structure.twitterapi.service.UserAccountService;
import java.util.ArrayList;
import java.util.Collections;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @MockBean
    private PostService postService;
    @MockBean
    private UserAccountService userService;
    @Autowired
    private MockMvc mockMvc;
    private Post post;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        UserAccount user = new UserAccount();
        user.setUsername("litvik");
        post = new Post();
        post.setId(4L);
        post.setUser(user);
        post.setDescription("description");
        post.setImagePath("path_image");
        post.setLikes(new ArrayList<>());
    }

    @Test
    void shouldCreatePost() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "Test image".getBytes());
        Mockito.when(postService.addPost("litvik", "description", imageFile)).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/posts/add")
                        .file(imageFile)
                        .param("desc", "description")
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Post uploaded successfully\"}"));

    }

    @Test
    void shouldDeletePost() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/remove/{id}", 1L)
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Post deleted successfully\"}"));
    }

    @Test
    public void testGetAllByUser() throws Exception {
        String username = "litvik";
        Mockito.when(userService.getIdByUsername(username)).thenReturn(3L);
        Mockito.when(postService.findPostsByUserId(3L)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/username")
                        .param("username", username)
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetAllByCurrentUser() throws Exception {
        Mockito.when(userService.getIdByUsername("litvik")).thenReturn(2L);
        Mockito.when(postService.findPostsByUserId(2L)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/my")
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testAddOrRemoveLike() throws Exception {
        Long postId = 4L;
        Mockito.when(postService.addLike("litvik", postId)).thenReturn(post);
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/like/{id}", postId)
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    private Authentication getTestAuthentication() {
        return new UsernamePasswordAuthenticationToken("litvik", "Password", Collections.emptyList());
    }

}