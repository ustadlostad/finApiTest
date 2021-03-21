import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BaseTest {

    String apiKey;
    String bearerToken;
    String request_token;
    String guest_session_id;
    String auth_base_url;

    @BeforeClass
    public void initialization(){

        bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMzJkNGNkNjFhY2EwYzBjZjFjODIzZTdjNDYzYzc5OCIsInN1YiI6IjYwNDUxOGY0ZTE4Yjk3MDA3OGU4MWMxYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0e48-JzDVWaFZb0asUS-wBroQdO8l7GWsgItn18ndHM";
        apiKey = "cc0a3effd7990f9a45633e52080005a8";
        auth_base_url = "https://api.themoviedb.org/3/authentication/";

        getRequestToken();
        getGuestSessionId();

    }

    public void getRequestToken(){
        request_token = given().
                header("Content-Type", "application/json")
                .param("api_key",apiKey)
                .get(auth_base_url + "token/new")
                .then()
                .statusCode(200)
                .extract()
                .path("request_token");
        System.out.println(request_token);
    }

    public void getGuestSessionId(){
        guest_session_id = given()
        .header("Content-Type", "application/json")
                .param("api_key",apiKey)
                .get(auth_base_url + "guest_session/new")
                .then()
                .statusCode(200)
                .extract()
                .path("guest_session_id");
        System.out.println(guest_session_id);
    }
}
