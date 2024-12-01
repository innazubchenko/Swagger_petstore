package API.config;

import io.restassured.RestAssured;

public class APIConfig {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";

    public static String getBaseUri() {
        return BASE_URI;
    }

    public static void setAuthorizationToken() {
        RestAssured.authentication = RestAssured.oauth2(API_KEY);
    }

    public static void deleteAuthorizationToken() {
        RestAssured.authentication = RestAssured.oauth2(null);
    }
}
