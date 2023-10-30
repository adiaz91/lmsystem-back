package com.lmsystem.repositories;


import com.lmsystem.entities.CourseRegistration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CourseRegistrationRepository extends ListCrudRepository<CourseRegistration,Integer> {

    @Query("SELECT r FROM CourseRegistration r WHERE r.student.email = :email")
    List<CourseRegistration> findByStudentEmail(String email);

}
