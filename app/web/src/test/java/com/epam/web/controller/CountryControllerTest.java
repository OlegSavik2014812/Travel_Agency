package com.epam.web.controller;

import com.epam.core.config.JpaConfig;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, JpaConfig.class})
@ActiveProfiles(profiles = {"test", "jpa"})
@WebAppConfiguration
@WithMockUser(authorities = "ADMIN")
public class CountryControllerTest {
    private static final String CONTENT_TYPE_TEXT_HTML_UTF8 = "text/html;charset=UTF-8";
    private static final String COUNTRY_LIST_PAGE = "country_list";
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getCountryList() throws Exception {
        this.mockMvc.perform(get("/get_countries")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(content().contentType(CONTENT_TYPE_TEXT_HTML_UTF8))
                .andExpect(view().name(COUNTRY_LIST_PAGE))
                .andExpect(model().attributeExists("countryList"));
    }
}