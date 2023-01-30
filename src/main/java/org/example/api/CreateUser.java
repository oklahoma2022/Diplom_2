package org.example.api;

import io.restassured.response.Response;
import org.example.models.UserModel;

import static org.example.constants.ConstantEndpoints.CREATE_USER_API;
import static org.example.constants.ConstantEndpoints.USER_API;

public class CreateUser extends BaseRest {

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
}