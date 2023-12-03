package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTest extends TestBase {

    String dataUserForCreate = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
    String dataUserForUpdate = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
    String dataUserForRegister = "{ \"email\": \"sydney@fife\" }";


    @Test
    void successfulRequestSingleUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void negativeRequestSingleUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void successfulCreateUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(dataUserForCreate)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void successfulUpdateUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(dataUserForUpdate)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void successfulDeleteUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void negativeRegisterUnsuccessfulTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(dataUserForRegister)
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
