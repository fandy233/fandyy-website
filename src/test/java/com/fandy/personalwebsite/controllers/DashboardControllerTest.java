package com.fandy.personalwebsite.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fandy.personalwebsite.models.CatProfile;
import com.fandy.personalwebsite.services.CatProfileService;
import com.fandy.personalwebsite.services.CustomUserDetailsService;
import com.fandy.personalwebsite.services.LoginService;
import com.fandy.personalwebsite.services.UserDetailsImpl;
import com.fandy.personalwebsite.services.jwt.JwtAuthTokenFilter;
import com.fandy.personalwebsite.services.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatProfileService catService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtils jwtUtils;
    /*
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
*/
    @Test
    @WithUserDetails(value = "testuser", userDetailsServiceBeanName = "customUserDetailsService")
    public void shouldReturnEmptyListWhenNoCatsFound() throws Exception {
        UserDetailsImpl userDetails = new UserDetailsImpl(
                "testUserId", "testuser", "fandy666", "ROLE_USER");

        when(customUserDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(catService.findCatsByUserId("testUserId")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/me/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    @WithMockUser(username = "testuser", roles = "USER", password = "fandy666")
    public void shouldReturnCatsInfoForUser() throws Exception {

        CatProfile cat1 = new CatProfile();
        cat1.setId("1");
        cat1.setName("Whiskers");
        cat1.setBreed("Siamese");
        cat1.setGender("Male");
        cat1.setImageUrl("http://example.com/photo1.jpg");
        cat1.setDescription("Seyaaa!");

        CatProfile cat2 = new CatProfile();
        cat2.setId("2");
        cat2.setName("Mittens");
        cat2.setBreed("Persian");
        cat2.setGender("Female");
        cat2.setImageUrl("http://example.com/photo2.jpg");
        cat2.setDescription("Azureee");

        List<CatProfile> cats = Arrays.asList(cat1, cat2);

        when(catService.findCatsByUserId("testUserId")).thenReturn(cats);

        mockMvc.perform(get("/users/me/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Whiskers"))
                .andExpect(jsonPath("$[0].breed").value("Siamese"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[1].name").value("Mittens"))
                .andExpect(jsonPath("$[1].breed").value("Persian"))
                .andExpect(jsonPath("$[1].gender").value("Female"));
    }
}

