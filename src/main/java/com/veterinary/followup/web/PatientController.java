package com.veterinary.followup.web;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import com.veterinary.followup.service.PatientService;
import com.veterinary.followup.service.UserService;
import com.veterinary.followup.web.dto.PatientRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @ModelAttribute("patient")
    public PatientRegistrationDto patientRegistrationDto() { return new PatientRegistrationDto(); }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "patientForm";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute("patient") @Valid PatientRegistrationDto patientDto,
                                  BindingResult result, Authentication auth) {
        User owner = userService.findByEmail(auth.getName());
        Patient existing = patientService.findByName_AndOwner(patientDto.getName(), owner);
        if (existing != null) {
            result.rejectValue("name", null, "There is already a patient with that name");
        }

        if (result.hasErrors()) {
            return "patientForm";
        }

        patientService.save(patientDto, owner);
        return "redirect:/?success";
    }

    private Patient getPatient(Long id, Authentication auth) throws ResponseStatusException {
        Optional <Patient> patient = patientService.findById(id);
        if (patient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found");
        }

        UserDetails userDetails = userService.loadUserByUsername(auth.getName());
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return patient.get();
        }

        User owner = userService.findByEmail(auth.getName());
        if (!patient.get().getOwner().equals(owner)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you don't authorized to do that");
        }
        return patient.get();
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Long id,  Model model, Authentication auth) throws ResponseStatusException{
        model.addAttribute("action", "/patient/"+ id +"/update");
        Patient patient = this.getPatient(id, auth);
        model.addAttribute("patient", patient);
        return "patientForm";
    }

    @PostMapping("/{id}/update")
    public String updatePatient(@PathVariable Long id, @ModelAttribute("patient") @Valid PatientRegistrationDto patientDto,
                                 BindingResult result, Model model, Authentication auth) throws ResponseStatusException{
        Patient patient = this.getPatient(id, auth);
        if (result.hasErrors()) {
            model.addAttribute("action", "/patient/"+ id +"/update");
            return "patientForm";
        }
        patientService.update(patientDto, patient);
        return "redirect:/?success";
    }

    @GetMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id, Authentication auth) throws ResponseStatusException{
        this.getPatient(id, auth);
        patientService.deleteById(id);
        return "redirect:/?success";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name == null) {
            return "searchByPatientName";
        }
        List<Patient> patients = patientService.findByNameContainingIgnoreCase(name);
        if (patients.isEmpty()) {
            return "redirect:/patient/search?noResult";
        }
        model.addAttribute("patients", patients);
        return "searchByPatientName";
    }
}
