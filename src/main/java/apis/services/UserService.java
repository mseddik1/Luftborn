package apis.services;

import apis.api.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final ApiClient apiClient;

    public UserService(ApiClient apiClient){
        this.apiClient=apiClient;
    }

    @Step("Get all users with pagination")
    public Response getAllUsers(int page) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        return apiClient.get("/users", queryParams);
    }

    @Step("Create User")
    public Response createUser() {
        return apiClient.post("/users", "{}");
    }
}
