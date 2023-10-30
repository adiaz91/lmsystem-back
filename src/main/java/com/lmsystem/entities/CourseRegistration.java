package com.lmsystem.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_registration", uniqueConstraints = {
        @UniqueConstraint(name = "course_registration_idx", columnNames = {"student_id","course_id"})
})
@Getter
@Setter
public class CourseRegistration extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;
}
