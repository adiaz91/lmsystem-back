package com.lmsystem.services.impl;

import com.lmsystem.entities.Student;
import com.lmsystem.exceptions.InvalidEntityException;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.StudentRepository;
import com.lmsystem.services.base.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;


@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private static final int MIN_AGE=16;

    @Override
    @Transactional
    public Student create(Student student) {
        validateAge(student.getDateOfBirth());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        validateAge(student.getDateOfBirth());
        findById(student.getId());
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findById(Integer id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with id:"+id+" not found"));
    }
    @Override
    @Transactional(readOnly = true)
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Student with email:"+email+" not found"));
    }

    private void validateAge(LocalDate dateOfBirth){
        boolean valid= dateOfBirth!=null
                && Period.between(dateOfBirth,LocalDate.now()).getYears()>=MIN_AGE;

        if(!valid){
            throw new InvalidEntityException("Students must be over "+MIN_AGE+" years of age");
        }
    }
}
