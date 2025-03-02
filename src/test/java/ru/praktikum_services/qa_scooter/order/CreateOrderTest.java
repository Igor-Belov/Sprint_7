//Orders - Создание заказа post api/v1/orders

//post Courier - Логин курьера в системе POST
//Создание заказа
//Проверь, что когда создаёшь заказ:
//можно указать один из цветов — BLACK или GREY;
//можно указать оба цвета;
//можно совсем не указывать цвет;
//тело ответа содержит track.
//Чтобы протестировать создание заказа, нужно использовать параметризацию.

package ru.praktikum_services.qa_scooter.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@DisplayName("Тесты параметрические создания заказа")
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private Order order;
    private OrderChecks check;

    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "value: {0}")
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK", "GRAY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        check = new OrderChecks();
    }

    @Test
    @DisplayName("CreateOrders - Создание заказа: 4 разных варианта цвета")
    public void createOrderTest() {
        order = Order.createOrder(this.color);
        ValidatableResponse response = orderClient.createOrder(order);
        check.checkCreateOrder(response);
    }
}

