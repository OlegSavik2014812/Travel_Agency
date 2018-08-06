package com.epam.web.controller;

import com.epam.core.config.JpaConfig;
import com.epam.core.entity.User;
import com.epam.web.config.application.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, JpaConfig.class})
@ActiveProfiles(profiles = {"test", "jpa"})
@WebAppConfiguration
public class UserControllerTest {
    private static final String CONTENT_TYPE_TEXT_HTML_UTF8 = "text/html;charset=UTF-8";
    private static final String USER_LIST_PAGE = "user_list";
    private static final String SIG_IN_PAGE = "auth/sign_in";
    private static final String SIG_UP_PAGE = "auth/sign_up";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private MultiValueMap<String, String> userAuthInfo;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        userAuthInfo = new LinkedMultiValueMap<>();
        userAuthInfo.put("login", Collections.singletonList("Username"));
        userAuthInfo.put("password", Collections.singletonList("Password"));
    }


    @Test
    public void showSignUpPage() throws Exception {
        this.mockMvc.perform(get("/go_to_sign_up")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_TEXT_HTML_UTF8))
                .andExpect(view().name(SIG_UP_PAGE));
    }

    @Test
    public void signUp() throws Exception {
        userAuthInfo.put("confirmPassword", Collections.singletonList("Password"));
        this.mockMvc.perform(post("/sign_up").params(userAuthInfo))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void showSignIn() throws Exception {
        this.mockMvc.perform(get("/go_to_sign_in")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_TEXT_HTML_UTF8))
                .andExpect(view().name(SIG_IN_PAGE));
    }

    @Test
    public void signIn() throws Exception {
        this.mockMvc.perform(post("/sign_in").params(userAuthInfo))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void signOut() throws Exception {
        this.mockMvc.perform(get("/sign_out"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getUserList() throws Exception {
        this.mockMvc.perform(get("/get_users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_TEXT_HTML_UTF8))
                .andExpect(view().name(USER_LIST_PAGE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("userList"));
    }
}