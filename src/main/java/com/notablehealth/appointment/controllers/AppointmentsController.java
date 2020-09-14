package com.notablehealth.appointment.controllers;

import com.notablehealth.appointment.models.Appointment;
import com.notablehealth.appointment.repositories.AppointmentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentsController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @ApiOperation(value = "Get the list of all doctors appointments", notes = "This api returns all the list of doctors appointment so no paging is facilitated")
    @GetMapping
    public List<Appointment> list() {
        return this.appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Appointment get(@PathVariable("id") long id) {
        return this.appointmentRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody Appointment appointment) {

        Date date = appointment.getDate();
        if (!this.isValidTime(date)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Time");
        }

        long doctorId = appointment.getDoctor().getId();
        long count = this.appointmentRepository.countByDoctorIdAndDateEquals(doctorId, date);

        if (count >= 3) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment not available for a doctor");
        }

        this.appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        this.appointmentRepository.deleteById(id);
    }

    @ApiOperation(value = "Get the list of all appointments for a doctor at a specific date time")
    @GetMapping("/doctors/{doctorId}/date/{date}")
    public List<Appointment> list(@PathVariable("doctorId") long doctorId, @PathVariable("date") String date) {
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm", Locale.ENGLISH);
        Date appointmentDate;
        try {
            appointmentDate = format.parse(date);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Date format");
        }
        return this.appointmentRepository.findAllByDoctorIdAndDate(doctorId, appointmentDate);
    }

    private boolean isValidTime(Date date) {
        int minutes = date.getMinutes();
        return (minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45);
    }
}
