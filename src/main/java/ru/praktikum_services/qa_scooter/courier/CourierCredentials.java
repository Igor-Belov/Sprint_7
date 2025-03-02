package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.Step;
import java.time.LocalDateTime;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("CourierCredentials - действие, сохраняем креды (пароль и логин) для созданного курьера")
    public static CourierCredentials extractCourierCredentials(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @Step("CourierCredentials - Действие, стираем пароль курьера из сохраненных кредов")
    public void setPasswordToNull() {
        this.password = null;
    }

    @Step("CourierCredentials - Действие, стираем логин курьера из сохраненных кредов")
    public void setLoginToNull() {
        this.login = null;
    }

    @Step("CourierCredentials - Действие, меняем в кредах курьера пароль")
    public void setBreakPassword() {
        this.password = this.password + LocalDateTime.now();
    }

    @Step("CourierCredentials - Действие, меняем в кредах курьера логин")
    public void setBreakLogin() {
        this.login = this.login + LocalDateTime.now();
    }
}
