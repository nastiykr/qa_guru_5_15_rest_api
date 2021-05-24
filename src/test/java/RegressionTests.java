import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegressionTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void createUser() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"nasst\", \"job\": \"qa\" }")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("nasst"));
    }

    @Test
    void updateUserInfo() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"nast\"}")
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("nast"));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void getInfoUser() {
        given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    void loginWithNoPassword() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"peter@klaven\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
