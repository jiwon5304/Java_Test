package com.example.java_test.adapter.in.web;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;


public class MyStepdefs {
    private Response  response;
    private String BASE_URL = "http://localhost:8080";

    // 회원 생성
    @Test
    public void createUser() {
        // given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("name", "user6");
        requestData.put("password", "password");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given()
                            .contentType("application/json")
                            .body(requestData);

        // when
        response = req.when().post("/users");

        // then
        response.then();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getString("name")).isEqualTo("user6");
        assertThat(response.body().jsonPath().getString("password")).isEqualTo("password");

    }

    // 전체 회원 조회
    @Test
    public void readUsers() {
        // given
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given();

        // when
        response = req.when().get("/users");

        // then
        response.then();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    // id로 회원 조회
    @Test
    public void readUserById() {
        // given
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given().pathParam("id",3);

        // when
        response = req.when().get("/users/{id}");

        // then
        response.then();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("user3");
        assertThat(response.jsonPath().getString("password")).isEqualTo("password");
    }

    // 회원 수정
    @Test
    public void updateUser() {
        //given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", 10);
        requestData.put("name", "new");
        requestData.put("password", "password");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given()
                            .contentType("application/json")
                            .pathParam("id", 10)
                            .body(requestData)
                            .log().all();

        // when
        response = req.when().put("/users/{id}");

        // then
        response.then();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("new");
        assertThat(response.jsonPath().getString("password")).isEqualTo("password");
    }

    // 회원 삭제
    @Test
    public void deleteUser() {
        // given
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given()
                .pathParam("id", 14);

        // when
        response = req.when().delete("/users/{id}");

        // then
        response.then();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
