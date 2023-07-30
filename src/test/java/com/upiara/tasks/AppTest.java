package com.upiara.tasks;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.ContentType;

public class AppTest {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
        .when()
            .get("/todo")
        .then()
            .log().all()
            .statusCode(200);
    }

    @Test
    public void deveAdcionarTarefaComSucesso() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body("{\n" + //
                "    \"task\": \"Teste via API\",\n" + //
                "    \"dueDate\": \"2030-12-30\"\n" + //
                "}")
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(201);
    }

    @Test
    public void naoDeveAdcionarTarefaInvalida() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body("{\n" + //
                "    \"task\": \"Teste via API\",\n" + //
                "    \"dueDate\": \"2010-12-30\"\n" + //
                "}")
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
