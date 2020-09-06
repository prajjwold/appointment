package com.notablehealth.appointment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notablehealth.appointment.models.Doctor;
import com.notablehealth.appointment.repositories.DoctorRepository;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorsController {
	@Autowired
	private DoctorRepository doctorRepository;

	@GetMapping
	public List<Doctor> list() {
		return doctorRepository.findAll();
	}

	@GetMapping("/{id}")
	public Doctor get(@PathVariable("id") long id) {
		return doctorRepository.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Doctor doctor) {
		doctorRepository.save(doctor);
	}
}
