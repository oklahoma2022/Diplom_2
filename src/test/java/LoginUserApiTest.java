import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.Validatable;
import org.example.api.CreateUser;
import org.example.api.LoginUser;
import org.example.models.UserModel;
import org.example.service.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginUserApiTest {
    CreateUser createUser;
    UserModel userModel;

    String userAccessToken;

    public LoginUserApiTest() {
        createUser = new CreateUser();
        userModel = UserGenerator.getRandomUser();
    }
    //Создаем курьера , которого будем авторизовывать
    @Before
    public void toCreateUser() {

        createUser.createUser(userModel);

    }
    @Test
    @DisplayName("Авторизация пользователя в системе")
    public void loginUser() {
        Response response = LoginUser.login(userModel);
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
        Response response = LoginUser.login(userModel);
        String massegeEmailNotvalid = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(massegeEmailNotvalid, equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("Авторизация пользователя неверный Password")
    public void loginUserNoValidPassword() {
        userModel.setPassword("123");
        Response response = LoginUser.login(userModel);
        String massegePasswordNotvalid = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(massegePasswordNotvalid, equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Авторизация пользователя пустой E-mail и Password")
    public void loginUserEmptyFields() {
        userModel.setEmail("EmailNotvalid@yandex.ru");
        userModel.setPassword("123");
        Response response = LoginUser.login(userModel);
        String massegeEmptyFields = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(massegeEmptyFields, equalTo("email or password are incorrect"));
    }
    @After
    public void deleteCourier() {
        if (userAccessToken != null) {
            createUser.deleteUser(userAccessToken)
                    .then()
                    .statusCode(202);
        }
    }
}


