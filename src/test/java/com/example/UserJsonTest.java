package com.example;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import org.junit.BeforeClass;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.builder.ResponseSpecBuilder;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserJsonTest {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";
        RestAssured.basePath = "/users";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();
        
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        resSpec = resBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = resSpec;
    }

    @Test
    public void testGetUsers() {
        given()
        .when()
            .get()
        .then()
            .body("", hasSize(3))
            .body("id", hasItems(1,2,3));
    }

    @Test
    public void testGetUser2() {
        given()
        .when()
            .get("/2")
        .then()
            .body("name", containsString("Joaquina"))
            .body("age", greaterThan(18));
    }

    @Test
    public void testGetUserResponse() {
        Response response = RestAssured.request(Method.GET, baseURI + basePath + "/2");

        //path
        Assert.assertEquals(new Integer(2), response.path("id"));

        //or

        //jsonpath
        JsonPath jpath = new JsonPath(response.asString());
        Assert.assertEquals(2, jpath.getInt("id"));

        //or

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(2, id);
    }

    @Test
    public void testSecondLevel() {
        given()
        .when()
            .get("/2")
        .then()
            .body("endereco.rua", is("Rua dos bobos"));
    }

    @Test
    public void testCheckList() {
        given()
        .when()
            .get("/3")
        .then()
            .body("name", is("Ana Júlia"))
            .body("filhos", hasSize(2))
            .body("filhos[0].name", is("Zezinho"));
    }

    @Test
    public void testUserError() {
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(404);
        resSpec = resBuilder.build();
        RestAssured.responseSpecification = resSpec;

        given()
        .when()
            .get("/4")
        .then()
            .body("error", is("Usuário inexistente"));
    }

    @Test
    public void testMoreAdvancedChecks() {
        given()
        .when()
            .get()
        .then()
            .body("", hasSize(3))
            .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
            .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"));
    }
    
    @Test
    public void testJoinJsonPathWithJAVA() {
        List<String> names =
        given()
        .when()
            .get()
        .then()
            .extract().path("name.findAll{it.startsWith('Maria')}");

        Assert.assertEquals(1, names.size());
    }
}
