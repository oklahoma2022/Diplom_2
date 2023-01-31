package org.example.service;

import com.github.javafaker.Faker;
import org.example.models.UserModel;

public class UserGeneratorService {
    Faker faker;

    public UserGeneratorService() {
        faker = new Faker();
    }

    public UserModel getRandomUser() {
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().name()
        );
    }

    public String generateEmail() {
        return faker.internet().emailAddress();
    }

    public String generatePassword() {
        return faker.internet().password();
    }

    public String generateName() {
        return faker.name().name();
    }
}

