package com.veterinary.followup.web;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import com.veterinary.followup.service.PatientService;
import com.veterinary.followup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String root(Model model, Authentication auth) {
        List<Patient> patients = patientService.getPatients();
        model.addAttribute("patients", patients);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}