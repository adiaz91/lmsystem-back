package com.lmsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmsystem.entities.Student;
import com.lmsystem.exceptions.ResourceNotFoundException;
import com.lmsystem.services.base.StudentService;
import com.lmsystem.data.StudentFactory;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@WebMvcTest(StudentController.class)
class StudentControllerTest {

    private static final String PATH = "/api/students";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;


    private Student student;

    @BeforeEach
    public void setup(){
        student= StudentFactory.create001();
    }


    @Test
    void findByEmail_successful() throws Exception {

        final String EMAIL="mail@example.com";
        when(studentService.findByEmail(EMAIL)).thenReturn(student);

        this.mockMvc.perform(get(PATH+ "/{email}",EMAIL)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.address", Is.is("18th Street 47")))
                .andExpect(jsonPath("$.email", Is.is(EMAIL)))
                .andExpect(jsonPath("$.firstName", Is.is("John")))
                .andExpect(jsonPath("$.lastName", Is.is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", Is.is("36985742"))
                );
        verify(studentService).findByEmail(EMAIL);

    }


    @Test
    void create_successful() throws Exception {

        final int ID=1;
        student.setId(null);
        when(studentService.create(any(Student.class))).then((an)-> {
            Student student =  an.getArgument(0);
            student.setId(ID);
            return student;
        } );

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.address", Is.is("18th Street 47")))
                .andExpect(jsonPath("$.email", Is.is("mail@example.com")))
                .andExpect(jsonPath("$.firstName", Is.is("John")))
                .andExpect(jsonPath("$.lastName", Is.is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", Is.is("36985742"))
                );
        verify(studentService).create(any(Student.class));

    }

    @Test
    void test_required_fields() throws Exception {

        student = new Student();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))).andExpect(status().isBadRequest());


    }


    @Test
    void findByEmail_notFound() throws Exception {

        final String EMAIL="notfound@mail.com";
        when(studentService.findByEmail(EMAIL)).thenThrow(new ResourceNotFoundException("Not found"));
        this.mockMvc.perform(get(PATH+ "/{email}",EMAIL)).andExpect(status().isNotFound());
        verify(studentService).findByEmail(EMAIL);

    }










}