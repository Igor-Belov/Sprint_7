package ru.praktikum_services.qa_scooter.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.Client;

public class OrderClient extends Client {
    private static final String ORDERS_API_PATH = "/orders";


    @Step("OrderClient - Действие. Запрос на создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_API_PATH)
                .then().log().all();
    }

    @Step("OrderClient - Действие. Запрос на получение списка заказа ")
    public ValidatableResponse getListOfOrders() {
        return spec()
                .when()
                .get(ORDERS_API_PATH)
                .then().log().all();
    }
}
