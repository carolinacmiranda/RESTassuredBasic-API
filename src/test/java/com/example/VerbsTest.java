package com.example;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.BeforeClass;
import java.util.Map;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VerbsTest {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";
        RestAssured.basePath = "/users";
    }

    @Test
    public void testPOST() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Jose");
        params.put("age", 50);

        given()
            .log().all()
            .contentType("application/json")
            .body(params)
        .when()
            .post()
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("name", is("Jose"))
            .body("age", is(50));
    }    

    @Test
    public void testPOST1() {
        User user = new User("Ana Maria", 34, null);

        given()
            .log().all()
            .contentType("application/json")
            .body(user)
        .when()
            .post()
        .then()
            .log().all()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("name", is("Ana Maria"))
            .body("age", is(34));
    }

    @Test
    public void testCheckRequiredFields() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{\"age\": 50}")
        .when()
            .post()
        .then()
            .log().all()
            .statusCode(400)
            .body("id", is(nullValue()))
            .body("error", is("Name é um atributo obrigatório"));
    }

    @Test
    public void testPUT() {
        given()
            .log().all()
            .contentType("application/json")
            .body("{\"name\": \"Joao\", \"age\": 55}")
        .when()
            .put("/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1))
            .body("name", is("Joao"))
            .body("age", is(55));
    }

    @Test
    public void testDELETE() {
        given()
            .log().all()
        .when()
            .delete("/3")
        .then()
            .log().all()
            .statusCode(204);
    }

    @Test
    public void testDELETENonExistentUser() {
        given()
            .log().all()
        .when()
            .delete("/1001")
        .then()
            .log().all()
            .statusCode(400)
            .body("error", is("Registro inexistente"));
    }
}
