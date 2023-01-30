package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRest {
    protected BaseRest() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    protected RequestSpecification jsonRequest() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON);
    }
}

