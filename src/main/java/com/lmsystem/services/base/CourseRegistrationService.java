package com.lmsystem.services.base;

import com.lmsystem.entities.CourseRegistration;

import java.util.List;

public interface CourseRegistrationService {

    CourseRegistration findById(Integer id);
    CourseRegistration registerStudent(Integer courseId,String email);
    List<CourseRegistration> findByStudent(String email);


   
}
