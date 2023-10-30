package com.lmsystem.controllers;


import com.lmsystem.entities.Task;
import com.lmsystem.entities.TaskCategory;
import com.lmsystem.services.base.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/categories")
    public List<TaskCategory> getCategories(){
        return taskService.findCategories();
    }

    @GetMapping(path = "/{id}")
    public Task get(@PathVariable Integer id){
        return taskService.findById(id);
    }

    @GetMapping(path = "/course/{courseRegId}")
    public List<Task> getAllByCourseReg(@PathVariable Integer courseRegId){
        return taskService.findAllByCourseRegistration(courseRegId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody Task task){
        return taskService.create(task);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Task update(@Valid @RequestBody Task task){
        return taskService.update(task);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
        taskService.delete(id);
    }



}
