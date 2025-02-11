//Courier - Логин курьера в системе POST /api/v1/courier
package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;

@DisplayName("Тесты создания курьера")
public class CreateCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private CourierCredentials courierCredentials;
    private CourierChecks check;

    private int courierId;

    @Before
    public void set() {
        courierClient = new CourierClient();
        courier = Courier.createRandomCourier();
        courierCredentials = CourierCredentials.extractCourierCredentials(courier);
        check = new CourierChecks();
    }

    @After
    public void clean() {
        ValidatableResponse logIn = courierClient.logIn(courierCredentials);
        if (logIn.extract().statusCode() == HTTP_OK) {
            courierId = check.checkLogInCourier(logIn);
            ValidatableResponse deleteCourier = courierClient.deleteCourier(courierId);
            check.checkDelete(deleteCourier);//Да, в общих случаях проверки тут не уместны, но, например, если вы удаляете тестовые данные из базы данных, можно добавить проверку, что данные действительно удалены. Однако это должно быть исключением, а не правилом.
        }
    }

    @Test
    @DisplayName("Тест - курьера можно создать, все поля (логин, пароль, имя) заполнены")
    public void CreateCourierAllFieldsHttpCreated() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        check.checkCreateCourier(responseCreateCourier);
    }

    @Test
    @DisplayName("Тест - курьера можно создать, только обязательные поля (логин, пароль) заполнены")
    public void CreateCourierRequiredFieldsHttpCreated() {
        courier.setFirstNameToNull();
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        check.checkCreateCourier(responseCreateCourier);
    }

    @Test
    @DisplayName("Тест - нельзя создать двух одинаковых курьеров")//Тест лишний, так как является частным случаем "Тест - если создать курьера с логином, который уже есть, возвращается ошибка; Или нужно уточнить требования по тесту"
    public void CreateTwoIdenticalCourierHttpConflict() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        ValidatableResponse responseCreateSameCourier = courierClient.createCourier(courier);
        check.checkCreateCourierConflict(responseCreateSameCourier);
    }

    @Test
    @DisplayName("Тест - чтобы создать курьера, нужно передать в ручку все обязательные поля (Негативный тест без пароля);")
    public void CreateCourierWithoutPasswordHttpBadRequest() {
        courier.setPasswordToNull();
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        check.checkCreateCourierBadRequest(responseCreateCourier);
    }

    @Test
    @DisplayName("Тест - чтобы создать курьера, нужно передать в ручку все обязательные поля (Негативный тест без логина);")
    public void CreateCourierWithoutLoginHttpBadRequest() {
        courier.setLoginToNull();
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        check.checkCreateCourierBadRequest(responseCreateCourier);
    }

    @Test
    @DisplayName("Тест - если создать курьера с логином, который уже есть, возвращается ошибка;") //общий случай теста "Тест - нельзя создать двух одинаковых курьеров"
    public void CreateCourierSameLoginBadRequest() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        courier.setNewPassword();
        courier.setNewFirstName();
        ValidatableResponse responseCreateSameLoginCourier = courierClient.createCourier(courier);
        check.checkCreateCourierConflict(responseCreateSameLoginCourier);
    }

    //Напрашиваются еще тесты на отсутствие уникальности для паролей и имен, но в ТЗ этого нет.
    //Так же можно было бы попередавать невалидные значения полей
}
