package ru.netology.rest;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;


public class AuthUserTest {
    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    String wrongLogin = DataGenerator.Generation.getUsername("en");
    String wrongPassword = DataGenerator.Generation.getPassword("en");

    UserData activeUser = DataGenerator.Generation.getNewUser("active", "en");
    UserData blockedUser = DataGenerator.Generation.getNewUser("blocked", "en");

    @Test
    public void shouldLogInActiveUser() {
        $("[data-test-id='login'] .input__control").setValue(activeUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);
    }

    @Test
    public void shouldNotLogInBlockedUser() {
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"), Duration.ofSeconds(15));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    public void shouldAlertWrongLoginIfActiveUser() {
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldAlertWrongPasswordIfActiveUser() {
        $("[data-test-id='login'] .input__control").setValue(activeUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
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
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldAlertWrongPasswordIfBlockedUser() {
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка!"));
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
