package org.example.service;
import com.github.javafaker.Faker;
import org.example.models.UserModel;

public class UserGenerator {
    public static UserModel getRandomUser() {
        Faker faker = new Faker();
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().name()
        );
    }
}

