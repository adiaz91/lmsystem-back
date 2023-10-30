package com.lmsystem.integration;

import com.lmsystem.data.CourseFactory;
import com.lmsystem.entities.Course;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseControllerIntTest {

    private static final String PATH = "/api/courses";
    @Autowired
    private WebTestClient client;
    private Course course;

    @BeforeEach
    public void setup() {
        course = CourseFactory.create001();
    }


    @Order(1)
    @Test
    void findById_successful() {
        final int ID = 10;

        client.get().uri(PATH + "/{id}", ID).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo("Java Development Masterclass")
                .jsonPath("$.description").isEqualTo("A comprehensive course that equips you with great skills")
                .jsonPath("$.startDate").isEqualTo("2023-11-02");
    }

    @Order(2)
    @Test
    void findById_notFound() {
        final int ID = 100;
        client.get().uri(PATH + "/{id}", ID).exchange()
                .expectStatus().isNotFound();

    }


    @Order(3)
    @Test
    void create_successful() {
        course.setId(null);
        client.post().uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(course)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("FullStack development")
                .jsonPath("$.description").isEqualTo("Development Masterclass")
                .jsonPath("$.startDate").isEqualTo("2023-11-01");
    }

    @Order(4)
    @Test
    void validate_required_fields() {

        course = new Course();

        client.post().uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(course)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();

    }



    @Order(5)
    @Test
    void validate_unique_name() {
        final int ID = 1;
        course.setId(null);
        course.setName("Java Development Masterclass");
        client.post().uri(PATH )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(course)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody();

    }





}