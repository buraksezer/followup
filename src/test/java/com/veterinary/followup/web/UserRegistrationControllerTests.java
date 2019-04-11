package com.veterinary.followup.web;


import static org.assertj.core.api.Assertions.assertThat;
import com.veterinary.followup.model.User;
import com.veterinary.followup.service.UserService;
import com.veterinary.followup.web.dto.UserRegistrationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserRegistrationControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterForm() throws Exception {
        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("firstName", "myFirstName")
                .param("lastName", "myLastName")
                .param("email", "foobar@gmail.com")
                .param("confirmEmail", "foobar@gmail.com")
                .param("password", "secret")
                .param("confirmPassword", "secret")
                .param("address", "myaddress")
                .param("phone", "905372954565")
                .param("terms", "true")).andDo(print()).andExpect(status().isFound());
        User user = userService.findByEmail("foobar@gmail.com");
        assertThat(user.getEmail()).isEqualTo("foobar@gmail.com");
    }

    @Test
    public void testLoginForm() throws Exception {
        UserRegistrationDto reg = new UserRegistrationDto();
        reg.setEmail("loginTest@gmail.com");
        reg.setPassword("secret");
        userService.save(reg);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("username", "loginTest@gmail.com")
                .param("password", "secret")).andDo(print()).andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }
}
