package com.lmsystem.services.base;

import com.lmsystem.entities.Course;

import java.util.List;

public interface CourseService {


    List<Course> findAll();
    Course findById(Integer id);
    Course create(Course course);
    Course update(Course course);
   
}
