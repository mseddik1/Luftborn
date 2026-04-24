package apis.services;

import apis.api.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {

    private final ApiClient apiClient;

    public AuthService(ApiClient apiClient){
        this.apiClient = apiClient;
    }


    @Step("Login: loginData='{loginData}'")
    public Response login(JsonNode loginData) {
        return apiClient.post("auth/login",loginData);
    }
}
