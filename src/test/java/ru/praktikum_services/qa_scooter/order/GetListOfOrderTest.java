package ru.praktikum_services.qa_scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Тесты получения списка заказов")
public class GetListOfOrderTest {
    private OrderClient orderClient;
    private Order order;
    private OrderChecks check;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        check = new OrderChecks();
    }

    @Test
    @DisplayName("В теле ответа возвращается список заказов")
    public void listOfOrderReturnedInBody() {
        order = Order.createOrder();
        orderClient.createOrder(order);
        ValidatableResponse ListOfOrders = orderClient.getListOfOrders();
        check.checkListOfOrder(ListOfOrders);

    }
}
