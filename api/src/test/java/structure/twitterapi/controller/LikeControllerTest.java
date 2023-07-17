package structure.twitterapi.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import structure.twitterapi.model.Like;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.LikeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LikeControllerTest {
    @MockBean
    private LikeService likeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @WithMockUser(username = "litvik", roles = "USER")
    void shouldGetById() {
        Like like = new Like();
        like.setId(3L);
        like.setPost(new Post());
        like.setUser(new UserAccount());
        Mockito.when(likeService.get(3L)).thenReturn(like);

        RestAssuredMockMvc.given()
                .when()
                .get("/likes/3")
                .then()
                .body("id", Matchers.equalTo(3));
    }
}