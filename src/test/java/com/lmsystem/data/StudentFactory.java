package com.lmsystem.data;

import com.lmsystem.entities.Student;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StudentFactory {

    public static Student create001(){

        var date = Instant.parse("2023-10-28T07:30:00Z");
        var dob = LocalDate.of(2000,1,1);

        var student = new Student();
        student.setId(1);
        student.setCreatedAt(date);
        student.setAddress("18th Street 47");
        student.setEmail("mail@example.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setDateOfBirth(dob);
        student.setPhoneNumber("36985742");
        student.setCreatedAt(date);
        student.setModifiedAt(date);
        return student;
    }
}
