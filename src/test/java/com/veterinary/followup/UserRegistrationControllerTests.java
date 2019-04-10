package com.veterinary.followup;


import com.veterinary.followup.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTests {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    // ... other methods


    private String buildUrlEncodedFormEntity(String... params) {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<params.length; i+=2) {
            if( i > 0 ) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

    @Test
    public void testRegisterForm() throws Exception {
        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .content(buildUrlEncodedFormEntity(
                        "firstName", "myFirstName",
                        "lastName", "myLastName",
                        "email", "foobar@gmail.com",
                        "confirmEmail", "foobar@gmail.com",
                        "password", "secret",
                        "confirmPassword", "secret",
                        "address", "myaddress",
                        "phone", "905372954565",
                        "terms", "true"))).andDo(print()).andExpect(status().isFound());
    }
}
