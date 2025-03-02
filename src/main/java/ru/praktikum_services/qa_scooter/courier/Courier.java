package ru.praktikum_services.qa_scooter.courier;

import io.qameta.allure.Step;

import java.time.LocalDateTime;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Courier - Действие, создаем курьера со случайными данными")
    static Courier createRandomCourier() {
        return new Courier("AutoCourier" + LocalDateTime.now(), "P@ssword123", "Auto");
    }

    @Step("Courier - Действие, получаем логин курьера")
    public String getLogin() {
        return login;
    }

    @Step("Courier - Действие, получаем пароль курьера")
    public String getPassword() {
        return password;
    }

    @Step("Courier - Действие, получаем имя курьера")
    public String getFirstName() {
        return firstName;
    }

    @Step("Courier - Действие, сбрасывает логин курьера в null")
    public void setLoginToNull() {
        this.login = null;
    }

    @Step("Courier - Действие, сбрасываем пароль курьера в null")
    public void setPasswordToNull() {
        this.password = null;
    }

    @Step("Courier - Действие, сбрасываем имя курьера в null")
    public void setFirstNameToNull() {
        this.firstName = null;
    }

    @Step("Courier - Действие, устанавливаем курьеру новый пароль")
    public void setNewPassword() {
        this.password = this.password + LocalDateTime.now();
    }

    @Step("Courier - Действие, устанавливаем курьеру новое имя")
    public void setNewFirstName() {
        this.firstName = this.firstName + LocalDateTime.now();
    }
}
