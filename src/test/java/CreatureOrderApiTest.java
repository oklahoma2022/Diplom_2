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

public class CreatureOrderApiTest {
    UserModel userModel;
    String userAccessToken;

    UserRestClient userRestClient;
    OrderRestClient orderRestClient;
    OrderService orderService;

    public CreatureOrderApiTest() {
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
    @DisplayName("Создание заказа с авторизованным пользователем c ингридиентами")
    public void createOrderByAuthUserTest() {
        OrderModel order = orderService.createOrderWithIngredients(3);

        orderRestClient.createOrder(userAccessToken, order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизованным пользователем без ингридиентов")
    public void createOrderByAuthUserNoIngredientsTest() {
        OrderModel order = new OrderModel();

        orderRestClient.createOrder(userAccessToken, order)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа с авторизованным пользователем и неверным ХЕШЕМ ингридиентов")
    public void createOrderByAuthUserIncorrectHashTest() {
        OrderModel order = orderService.createOrderWithCustomIngredient("TestIncorrectHash");

        orderRestClient.createOrder(userAccessToken, order)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }


    @Test
    @DisplayName("Создание заказа пользователь не авторизован с ингридиентами ")
    public void createOrderByNotAuthUserTest() {
        OrderModel order = orderService.createOrderWithIngredients(3);

        orderRestClient.createOrder("", order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));

    }

    @Test
    @DisplayName("Создание заказа пользователь не авторизован  без ингридиентов ")
    public void createOrderByNotAuthNoIngredientsUserTest() {
        OrderModel order = new OrderModel();

        orderRestClient.createOrder("", order)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа пользователь не авторизован и  с неверным ХЕШЕМ ингридиентов")
    public void createOrderByNotAuthIncorrectHashTest() {
        OrderModel order = orderService.createOrderWithCustomIngredient("TestIncorrectHash");

        orderRestClient.createOrder("", order)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

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
