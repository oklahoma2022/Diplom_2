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

    public static String generateEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static String generateName() {
        Faker faker = new Faker();
        return faker.name().name();
    }
}

