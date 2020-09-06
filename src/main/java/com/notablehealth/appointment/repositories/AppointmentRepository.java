package com.notablehealth.appointment.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notablehealth.appointment.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findAllByDoctorIdAndDate(Long doctorId, Date date);

	long countByDoctorIdAndDateEquals(Long doctorId, Date date);
}
