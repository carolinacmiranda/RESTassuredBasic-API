package com.example;

import org.junit.Test;
import org.hamcrest.Matchers;
import java.util.Map;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTest {
    @Test
    public void testSWAPI() {
        given()
            .log().all()
        .when()
            .get("https://swapi.dev/api/people/1/")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", Matchers.is("Luke Skywalker"));
    }

    @Test
    public void testPasswordlessAccess() {
        given()
            .log().all()
        .when()
            .get("https://restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(401);
    }

    @Test
    public void testPasswordAccess() {
        given()
            .log().all()
        .when()
            .get("https://admin:senha@restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is("logado"));
    }

    @Test
    public void testPasswordAccess2() {
        given()
            .log().all()
            .auth().basic("admin", "senha")
        .when()
            .get("https://restapi.wcaquino.me/basicauth")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is("logado"));
    }

    @Test
    public void testWeather() {
        given()
            .log().all()
            .queryParams("q", "Salvador,BR")
            .queryParams("appid", "6bd73aaadb3fe8bc989050f5a68ce77b")
            .queryParams("units", "metric")
        .when()
            .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Salvador"))
            .body("main.temp", greaterThan(25f));
    }

    @Test
    public void testAuthTokenJWT() {
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "email123@teste.com.br");
        login.put("senha", "Teste@123");

        String token = given()
            .log().all()
            .body(login)
            .contentType("application/json")
        .when()
            .post("https://barrigarest.wcaquino.me/signin")
        .then()
            .log().all()
            .statusCode(200)
            .extract().path("token");

        given()
            .log().all()
            .header("Authorization", "JWT " + token)
        .when()
            .get("https://barrigarest.wcaquino.me/contas")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", hasItem(2230162));
    }
}
