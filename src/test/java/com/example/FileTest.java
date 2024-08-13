package com.example;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.BeforeClass;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class FileTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";
    }

    @Test
    public void testSendFile() {
        given()
            .log().all()
        .when()
            .post("/upload")
        .then()
            .log().all()
            .statusCode(404)
            .body("error", is("Arquivo não enviado"));
    }

    @Test
    public void testUploadFile() {
        given()
            .log().all()
            .multiPart("arquivo", new File("src/main/resources/RestAssuredWorkshop.pdf"))
        .when()
            .post("/upload")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("RestAssuredWorkshop.pdf"));
    }

    @Test
    public void testDownloadFile() throws IOException {
        byte[] fileContent = given()
                                .log().all()
                             .when()
                                .get("/download")
                             .then()
                                .log().all()
                                .statusCode(200)
                                .extract().asByteArray();

        File image = new File("src/main/resources/Image.pdf");

        try (OutputStream out = new FileOutputStream(image)) {
            out.write(fileContent);
        }

        System.out.println("File size: " + image.length() + " bytes");
        assertThat(image.length(), lessThan(100000L));
    }

    @Test
    public void testSchemaJson() {
        given()
            .log().all()
        .when()
            .get("/users")
        .then()
            .log().all()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("users.json"));
    }
}
