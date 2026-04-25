package apis.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.SecretManager;

import java.util.Map;

public class ApiClient {

    private final String baseUrl;
    private final String apiKey;


    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.apiKey = SecretManager.get("REQRES_API_KEY");

    }

    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .header("x-api-key", apiKey);
    }

    private RequestSpecification request(Map<String, ?> queryParams) {
        RequestSpecification request = request();
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }
        return request;
    }

    public Response get(String endpoint) {
        return request().when().get(endpoint);
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        return request(queryParams).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return request().body(body).when().post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return request().body(body).when().put(endpoint);
    }

    public Response patch(String endpoint, Object body) {
        return request().body(body).when().patch(endpoint);
    }

    public Response delete(String endpoint) {
        return request().when().delete(endpoint);
    }
}