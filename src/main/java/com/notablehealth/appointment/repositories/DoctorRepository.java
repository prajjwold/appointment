package com.notablehealth.appointment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notablehealth.appointment.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
