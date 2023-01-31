import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.httpclient.HttpStatus;
import org.example.api.UserRestClient;
import org.example.models.UserModel;
import org.example.service.UserGeneratorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangeUserApiTest {
    UserGeneratorService userGenerator;
    UserRestClient userRestClient;
    UserModel userModel;
    String userAccessToken;

    public ChangeUserApiTest() {
        userGenerator = new UserGeneratorService();
        userModel = userGenerator.getRandomUser();
        userRestClient = new UserRestClient();
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
    @DisplayName("Изменение E-mail. Пользователь авторизован")
    public void changeEmailAuthorizationTrue() {
        String email = userGenerator.generateEmail();
        userModel.setEmail(email);

        Boolean messageChangeEmail = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("success");

        assertTrue(messageChangeEmail);
    }

    @Test
    @DisplayName("Изменение Password. Пользователь авторизован")
    public void changePasswordAuthorizationTrue() {
        String password = userGenerator.generatePassword();
        userModel.setPassword(password);

        Boolean messageChangePassword = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("success");

        assertTrue(messageChangePassword);
    }

    @Test
    @DisplayName("Изменение Name. Пользователь авторизован")
    public void changeNameAuthorizationTrue() {
        String name = userGenerator.generateName();
        userModel.setPassword(name);

        Boolean messageChangeName = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("success");

        assertTrue(messageChangeName);
    }

    @Test
    @DisplayName("Изменение E-mail. Пользователь  не авторизован")
    public void changeEmailAuthorizationFalse() {
        String email = userGenerator.generateEmail();
        userModel.setEmail(email);

        Boolean messageChangeEmail = userRestClient
                .updateUser(userModel, "") // указываем пустой токен
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .path("success");

        assertFalse(messageChangeEmail);
    }

    @Test
    @DisplayName("Изменение Password. Пользователь не авторизован")
    public void changePasswordAuthorizationFalse() {
        String password = userGenerator.generatePassword();
        userModel.setPassword(password);

        Boolean messageChangePassword = userRestClient
                .updateUser(userModel, "")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .path("success");

        assertFalse(messageChangePassword);
    }

    @Test
    @DisplayName("Изменение Name. Пользователь не авторизован")
    public void changeNameAuthorizationFalse() {
        String name = userGenerator.generateName();
        userModel.setName(name);

        Boolean messageChangeName = userRestClient
                .updateUser(userModel, "")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .path("success");

        assertFalse(messageChangeName);
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
