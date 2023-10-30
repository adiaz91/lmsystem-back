package com.lmsystem.repositories;

import com.lmsystem.entities.TaskCategory;
import org.springframework.data.repository.ListCrudRepository;

public interface TaskCategoryRepository extends ListCrudRepository<TaskCategory,Integer> {
}
