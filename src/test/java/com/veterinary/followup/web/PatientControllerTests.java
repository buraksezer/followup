package com.veterinary.followup.web;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.service.PatientService;
import com.veterinary.followup.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PatientControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PatientService patientService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "foobar@gmail.com", password = "secret", roles = "USER")
    public void testCreatePatient() throws Exception {
        mvc.perform(post("/patient/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("name", "zeze")
                .param("description", "fulya'nin kedisi")
                .param("age", "1")
                .param("breed", "tekir")
                .param("species", "kedi")).andDo(print()).andExpect(status().isFound())
                .andExpect(redirectedUrl("/?success"));
        List<Patient> patients = patientService.getPatients();
        assertThat(patients.get(0).getName()).isEqualTo("zeze");
    }
}
