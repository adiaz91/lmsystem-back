package com.lmsystem.repositories;

import com.lmsystem.entities.Course;
import org.springframework.data.repository.ListCrudRepository;

public interface CourseRepository extends ListCrudRepository<Course,Integer> {
}
