package com.notablehealth.appointment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Doctor Id - auto generated")
    private Long id;

    @ApiModelProperty(value = "Doctor's First Name")
    @NotNull
    private String firstName;

    @ApiModelProperty(value = "Doctor's Last Name")
    private String lastName;

    //	@OneToMany()
    //	@JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @JsonIgnore
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ApiModelProperty(value = "List of all appointments scheduled with doctor")
    private List<Appointment> appointments;
}
