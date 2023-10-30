package com.lmsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task extends BaseEntity{

    @NotBlank
    @Size(min=1,max=1000)
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "task_date")
    @Temporal(TemporalType.DATE)
    private LocalDate taskDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private TaskCategory category;
    @NotNull
    @Column(name = "time_spent")
    private int timeSpent;
    @ManyToOne
    @JoinColumn(name = "course_registration_id")
    private CourseRegistration courseRegistration;






}
