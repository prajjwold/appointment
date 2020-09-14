package com.notablehealth.appointment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Appointment Id - auto generated")
    private Long id;

    @ApiModelProperty(value = "Patient First Name")
    @NotNull
    private String patientFirstName;

    @ApiModelProperty(value = "Patient Last Name")
    private String patientLastName;

    @ApiModelProperty(value = "Scheduled Appointment DateTime in MM-dd-yyyy hh:mm format")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm")
    @NotNull
    private Date date;

    @ApiModelProperty(value = "Type of Appointment : New or Followup")
    @NotNull
    private AppointmentKind kind;

    @ApiModelProperty(value = "Doctor with whom the appointment is scheduled")
    @OneToOne()
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

//    @ApiModelProperty(value = "Field for Versioning")
//    @Version
//    private int version;
}
