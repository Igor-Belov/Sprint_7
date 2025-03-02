package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class Client {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String BASE_PATH = "/api/v1";

    @Step("Client - Действие. Создание спецификации запроса")
    public RequestSpecification spec() {
        return given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
        ;
    }
}
