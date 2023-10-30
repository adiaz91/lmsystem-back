package com.lmsystem.data;

import com.lmsystem.entities.Course;

import java.time.Instant;
import java.time.LocalDate;

public class CourseFactory {

    public static Course create001(){

        var date = Instant.parse("2023-10-28T07:30:00Z");
        var localDate = LocalDate.of(2023,11,1);
        var course = new Course();
        course.setId(1);
        course.setCreatedAt(date);
        course.setName("FullStack development");
        course.setDescription("Development Masterclass");
        course.setStartDate(localDate);
        course.setCreatedAt(date);
        course.setModifiedAt(date);
        return course;
    }
}
