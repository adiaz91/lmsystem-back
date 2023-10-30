package com.lmsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmsystem.data.CourseFactory;
import com.lmsystem.entities.Course;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.services.base.CourseService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    private static final String PATH = "/api/courses";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;


    private Course course;

    @BeforeEach
    public void setup(){
        course= CourseFactory.create001();
    }


    @Test
    void findById_successful() throws Exception {

        final int ID=1;
        when(courseService.findById(ID)).thenReturn(course);

        this.mockMvc.perform(get(PATH+ "/{id}",ID)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.name", Is.is("FullStack development")))
                .andExpect(jsonPath("$.description", Is.is("Development Masterclass")))
                .andExpect(jsonPath("$.startDate", Is.is("2023-11-01")));
        verify(courseService).findById(ID);

    }


    @Test
    void create_successful() throws Exception {

        final int ID=10;
        course.setId(null);
        when(courseService.create(any(Course.class))).then((an)-> {
            Course course =  an.getArgument(0);
            course.setId(ID);
            return course;
        } );

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is(ID)))
                .andExpect(jsonPath("$.name", Is.is("FullStack development")))
                .andExpect(jsonPath("$.description", Is.is("Development Masterclass")))
                .andExpect(jsonPath("$.startDate", Is.is("2023-11-01")));
        verify(courseService).create(any(Course.class));

    }

    @Test
    void test_required_fields() throws Exception {

        course = new Course();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course))).andExpect(status().isBadRequest());


    }


    @Test
    void findByEmail_notFound() throws Exception {

        final int ID=2;
        when(courseService.findById(ID)).thenThrow(new ResourceNotFoundException("Not found"));
        this.mockMvc.perform(get(PATH+ "/{id}",ID)).andExpect(status().isNotFound());
        verify(courseService).findById(ID);

    }










}