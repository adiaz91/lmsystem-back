package com.lmsystem.integration;

import com.lmsystem.data.StudentFactory;
import com.lmsystem.entities.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.time.LocalDate;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerIntTest {

    private static final String PATH = "/api/students";
    @Autowired
    private WebTestClient client;
    private Student student;

    @BeforeEach
    public void setup() {
        student = StudentFactory.create001();
    }


    @Order(1)
    @Test
    void findByEmail_successful() {
        final String EMAIL = "alice@example.com";

        client.get().uri(PATH + "/{email}", EMAIL).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(10)
                .jsonPath("$.address").isEqualTo("123 Main Street")
                .jsonPath("$.email").isEqualTo(EMAIL)
                .jsonPath("$.firstName").isEqualTo("Alice")
                .jsonPath("$.lastName").isEqualTo("Smith")
                .jsonPath("$.phoneNumber").isEqualTo("555-555-5555")
                .jsonPath("$.dateOfBirth").isEqualTo("1995-05-15");

    }

    @Order(2)
    @Test
    void findByEmail_notFound() {
        final String EMAIL = "notfound@mail.com";
        client.get().uri(PATH + "/{email}", EMAIL).exchange()
                .expectStatus().isNotFound();

    }




    @Order(3)
    @Test
    void create_successful() {
        student.setId(null);

        client.post().uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.address").isEqualTo("18th Street 47")
                .jsonPath("$.email").isEqualTo("mail@example.com")
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.lastName").isEqualTo("Doe")
                .jsonPath("$.phoneNumber").isEqualTo("36985742")
                .jsonPath("$.dateOfBirth").isEqualTo("2000-01-01");
    }

    @Order(4)
    @Test
    void validate_required_fields() {

        student = new Student();

        client.post().uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

    }



    @Order(5)
    @Test
    void validate_unique_email() {
        final int ID = 1;
        student.setId(null);
        student.setEmail("alice@example.com");
        client.post().uri(PATH )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The email is already in use");

    }


    @Order(6)
    @Test
    void test_create_valid_age() {
        final int ID = 1;
        student.setId(null);
        student.setDateOfBirth(LocalDate.now());
        client.post().uri(PATH )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(student)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Students must be over 16 years of age");

    }


}