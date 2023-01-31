import org.apache.commons.httpclient.HttpStatus;
import org.example.api.UserRestClient;
import io.qameta.allure.junit4.DisplayName;
import org.example.models.UserModel;
import org.example.service.UserGeneratorService;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreatureUserApiTest {
    UserRestClient userRestClient;
    UserModel userModel;
    String userAccessToken;

    public CreatureUserApiTest() {
        userModel = (new UserGeneratorService()).getRandomUser();
        userRestClient = new UserRestClient();
    }

    @Test
    @DisplayName("Проверка создания курьера.Позитивный кейс")
    public void toCreateUserValidTest() {
        userAccessToken = userRestClient.createUser(userModel)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Проверка создания одинаковых Пользователей")
    public void createDoubleUserTest() {
        userAccessToken = userRestClient.createUser(userModel)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("accessToken");

        userRestClient.createUser(userModel)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле E-mail")
    public void toCreateUserNoEmail() {
        UserModel newUser = new UserModel("", userModel.getPassword(), userModel.getName());

        userAccessToken = userRestClient.createUser(newUser)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле Password")
    public void toCreateUserNoPassword() {
        UserModel newUser = new UserModel(userModel.getEmail(), "", userModel.getName());

        userAccessToken = userRestClient.createUser(newUser)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле Name")
    public void toCreateUserNoName() {
        UserModel newUser = new UserModel(userModel.getEmail(), userModel.getPassword(), "");

        userAccessToken = userRestClient.createUser(newUser)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Проверка создания курьера если ВСЕ поля пустые")
    public void toCreateUserNoEmpty() {
        UserModel newUser = new UserModel("", "", "");

        userAccessToken = userRestClient.createUser(newUser)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
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
