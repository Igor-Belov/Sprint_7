package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.Client;
import java.util.Map;

public class CourierClient extends Client {
    private static final String COURIER_API_PATH = "/courier";

    @Step("CourierClient - действие, заходим курьером в личный кабинет")
    public ValidatableResponse logIn(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_API_PATH + "/login")
                .then().log().all();
    }

    @Step("CourierClient - действие, запрос на создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API_PATH)
                .then().log().all();
    }

    @Step("CourierClient - действие, запрос на удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return spec()
                .body(Map.of("id", courierId))
                .when()
                .delete(COURIER_API_PATH + "/" +courierId)
                .then().log().all();
    }

}
