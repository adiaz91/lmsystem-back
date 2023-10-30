package com.lmsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "courses", uniqueConstraints = {
        @UniqueConstraint(name = "courses_name_idx", columnNames = {"name"})
})
@Getter
@Setter
public class Course extends BaseEntity{

    @NotBlank
    @Size(min=1,max=200)
    @Column(name = "name")
    private String name;
    @NotBlank
    @Size(min=1,max=2000)
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;


}
