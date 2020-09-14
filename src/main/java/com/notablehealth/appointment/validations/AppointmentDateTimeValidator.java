package com.notablehealth.appointment.validations;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

@Component
public class AppointmentDateTimeValidator implements ConstraintValidator<AppointmentDateTime, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        // return dateOfBirth != null && LocalDate.now().minusYears(ADULT_AGE).isAfter(dateOfBirth);
        int minutes = date.getMinutes();
        return (minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45);
    }
}
