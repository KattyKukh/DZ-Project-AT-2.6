package ru.netology.rest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.*;

public class AuthUserTest {
    DataGenerator generator = new DataGenerator();

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogInActiveUser() {
        UserData userActive = generator.getNewUser("active", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin());
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);
    }

    @Test
    public void shouldNotLogInBlockedUser() {
        UserData userActive = generator.getNewUser("blocked", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin());
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Пользователь заблокирован!"));
    }
    @Test
    public void shouldAlertWrongLoginIfActiveUser() {
        UserData userActive = generator.getNewUser("active", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin()+1);
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    public void shouldAlertWrongPasswordifActiveUser() {
        UserData userActive = generator.getNewUser("active", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin());
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword()+2);
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    public void shouldAlertWrongLoginIfBlockedUser() {
        UserData userActive = generator.getNewUser("blocked", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin()+3);
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    public void shouldAlertWrongPasswordifBlockedUser() {
        UserData userActive = generator.getNewUser("active", "en");
        $("[data-test-id='login'] .input__control").setValue(userActive.getLogin());
        $("[data-test-id='password'] .input__control").setValue(userActive.getPassword()+4);
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
