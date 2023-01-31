package org.example.service;

import io.restassured.response.Response;

public class StatusHandlerService {

    public Response checkStatusCode(Response response) {
        if (response.getStatusCode() == 429) {
            throw new RuntimeException("Превышено количество запросов");
        }

        return response;
    }

}
