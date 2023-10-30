package com.lmsystem.repositories;

import com.lmsystem.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TaskRepository extends ListCrudRepository<Task,Integer> {


    @Query("SELECT t FROM Task t WHERE t.courseRegistration.id = :courseRegistrationId")
    List<Task> findAllByCourseRegistration(Integer courseRegistrationId);

}
