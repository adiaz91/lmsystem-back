package com.lmsystem.controllers;


import com.lmsystem.dto.CourseRegistrationDto;
import com.lmsystem.entities.CourseRegistration;
import com.lmsystem.services.base.CourseRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    @GetMapping("/{email}")
    public List<CourseRegistration> getAllByEmail(@PathVariable String email){
        return courseRegistrationService.findByStudent(email);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseRegistration create(@Valid @RequestBody CourseRegistrationDto dto){
        return courseRegistrationService.registerStudent(dto.getCourseId(), dto.getEmail());
    }





}
