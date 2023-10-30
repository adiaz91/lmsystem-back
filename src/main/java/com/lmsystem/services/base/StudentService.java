package com.lmsystem.services.base;

import com.lmsystem.entities.Student;

public interface StudentService {

    Student create(Student student);
    Student update(Student student);
    Student findByEmail(String email);
}
