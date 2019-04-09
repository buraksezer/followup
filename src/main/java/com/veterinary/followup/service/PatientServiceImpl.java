package com.veterinary.followup.service;

import java.util.List;
import java.util.Optional;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import com.veterinary.followup.web.dto.PatientRegistrationDto;
import com.veterinary.followup.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findByName_AndOwner(String name, User owner) {
        return patientRepository.findByName_AndOwner(name, owner);
    }

    public Optional <Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public List <Patient> findByNameContainingIgnoreCase(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional
    public List <Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient save(PatientRegistrationDto registration, User owner) {
        Patient patient = new Patient();
        patient.setName(registration.getName());
        patient.setDescription(registration.getDescription());
        patient.setAge(registration.getAge());
        patient.setBreed(registration.getBreed());
        patient.setSpecies(registration.getSpecies());
        patient.setOwner(owner);
        return patientRepository.save(patient);
    }

    public void update(PatientRegistrationDto registration, Patient patient) {
        patient.setName(registration.getName());
        patient.setDescription(registration.getDescription());
        patient.setAge(registration.getAge());
        patient.setBreed(registration.getBreed());
        patient.setSpecies(registration.getSpecies());
        patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}