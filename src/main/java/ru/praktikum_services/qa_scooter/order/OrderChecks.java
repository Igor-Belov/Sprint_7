package ru.praktikum_services.qa_scooter.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;


public class OrderChecks {

    @Step("OrderCheck - проверка что ответ сервера соответствует успешному созданию заказа.")
    public void checkCreateOrder(ValidatableResponse createResponse) {
        Integer created = createResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("track");
        assertNotNull("Трэк номер не вернулся в ответе", created);
    }

    @Step("OrderCheck - проверка что ответ сервера соответствует не пустому списку заказов.")
    public void checkListOfOrder(ValidatableResponse ListOfOrders) {
    ArrayList created = ListOfOrders
            .assertThat()
            .statusCode(HTTP_OK)
            .extract()
            .path("orders");
    assertFalse("Список заказов не содержит элементов", created.isEmpty());
    }
}
