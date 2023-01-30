import org.example.api.CreateUser;
import io.qameta.allure.junit4.DisplayName;
import org.example.models.UserModel;
import org.example.service.UserGenerator;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

//    создать уникального пользователя;
//    создать пользователя, который уже зарегистрирован;
//    создать пользователя и не заполнить одно из обязательных полей.

public class CreatureUserApiTest {
    CreateUser createUser;
    UserModel userModel;
    String userAccessToken;

    public CreatureUserApiTest() {
        createUser = new CreateUser();
        userModel = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Проверка создания курьера.Позитивный кейс")
    public void toCreateUserValidTest() {
        userAccessToken = createUser.createUser(userModel)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    //    Создание пользователя с такими же данныеми
    @Test
    @DisplayName("Проверка создания одинаковых Пользователей")
    public void createDoubleUserTest() {
        userAccessToken = createUser.createUser(userModel)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

        createUser.createUser(userModel)
                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка создания курьера если не передаем обязательное поле E-mail")
    public void toCreateUserNoEmail() {
        UserModel newUser = new UserModel("", userModel.getPassword(), userModel.getName());

        userAccessToken = createUser.createUser(newUser)
                .then()
                .statusCode(403)
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

        userAccessToken = createUser.createUser(newUser)
                .then()
                .statusCode(403)
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

        userAccessToken = createUser.createUser(newUser)
                .then()
                .statusCode(403)
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

        userAccessToken = createUser.createUser(newUser)
                .then()
                .statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
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
