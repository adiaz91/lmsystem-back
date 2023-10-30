package com.lmsystem.controllers;


import com.lmsystem.entities.Student;
import com.lmsystem.services.base.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(path = "/{email}")
    public Student get(@PathVariable String email){
        return studentService.findByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student){
        return studentService.create(student);
    }



}
