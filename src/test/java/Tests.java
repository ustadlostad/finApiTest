import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Tests extends BaseTest{

    JsonPath path;
    Helpers helpers = new Helpers();
    int randomNumber;
    String baseUrl = "https://api.themoviedb.org/3";
    String getMovieUrl = "/trending/movie/day";
    String movieDetailUrl = "/movie/";
    String movieRatingUrl = "/rating";
    int movieListSize;
    int movieId;

    @Test(priority = 0)
    public void getMovieList(){
        movieListSize= given()
                .header("Authorization",bearerToken)
                .get(baseUrl + getMovieUrl)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("results")
                .size();
        System.out.println(movieListSize);
    }

    @Test(priority = 1)
    public void pickRandomMovie(){
        randomNumber = helpers.randomGenerator(movieListSize + 1);

        movieId = given()
                .header("Authorization", bearerToken)
                .get(baseUrl + getMovieUrl)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("results["+randomNumber+"].id");

        System.out.println(movieId);
    }

    @Test(priority = 2)
    public void getMovieDetail(){

        int detailId = given()
                .header("Authorization", bearerToken)
                .get(baseUrl + movieDetailUrl + movieId )
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id");
        Assert.assertEquals(detailId,movieId);

    }

    @Test(priority = 3)
    public void PostRating(){

       String message = given()
                .queryParam("api_key",apiKey)
                .queryParam("guest_session_id",guest_session_id)
                .param("value",8.5)
                .when()
                .post(baseUrl + movieDetailUrl + movieId + movieRatingUrl)
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .get("status_message");
        System.out.println(message);
        Assert.assertEquals(message,"Success.");

    }

    @Test(priority = 3)
    public void deleteRating() {

        String message = given()
                .queryParam("api_key", apiKey)
                .queryParam("guest_session_id", guest_session_id)
                .param("value", 8.5)
                .when()
                .delete(baseUrl + movieDetailUrl + movieId + movieRatingUrl)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("status_message");
        System.out.println(message);
        Assert.assertEquals(message, "The item/record was deleted successfully.");
    }

}
