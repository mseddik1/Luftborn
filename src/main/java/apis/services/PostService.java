package apis.services;

import apis.api.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PostService {

    private final ApiClient apiClient;

    public PostService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Step("Get all posts'")
    public Response getAllPosts() {
        return apiClient.get("/posts");
    }
    @Step("Get all posts with pagination")
    public Response getAllPosts(int limit, int skip) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("skip", skip);
        return apiClient.get("/posts", queryParams);
    }

    public Response getPostById(int postId) {
        return apiClient.get("/posts/" + postId);
    }


}