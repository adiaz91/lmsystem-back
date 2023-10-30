package com.lmsystem.services.impl;

import com.lmsystem.data.CourseFactory;
import com.lmsystem.entities.Course;
import com.lmsystem.exceptions.InvalidEntityException;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImpl courseService;

    Course course;

    @BeforeEach
    void setUp() {
        course= CourseFactory.create001();
    }

    @Test
    void create() {
        course.setId(null);

        final var  date = Instant.now();
        when(courseRepository.save(any(Course.class))).then((a)->{
            Course e= a.getArgument(0);
            e.setId(10);
            e.setCreatedAt(date);
            e.setModifiedAt(date);
            return e;
        });

        Course result=courseService.create(course);
        assertEquals(10,result.getId());
        assertEquals("FullStack development",result.getName());
        assertEquals("Development Masterclass", result.getDescription());
        assertEquals(LocalDate.of(2023,11,1),result.getStartDate());
        assertEquals(date,result.getCreatedAt());
        assertEquals(date,result.getModifiedAt());

        verify(courseRepository).save(any(Course.class));
    }


    @Test
    void  findById_not_found() {
        final int ID=100;
        when(courseRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->courseService.findById(ID));
        verify(courseRepository).findById(ID);
    }


    @Test
    void update() {

        final var  date = Instant.now();
        when(courseRepository.save(any(Course.class))).then((a)->{
            Course e = a.getArgument(0);
            e.setModifiedAt(date);
            return e;
        });

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course result=courseService.update(course);
        assertEquals(1,result.getId());
        assertEquals("FullStack development",result.getName());
        assertEquals("Development Masterclass", result.getDescription());
        assertEquals(LocalDate.of(2023,11,1),result.getStartDate());
        assertEquals(Instant.parse("2023-10-28T07:30:00Z"),result.getCreatedAt());
        assertEquals(date,result.getModifiedAt());
        verify(courseRepository).findById(1);
        verify(courseRepository).save(any(Course.class));

    }



    @Test
    void findById() {
        final int ID=1;
        when(courseRepository.findById(ID)).thenReturn(Optional.of(course));
        var result=courseService.findById(ID);
        assertEquals(1,result.getId());
        assertEquals("FullStack development",result.getName());
        assertEquals("Development Masterclass", result.getDescription());
        assertEquals(LocalDate.of(2023,11,1),result.getStartDate());
        assertEquals(Instant.parse("2023-10-28T07:30:00Z"),result.getCreatedAt());
        verify(courseRepository).findById(ID);


    }





}