package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CourierChecks {

    @Step("CourierChecks - проверка что ответ сервера соответствует успешному созданию курьера")
    public void checkCreateCourier(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok")
                ;
        assertTrue(created);
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует уже занятому логину")
    public void checkCreateCourierConflict(ValidatableResponse createResponse) {
        String created = createResponse
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .path("message")
                ;
        assertEquals("Этот логин уже используется. Попробуйте другой.", created);
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует не полным данным запроса")
    public void checkCreateCourierBadRequest(ValidatableResponse createResponse) {
        String created = createResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .path("message")
                ;
        assertEquals("Недостаточно данных для создания учетной записи", created);
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует успешному входу курьера")
    public int checkLogInCourier(ValidatableResponse logInResponse) {
        int id = logInResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id")
                ;
        assertNotEquals(0, id);
        return id;
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует попытке входа с отсутствующими данными")
    public void checkLogInCourierBadRequest(ValidatableResponse logInResponse) {
        String LogInCourierMessage = logInResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .path("message")
                ;
        assertEquals("Недостаточно данных для входа", LogInCourierMessage);
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует попытке входа не зарегистрированным курьером")
    public void checkLogInCourierNotFound(ValidatableResponse logInResponse) {
        String LogInCourierMessage = logInResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .path("message")
                ;
        assertEquals("Учетная запись не найдена", LogInCourierMessage);
    }

    @Step("CourierChecks - проверка что ответ сервера соответствует успешному удалению курьера")
    public void checkDelete(ValidatableResponse DeleteResponse) {
        Boolean deleted = DeleteResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("ok")
                ;
        assertTrue(deleted);
    }

}

