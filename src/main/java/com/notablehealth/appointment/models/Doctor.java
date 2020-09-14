package com.notablehealth.appointment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Doctor Id - auto generated")
    private Long id;

    @ApiModelProperty(value = "Doctor's First Name")
    @NotNull(message = "FirstName is required")
    @Size(min = 1, max = 50, message = "FirstName should be of 1-50 character length")
    private String firstName;

    @ApiModelProperty(value = "Doctor's Last Name")
    @Size(max = 50, message = "LastName should not exceeds 50 characters")
    private String lastName;

    //	@OneToMany()
    //	@JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @JsonIgnore
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ApiModelProperty(value = "List of all appointments scheduled with doctor")
    private List<Appointment> appointments;
}
