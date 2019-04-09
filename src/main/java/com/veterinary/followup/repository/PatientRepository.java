package com.veterinary.followup.repository;

import com.veterinary.followup.model.Patient;
import com.veterinary.followup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository <Patient, Long> {
    Patient findByName_AndOwner(String name, User owner);

    List<Patient> findByNameContainingIgnoreCase(String name);
}
