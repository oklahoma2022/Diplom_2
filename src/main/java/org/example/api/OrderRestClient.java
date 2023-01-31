package org.example.api;

import io.restassured.response.Response;
import org.example.models.OrderModel;
import org.example.service.StatusHandlerService;

import static org.example.constants.ConstantEndpoints.*;

public class OrderRestClient extends BaseRest {

    StatusHandlerService statusService;

    public OrderRestClient() {
        statusService = new StatusHandlerService();
    }

    public String getIngredient(int index) {
        Response response = jsonRequest()
                .when()
                .get(GET_INGREDIENTS_API);

        return statusService.checkStatusCode(response)
                .then()
                .extract()
                .path("data[" + index + "]._id");
    }

    //данные для авторизованого пользователя
    public Response createOrder(String accessToken, OrderModel orderModel) {
        Response response = jsonRequest()
                .header("Authorization", accessToken)
                .when()
                .body(orderModel)
                .when()
                .post(CREATE_ORDER_API);

        return statusService.checkStatusCode(response);
    }

    public Response getOrderUser(String accessToken) {
        Response response = jsonRequest()
                .header("Authorization", accessToken)
                .when()
                .get(GET_USER_ORDER_API);

        return statusService.checkStatusCode(response);
    }
}
