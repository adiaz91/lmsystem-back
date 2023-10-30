package com.lmsystem.services.base;

import com.lmsystem.entities.Task;
import com.lmsystem.entities.TaskCategory;

import java.util.List;

public interface TaskService {
    List<TaskCategory> findCategories();
    List<Task> findAllByCourseRegistration(Integer courseRegistrationId);

    Task findById(Integer id);

    Task create(Task task);
    Task update(Task task);
    void delete(Integer taskId);
   
}
