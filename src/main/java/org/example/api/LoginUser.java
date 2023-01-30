package org.example.api;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.example.models.UserModel;

import static io.restassured.RestAssured.given;
import static org.example.constants.ConstantEndpoints.LOGIN_USER_API;

public class LoginUser {

    public static Response login(UserModel userModel) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userModel)
                .when()
                .post(LOGIN_USER_API);
    }

}
