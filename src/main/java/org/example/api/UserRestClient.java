package org.example.api;

import io.restassured.response.Response;
import org.example.models.UserModel;
import org.example.service.StatusHandlerService;

import static org.example.constants.ConstantEndpoints.*;

public class UserRestClient extends BaseRest {

    StatusHandlerService statusService;

    public UserRestClient() {
        statusService = new StatusHandlerService();
    }

    public Response createUser(UserModel createUser) {
        Response response = jsonRequest()
                .body(createUser)
                .when()
                .post(CREATE_USER_API);

        return statusService.checkStatusCode(response);
    }

    public Response deleteUser(String accessToken) {
        Response response = jsonRequest()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_API);

        return statusService.checkStatusCode(response);
    }

    public Response login(UserModel userModel) {
        Response response = jsonRequest()
                .body(userModel)
                .when()
                .post(LOGIN_USER_API);

        return statusService.checkStatusCode(response);
    }

    public Response updateUser(UserModel userModel, String accessToken) {
        Response response = jsonRequest()
                .header("Authorization", accessToken)
                .body(userModel)
                .when()
                .patch(USER_API);

        return statusService.checkStatusCode(response);
    }
}
