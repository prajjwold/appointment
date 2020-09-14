package com.notablehealth.appointment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.notablehealth.appointment.models.Appointment;
import com.notablehealth.appointment.models.AppointmentKind;
import com.notablehealth.appointment.repositories.AppointmentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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


    @ApiOperation(value = "Get the list of all doctors appointments with pagination", notes = "This api returns all the list of doctors appointment with pagination")
    @GetMapping("/pages")
    public ResponseEntity<Page<Appointment>> paginatedlist(@RequestParam(name = "page", defaultValue = "0") Integer pageNo,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
                                                           @RequestParam(name = "sort", defaultValue = "id") String sortBy,
                                                           @RequestParam(name = "dir", defaultValue = "ASC") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.valueOf(sortDir), sortBy));
        var list = this.appointmentRepository.findAll(paging);
        return new ResponseEntity<Page<Appointment>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Appointment>> search(@RequestParam(name = "doctor", defaultValue = "") String doctorName,
                                                    @RequestParam(name = "appointmentType") AppointmentKind kind,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer pageNo,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
                                                    @RequestParam(name = "sort", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "dir", defaultValue = "ASC") String sortDir) {
        Pageable paging = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.valueOf(sortDir), sortBy));
        Page<Appointment> list = this.appointmentRepository.search(doctorName, kind, paging);
        return new ResponseEntity<Page<Appointment>>(list, new HttpHeaders(), HttpStatus.OK);
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


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") long id, @RequestBody Map<String, Object> patch)
            throws JsonMappingException, JsonProcessingException {
        Optional<Appointment> appointment = this.appointmentRepository.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerForUpdating(appointment.orElseGet(null));
        Appointment updated = objectReader.readValue(objectMapper.writeValueAsString(patch));
        this.appointmentRepository.save(updated);
    }

    private boolean isValidTime(Date date) {
        int minutes = date.getMinutes();
        return (minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
