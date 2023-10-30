package com.lmsystem.controllers;


import com.lmsystem.entities.Course;
import com.lmsystem.services.base.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getAll(){
        return courseService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course get(@PathVariable Integer id){
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course create(@Valid @RequestBody Course course){
        return courseService.create(course);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Course update(@Valid @RequestBody Course course){
        return courseService.update(course);
    }



}
