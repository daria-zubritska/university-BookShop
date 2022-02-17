package ua.university.kma.BookShop.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ua.university.kma.BookShop.dto.AddResponseDto;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRestControllerIntegrationTest {

    @LocalServerPort
    void savePort(final int port) {

        RestAssured.port = port;
    }

    @Test
    void shouldSendRequestAdd() throws JsonProcessingException {
        given()
                .body(MyRestControllerTest.class.getResourceAsStream("/requestAdd.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("requiredField", "value")
                .when()
                .post("/add-book")
                .then()
                .body("response", CoreMatchers.is("Success"));
    }

    @Test
    void shouldSendRequest_testResponseAsObjectAdd() {
        final AddResponseDto responseDto = given()
                .body(MyRestControllerTest.class.getResourceAsStream("/requestAdd.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("requiredField", "value")
                .when()
                .post("/add-book")
                .then()
                .header(HttpHeaders.AUTHORIZATION, CoreMatchers.startsWith("generated-jwt"))
                .extract()
                .as(AddResponseDto.class);

        assertThat(responseDto)
                .returns("Success", AddResponseDto::getResponse);
    }

}
