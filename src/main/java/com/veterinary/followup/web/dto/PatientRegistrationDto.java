package com.veterinary.followup.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;

public class PatientRegistrationDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @DecimalMax("40.0")
    private Integer age;

    @NotEmpty
    private String breed;

    @NotEmpty
    private String species;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}