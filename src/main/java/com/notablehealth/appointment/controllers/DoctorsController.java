package com.notablehealth.appointment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.notablehealth.appointment.models.Doctor;
import com.notablehealth.appointment.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorsController {
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public List<Doctor> list() {
        return this.doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Doctor get(@PathVariable("id") long id) {
        return this.doctorRepository.getOne(id);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Doctor doctor) {
        this.doctorRepository.save(doctor);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable("id") long id, @RequestBody Doctor dto) {
        Optional<Doctor> doctor = this.doctorRepository.findById(id);
        this.doctorRepository.save(doctor.orElseGet(null));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patch(@PathVariable("id") long id, @RequestBody Map<String, Object> patch)
            throws JsonMappingException, JsonProcessingException {
        Optional<Doctor> doctor = this.doctorRepository.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerForUpdating(doctor.orElseGet(null));
        Doctor updated = objectReader.readValue(objectMapper.writeValueAsString(patch));
        this.doctorRepository.save(updated);
    }
}
