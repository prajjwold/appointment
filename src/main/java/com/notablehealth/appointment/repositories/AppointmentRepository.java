package com.notablehealth.appointment.repositories;

import com.notablehealth.appointment.models.Appointment;
import com.notablehealth.appointment.models.AppointmentKind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Override
    List<Appointment> findAll(Sort sort);

    @Override
    Page<Appointment> findAll(Pageable pageable);

    List<Appointment> findAllByDoctorIdAndDate(Long doctorId, Date date);

    Page<Appointment> findAllByPatientFirstName(String patientFirstName, Pageable pageable);

    Page<Appointment> findAllByDoctorId(int doctorId, Pageable pageable);

    Page<Appointment> findAllByDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("Select a from Appointment a where a.doctor.firstName like %:name AND a.kind = :kind")
    Page<Appointment> search(@Param("name") String doctorName, @Param("kind") AppointmentKind kind, Pageable pageable);

    Long countByDoctorIdAndDateEquals(Long doctorId, Date date);
}
