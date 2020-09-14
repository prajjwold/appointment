package com.notablehealth.appointment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.notablehealth.appointment.validations.AppointmentDateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Appointment Id - auto generated")
    private Long id;

    @ApiModelProperty(value = "Patient First Name")
    @NotNull(message = "Patient FirstName is required")
    @Size(min = 1, max = 50, message = "Patient FirstName should be of 1-50 character length")
//    @Size(min = 1, max = 50, message = "{appointment.patientFirstName.notNull}")
    private String patientFirstName;

    @ApiModelProperty(value = "Patient Last Name")
    @Size(max = 50, message = "Patient LastName should not exceeds 50 characters")
//    @Size(min = 0, max = 50, message = "{appointment.patientLastName.size}")
    private String patientLastName;

    @ApiModelProperty(value = "Scheduled Appointment DateTime in MM-dd-yyyy hh:mm format")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm")
    @NotNull(message = "Date cannot be empty")
//    @NotNull(message = "{appointment.date.notNull}")
    @Future(message = "Past Date for appointment could not be saved")
//    @Future(message = "{appointment.date.pastDate}")
    @AppointmentDateTime(message = "DateTime minute should be a multiple of 15 minutes")
    private Date date;

    @ApiModelProperty(value = "Type of Appointment : New or Followup")
    @NotNull(message = "Appointment Type cannot be empty")
//    @NotNull(message = "{appointment.appointmentKind.nonNull}")
    private AppointmentKind kind;

    @ApiModelProperty(value = "Doctor with whom the appointment is scheduled")
    @OneToOne()
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

//    @ApiModelProperty(value = "Field for Versioning")
//    @Version
//    private int version;
}
