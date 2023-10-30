package com.lmsystem.services.impl;

import com.lmsystem.data.StudentFactory;
import com.lmsystem.entities.Student;
import com.lmsystem.exceptions.InvalidEntityException;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl studentService;

    Student student;

    @BeforeEach
    void setUp() {
        student= StudentFactory.create001();
    }

    @Test
    void create() {
        student.setId(null);

        final var  date = Instant.now();
        when(studentRepository.save(any(Student.class))).then((a)->{
            Student e= a.getArgument(0);
            e.setId(10);
            e.setCreatedAt(date);
            e.setModifiedAt(date);
            return e;
        });

        Student result=studentService.create(student);
        assertEquals(10,result.getId());
        assertEquals("18th Street 47",result.getAddress());
        assertEquals("mail@example.com", result.getEmail());
        assertEquals("John",  result.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("36985742",  student.getPhoneNumber());
        assertEquals(LocalDate.of(2000,1,1), student.getDateOfBirth());
        assertEquals(date,result.getCreatedAt());
        assertEquals(date,result.getModifiedAt());

        verify(studentRepository).save(any(Student.class));
    }


    @Test
    void  findById_not_found() {
        final int ID=100;
        when(studentRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->studentService.findById(ID));
        verify(studentRepository).findById(ID);
    }

    @Test
    void test_age_restriction() {

        var student=StudentFactory.create001();
        student.setDateOfBirth(LocalDate.now());
        assertThrows(InvalidEntityException.class,()->studentService.create(student));
        assertThrows(InvalidEntityException.class,()->studentService.update(student));

    }


    @Test
    void update() {

        final var  date = Instant.now();
        when(studentRepository.save(any(Student.class))).then((a)->{
            Student e = a.getArgument(0);
            e.setModifiedAt(date);
            return e;
        });

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Student result=studentService.update(student);
        assertEquals(1,result.getId());
        assertEquals("18th Street 47",result.getAddress());
        assertEquals("mail@example.com", result.getEmail());
        assertEquals("John",  result.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("36985742",  student.getPhoneNumber());
        assertEquals(LocalDate.of(2000,1,1), student.getDateOfBirth());
        assertEquals(Instant.parse("2023-10-28T07:30:00Z"),result.getCreatedAt());
        assertEquals(date,result.getModifiedAt());
        verify(studentRepository).findById(1);
        verify(studentRepository).save(any(Student.class));

    }



    @Test
    void findById() {
        final int ID=1;
        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));
        var result=studentService.findById(ID);
        assertEquals(1,result.getId());
        assertEquals("18th Street 47",result.getAddress());
        assertEquals("mail@example.com", result.getEmail());
        assertEquals("John",  result.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("36985742",  student.getPhoneNumber());
        assertEquals(LocalDate.of(2000,1,1), student.getDateOfBirth());
        assertEquals(Instant.parse("2023-10-28T07:30:00Z"),result.getCreatedAt());
        verify(studentRepository).findById(ID);


    }





}