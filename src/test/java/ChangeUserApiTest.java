import io.qameta.allure.junit4.DisplayName;
import org.example.api.UserRestClient;
import org.example.models.UserModel;
import org.example.service.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ChangeUserApiTest {
    UserRestClient userRestClient;
    UserModel userModel;
    String userAccessToken;

    public ChangeUserApiTest() {
        userRestClient = new UserRestClient();
        userModel = UserGenerator.getRandomUser();
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
        String email = UserGenerator.generateEmail();
        userModel.setEmail(email);

        Boolean messageChangeEmail = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(messageChangeEmail);
    }

    @Test
    @DisplayName("Изменение Password. Пользователь авторизован")
    public void changePasswordAuthorizationTrue() {
        String password = UserGenerator.generatePassword();
        userModel.setPassword(password);

        Boolean messageChangePassword = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(messageChangePassword);
    }

    @Test
    @DisplayName("Изменение Name. Пользователь авторизован")
    public void changeNameAuthorizationTrue() {
        String name = UserGenerator.generateName();
        userModel.setPassword(name);

        Boolean messageChangeName = userRestClient
                .updateUser(userModel, userAccessToken)
                .then()
                .statusCode(200)
                .extract()
                .path("success");

        assertTrue(messageChangeName);
    }

    @Test
    @DisplayName("Изменение E-mail. Пользователь  не авторизован")
    public void changeEmailAuthorizationFalse() {
        String email = UserGenerator.generateEmail();
        userModel.setEmail(email);

        Boolean messageChangeEmail = userRestClient
                .updateUser(userModel, "")
                .then()
                .statusCode(401)
                .extract()
                .path("success");

        assertFalse(messageChangeEmail);
    }

    @Test
    @DisplayName("Изменение Password. Пользователь не авторизован")
    public void changePasswordAuthorizationFalse() {
        String password = UserGenerator.generatePassword();
        userModel.setPassword(password);

        Boolean messageChangePassword = userRestClient
                .updateUser(userModel, "")
                .then()
                .statusCode(401)
                .extract()
                .path("success");

        assertFalse(messageChangePassword);
    }

    @Test
    @DisplayName("Изменение Name. Пользователь не авторизован")
    public void changeNameAuthorizationFalse() {
        String name = UserGenerator.generateName();
        userModel.setName(name);

        Boolean messageChangeName = userRestClient
                .updateUser(userModel, "")
                .then()
                .statusCode(401)
                .extract()
                .path("success");

        assertFalse(messageChangeName);
    }

    @After
    public void deleteCourier() {
        if (userAccessToken != null) {
            userRestClient.deleteUser(userAccessToken)
                    .then()
                    .statusCode(202);
        }
    }
}
