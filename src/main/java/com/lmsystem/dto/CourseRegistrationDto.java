package com.lmsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRegistrationDto {
    @NotNull
    private Integer courseId;
    @NotNull
    private String email;
}
