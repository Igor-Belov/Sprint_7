//Courier - Создание курьера POST /api/v1/courier/login
package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;

@DisplayName("Тесты LogIn курьера")
public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private CourierCredentials courierCredentials;
    private CourierCredentials originalCourierCredentials;//для хранения исходных кредов без модификаций
    private ValidatableResponse responseCreateCourier;
    private CourierChecks check;

    private int courierId;

    @Before
    public void set() {
        courierClient = new CourierClient();
        courier = Courier.createRandomCourier();
        courierCredentials = CourierCredentials.extractCourierCredentials(courier);
        check = new CourierChecks();
        responseCreateCourier = courierClient.createCourier(courier);
        Assume.assumeTrue(responseCreateCourier.extract().statusCode() == 201);
    }

    @After
    public void clean() {
        originalCourierCredentials = CourierCredentials.extractCourierCredentials(courier); //получить оригинальные креды под которыми можно залогинеться
        ValidatableResponse logIn = courierClient.logIn(originalCourierCredentials);
        if (logIn.extract().statusCode() == HTTP_OK) {
            courierId = check.checkLogInCourier(logIn);
            ValidatableResponse deleteCourier = courierClient.deleteCourier(courierId);
            check.checkDelete(deleteCourier);//Да, в общих случаях проверки тут не уместны, но, например, если вы удаляете тестовые данные из базы данных, можно добавить проверку, что данные действительно удалены. Однако это должно быть исключением, а не правилом.
        }
    }

    @Test
    @DisplayName("Тест - курьер может авторизоваться и запрос вернет id;")
    public void AuthorizationCourierHttpOk() {
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        check.checkLogInCourier(logIn);
    }

    @Test
    @DisplayName("Тест - курьер не может авторизоваться без логина;")
    public void AuthorizationCourierWithoutLoginHttpBadRequest() {
        courierCredentials.setLoginToNull();
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        check.checkLogInCourierBadRequest(logIn);
    }

//    @Test
//    @DisplayName("Тест - курьер не может авторизоваться без пароля;")
//    public void AuthorizationCourierWithoutPasswordHttpBadRequest() {
//        courierCredentials.setPasswordNull();
//        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
//        check.checkLogInCourierBadRequest(logIn);
//    }

    @Test
    @DisplayName("Тест - система вернёт ошибку, если неправильно указать логин;")
    public void AuthorizationCourierFailLoginHttpNotFound() {
        courierCredentials.setBreakLogin();
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        check.checkLogInCourierNotFound(logIn);
    }

    @Test
    @DisplayName("Тест - система вернёт ошибку, если неправильно указать пароль;")
    public void AuthorizationCourierFailPasswordHttpNotFound() {
        courierCredentials.setBreakPassword();
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        check.checkLogInCourierNotFound(logIn);
    }

    @Test
    @DisplayName("Тест - система вернёт ошибку, если указать несуществующего пользователя;")//избыточный тест, частный случай "Тест - система вернёт ошибку, если неправильно указать логин;"
    public void AuthorizationCourierUnknownUserHttpNotFound() {
        courierCredentials.setBreakLogin();
        courierCredentials.setBreakPassword();
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        check.checkLogInCourierNotFound(logIn);
    }
}