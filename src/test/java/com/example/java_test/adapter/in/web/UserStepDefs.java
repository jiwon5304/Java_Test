package com.example.java_test.adapter.in.web;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStepDefs {
    private Response response;

    private Map<String, Object> user;
    private String name;
    private String password;

    @Before
    public void setup() {
        RestAssured.baseURI="http://localhost:8080";
    }

    @When("조회할 회원의 ID {string} 입력받는다\"")
    public void 조회할_회원의_id_입력받는다(String string) {
        // Write code here that turns the phrase above into concrete actions
        response = RestAssured
                .given()
                .when().get("users/"+string);
    }

    @Then("status 코드 {string}을 수신한다")
    public void status코드을_수신한다(String arg0) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Then("user 정보를 수신한다")
    public void 회원_정보를_수신한다() {
        assertThat((int) response.body().path("id")).isEqualTo(3);
    }

    @When("클라이언트가 전체회사목록을 요청한다")
    public void 클라이언트가_전체회사목록을_요청한다() {
        response = RestAssured
                .given()
                .when().get("users");
    }

    @And("n{string}명의 user를 수신한다")
    public void n명의_User를_수신한다(String arg0) {
        assertThat((int)response.body().path("id[6]")).isEqualTo(15);
    }

    @Given("생성할 회원의 정보 {string} {string}")
    public void 생성할_회원의_정보(String name, String password) {
        user = new HashMap<>();
        user.put("name", name);
        user.put("password", password);
    }

    @When("입력 받은 정보로 회원을 생성한다")
    public void 입력받은정보로_회원을_생성한다() {
        response = RestAssured
                .given()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users");

    }
    @And("user {string} 정보를 수신한다")
    public void user정보를_수신한다(String name) {
        assertThat((String)response.body().path("name")).isEqualTo(name);
    }

    @Given("수정할 회원의 {string} {string} 입력받는다\"")
    public void 수정할회원의입력받는다(String name, String password){
        user = new HashMap<>();
        user.put("name", name);
        user.put("password", password);
        }

    @When("입력 받은 ID {string} 정보로 회원을 수정한다")
    public void 입력받은ID정보로회원을수정한다(String id) {
        response = RestAssured
                .given()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("users/"+id);
    }

    @And("수정된 user {string} 정보를 수신한다")
    public void 수정된User정보를수신한다(String name) {
        assertThat((String)response.body().path("name")).isEqualTo(name);
    }

    @When("삭제할 회원의 {string} 입력받는다")
    public void 삭제할회원의입력받는다(String id) {
        response = RestAssured
                .given()
                .when().delete("users/"+ id);
    }
}
