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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import structure.twitterapi.model.Post;
import structure.twitterapi.model.UserAccount;
import structure.twitterapi.service.UserAccountService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserAccountService userService;
    @Autowired
    private MockMvc mockMvc;
    private UserAccount user;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        user = new UserAccount();
        user.setId(1L);
        user.setUsername("litvik");
        user.setRole(new HashSet<>());
    }

    @Test
    public void shouldGetById() throws Exception {
        Mockito.when(userService.get(user.getId())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user.getId())
                .with(authentication(getTestAuthentication())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void shouldGetByUsername() throws Exception {
        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/username")
                        .with(authentication(getTestAuthentication()))
                        .param("username", user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()));
    }

    private Authentication getTestAuthentication() {
        return new UsernamePasswordAuthenticationToken("litvik", "Password", Collections.emptyList());
    }
}