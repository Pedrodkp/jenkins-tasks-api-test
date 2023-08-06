import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AppTest {
    @Test
    public void deveRetornarTarefasTest() {
        RestAssured.given()
        .when()
            .get("http://localhost:8001/tasks-backend/todo")
        .then()
            .log().all()
            .statusCode(200);
    }

    @Test
    public Integer deveAdcionarTarefaComSucessoTest() {
        Integer id = RestAssured.given()
            .contentType(ContentType.JSON)
            .body("{\n" + //
                "    \"task\": \"Teste via API\",\n" + //
                "    \"dueDate\": \"2030-12-30\"\n" + //
                "}")
        .when()
            .post("http://localhost:8001/tasks-backend/todo")
        .then()
            .log().all()
            .statusCode(201)
            .extract().path("id");
        return id;
    }

    @Test
    public void naoDeveAdcionarTarefaInvalidaTest() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body("{\n" + //
                "    \"task\": \"Teste via API\",\n" + //
                "    \"dueDate\": \"2010-12-30\"\n" + //
                "}")
        .when()
            .post("http://localhost:8001/tasks-backend/todo")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", CoreMatchers.is("Due date must not be in past"));
    }

    @Test
    public void deveRemoverTarefaComSucessoTest() {
        Integer id = deveAdcionarTarefaComSucessoTest();
        System.out.println(id);

        RestAssured.given()
        .when()
            .delete("http://localhost:8001/tasks-backend/todo/"+id)
        .then()
            .log().all()
            .statusCode(204);
    }
}
