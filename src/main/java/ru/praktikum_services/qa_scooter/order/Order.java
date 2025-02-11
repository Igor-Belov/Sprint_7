package ru.praktikum_services.qa_scooter.order;

import io.qameta.allure.Step;

import java.util.List;

public class Order {
    private String firstName; //Имя заказчика, записывается в поле firstName таблицы Orders
    private String lastName; //Фамилия заказчика, записывается в поле lastName таблицы Orders
    private String address; //Адрес заказчика, записывается в поле adress таблицы Orders
    private String metroStation; //Ближайшая к заказчику станция метро, записывается в поле metroStation таблицы Orders
    private String phone; //Телефон заказчика, записывается в поле phone таблицы Orders
    private int rentTime; //Количество дней аренды, записывается в поле rentTime таблицы Orders
    private String deliveryDate; //Дата доставки, записывается в поле deliveryDate таблицы Orders
    private String comment; //Комментарий от заказчика, записывается в поле comment таблицы Orders
    private List<String> color; //Необязательный. Предпочитаемые цвета, записываются в поле color таблицы Orders

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Step("Order - Действие, создаем заказ передав цвет")
    public static Order createOrder(List<String> color) {
        return new Order("firstName","lastName", "address", "metroStation", "phone", 225, "2022-11-15", "comment", color);
    }

    @Step("Order - Действие, создаем заказ без цвета")
    public static Order createOrder() {
        return new Order("firstName","lastName", "address", "metroStation", "phone", 225, "2022-11-15", "comment", List.of());
    }


}


