package com.example;

import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void testGetUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given().
            when().
            get("/users/1").
            then().
            statusCode(200).
            body("username", equalTo("Bret"));
    }
}
