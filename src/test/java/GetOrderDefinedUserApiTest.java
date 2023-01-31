import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.httpclient.HttpStatus;
import org.example.api.OrderRestClient;
import org.example.api.UserRestClient;
import org.example.models.OrderModel;
import org.example.models.UserModel;
import org.example.service.OrderService;
import org.example.service.UserGeneratorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderDefinedUserApiTest {
    UserModel userModel;
    String userAccessToken;
    UserRestClient userRestClient;
    OrderRestClient orderRestClient;
    OrderService orderService;

    public GetOrderDefinedUserApiTest() {
        userModel = (new UserGeneratorService()).getRandomUser();
        userRestClient = new UserRestClient();
        orderRestClient = new OrderRestClient();
        orderService = new OrderService();
    }

    @Before
    public void toCreateUser() {
        userAccessToken = userRestClient
                .createUser(userModel)
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Получение заказов конкретного авторизованого пользователя ")
    public void getOrderAuthUserTest() {
        OrderModel order = orderService.createOrderWithIngredients(3);
        orderRestClient.createOrder(userAccessToken, order)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true));

        orderRestClient.getOrderUser(userAccessToken)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Получение заказов конкретного не авторизованого пользователя ")
    public void getOrderNotAuthUser() {
        orderRestClient.getOrderUser("")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @After
    public void deleteCourier() {
        if (userAccessToken != null) {
            userRestClient.deleteUser(userAccessToken)
                    .then()
                    .statusCode(HttpStatus.SC_ACCEPTED);
        }
    }
}