package ru.netology.rest;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public UserData getNewUser(String status, String locale) {
        Faker faker = new Faker(new Locale(locale));
        UserData userData = new UserData(faker.name().username(), faker.internet().password(), status);
        Gson gson = new Gson();
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(gson.toJson(userData)) // передаём в теле объект, который будет преобразован в JSON
                .when() // на какой путь, относительно BaseUri отправляем запрос (endpoint)
                .post("/api/system/users")
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 - OK
        return userData;
    }
}
