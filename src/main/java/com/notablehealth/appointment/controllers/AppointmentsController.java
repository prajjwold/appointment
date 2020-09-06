package com.notablehealth.appointment.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.notablehealth.appointment.models.Appointment;
import com.notablehealth.appointment.repositories.AppointmentRepository;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentsController {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@GetMapping
	public List<Appointment> list() {
		return appointmentRepository.findAll();
	}

	@GetMapping("/{id}")
	public Appointment get(@PathVariable("id") long id) {
		return appointmentRepository.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Appointment appointment) {

		Date date = appointment.getDate();
		if (!isValidTime(date)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Time");
		}

		long doctorId = appointment.getDoctor().getId();
		long count = appointmentRepository.countByDoctorIdAndDateEquals(doctorId, date);

		if (count >= 3) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment not available for a doctor");
		}

		appointmentRepository.save(appointment);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") long id) {
		appointmentRepository.deleteById(id);
	}

	@GetMapping("/doctors/{doctorId}/date/{date}")

	public List<Appointment> list(@PathVariable("doctorId") long doctorId, @PathVariable("date") String date) {
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm", Locale.ENGLISH);
		Date appointmentDate;
		try {
			appointmentDate = format.parse(date);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Date format");
		}
		return appointmentRepository.findAllByDoctorIdAndDate(doctorId, appointmentDate);
	}

	private boolean isValidTime(Date date) {
		int minutes = date.getMinutes();
		return (minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45);
	}
}
