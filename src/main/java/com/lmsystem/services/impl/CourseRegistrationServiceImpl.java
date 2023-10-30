package com.lmsystem.services.impl;

import com.lmsystem.entities.Course;
import com.lmsystem.entities.CourseRegistration;
import com.lmsystem.entities.Student;
import com.lmsystem.exceptions.InvalidEntityException;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.CourseRegistrationRepository;
import com.lmsystem.services.base.CourseRegistrationService;
import com.lmsystem.services.base.CourseService;
import com.lmsystem.services.base.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@AllArgsConstructor
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private static final int  MAX_COURSES_STUDENT=3;
    private CourseRegistrationRepository courseRegistrationRepository;
    private StudentService studentService;
    private CourseService courseService;

    @Override
    @Transactional
    public CourseRegistration findById(Integer id) {
        return courseRegistrationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("CourseRegistration with id:"+id+" not found"));
    }

    @Override
    @Transactional
    public CourseRegistration registerStudent(Integer courseId,String email) {
        if(findByStudent(email).size()<MAX_COURSES_STUDENT) {
            Student student = studentService.findByEmail(email);
            Course course = courseService.findById(courseId);
            CourseRegistration courseRegistration = new CourseRegistration();
            courseRegistration.setCourse(course);
            courseRegistration.setStudent(student);
            return courseRegistrationRepository.save(courseRegistration);
        }else{
            throw new InvalidEntityException("Maximum number of courses for student reached");
        }

    }
    @Override
    @Transactional
    public List<CourseRegistration> findByStudent(String email) {
        return courseRegistrationRepository.findByStudentEmail(email);
    }
}
