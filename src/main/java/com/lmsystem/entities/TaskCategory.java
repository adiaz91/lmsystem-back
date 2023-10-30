package com.lmsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_category")
@Getter
@Setter
public class TaskCategory extends BaseEntity{

    @NotBlank
    @Size(min=1,max=1000)
    @Column(name = "description")
    private String description;



}
