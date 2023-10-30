package com.lmsystem.services.impl;

import com.lmsystem.entities.Task;
import com.lmsystem.entities.TaskCategory;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.TaskCategoryRepository;
import com.lmsystem.repositories.TaskRepository;
import com.lmsystem.services.base.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskCategoryRepository taskCategoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskCategory> findCategories() {
        return taskCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllByCourseRegistration(Integer courseRegistrationId) {
        return taskRepository.findAllByCourseRegistration(courseRegistrationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id:"+id+" not found"));
    }

    @Override
    @Transactional
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        findById(task.getId());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(Integer taskId) {
        var task =findById(taskId);
        taskRepository.delete(task);
    }

}
