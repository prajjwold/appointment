package com.notablehealth.appointment.controllers;

import com.notablehealth.appointment.models.Doctor;
import com.notablehealth.appointment.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

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
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid Doctor doctor) {
        this.doctorRepository.save(doctor);
    }
}
