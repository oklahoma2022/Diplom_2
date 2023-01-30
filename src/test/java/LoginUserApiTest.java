import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.api.UserRestClient;
import org.example.models.UserModel;
import org.example.service.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginUserApiTest {
    UserRestClient userRestClient;
    UserModel userModel;

    String userAccessToken;

    public LoginUserApiTest() {
        userRestClient = new UserRestClient();
        userModel = UserGenerator.getRandomUser();
    }

    //Создаем курьера , которого будем авторизовывать
    @Before
    public void toCreateUser() {
        userRestClient.createUser(userModel);
    }

    @Test
    @DisplayName("Авторизация пользователя в системе")
    public void loginUser() {
        Response response = userRestClient.login(userModel);
        userAccessToken = response
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("accessToken");
        assertThat(userAccessToken, notNullValue());
    }

    @Test
    @DisplayName("Авторизация пользователя неверный E-mail")
    public void loginUserNoValidEmail() {
        userModel.setEmail("EmailNotvalid@yandex.ru");
        Response response = userRestClient.login(userModel);
        String massageEmailNotValid = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(massageEmailNotValid, equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("Авторизация пользователя неверный Password")
    public void loginUserNoValidPassword() {
        userModel.setPassword("123");
        Response response = userRestClient.login(userModel);
        String massagePasswordNotValid = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(massagePasswordNotValid, equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя пустой E-mail и Password")
    public void loginUserEmptyFields() {
        userModel.setEmail("EmailNotvalid@yandex.ru");
        userModel.setPassword("123");
        Response response = userRestClient.login(userModel);
        String messageEmptyFields = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(messageEmptyFields, equalTo("email or password are incorrect"));
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


