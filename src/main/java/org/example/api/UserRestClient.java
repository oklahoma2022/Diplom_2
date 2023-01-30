package org.example.api;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.models.UserModel;
import org.example.service.UserGenerator;

import static io.restassured.RestAssured.given;
import static org.example.constants.ConstantEndpoints.*;

public class UserRestClient extends BaseRest {

    public Response createUser(UserModel createUser) {
        Response response = jsonRequest()
                .body(createUser)
                .when()
                .post(CREATE_USER_API);

        if (response.getStatusCode() == 429) {
            throw new RuntimeException("Превышено количество запросов");
        }

        return response;
    }

    public Response deleteUser(String accessToken) {
        return jsonRequest()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_API);
    }

    public Response login(UserModel userModel) {
        return jsonRequest()
                .body(userModel)
                .when()
                .post(LOGIN_USER_API);
    }

    public Response updateUser(UserModel userModel, String accessToken) {
        return jsonRequest()
                .header("Authorization", accessToken)
                .body(userModel)
                .when()
                .patch(USER_API);
    }
}
