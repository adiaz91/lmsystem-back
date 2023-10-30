package com.lmsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(name = "students_email_idx", columnNames = {"email"})
})
@Getter
@Setter
public class Student extends BaseEntity {

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;
    @NotBlank
    @Size(min=1,max=200)
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Size(min=1,max=200)
    @Column(name = "last_name")
    private String lastName;
    @NotBlank
    @Size(min=1,max=200)
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    @NotBlank
    @Size(min=1,max=200)
    @Column(name = "phone_number")
    private String phoneNumber;


}
