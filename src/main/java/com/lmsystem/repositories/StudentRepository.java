package com.lmsystem.repositories;

import com.lmsystem.entities.Student;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface StudentRepository extends ListCrudRepository<Student,Integer> {

    Optional<Student> findByEmail(String email);
}
