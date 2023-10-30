package com.lmsystem.services.impl;

import com.lmsystem.entities.Course;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.CourseRepository;
import com.lmsystem.services.base.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Override
    @Transactional
    public Course create(Course course) {
        return courseRepository.save(course);
    }
    @Override
    @Transactional
    public Course update(Course course) {
        findById(course.getId());
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course findById(Integer id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course with id:"+id+" not found"));
    }

}
