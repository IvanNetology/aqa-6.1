package ru.netology.ibank;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @Test
    public void shouldYesUser() {
        String status = "active";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldNoUser() {
        String status = "active";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);
        RegistrationInfo wrongInfo = DataGenerator.Registration.invalidData();

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(wrongInfo.getLogin());
        $("[data-test-id='password'] input").setValue(wrongInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldBlockUser() {
        String status = "blocked";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldErrorLogin() {
        String status = "active";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);
        RegistrationInfo wrongInfo = DataGenerator.Registration.invalidData();

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(wrongInfo.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldErrorPassword() {
        String status = "active";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);
        RegistrationInfo wrongInfo = DataGenerator.Registration.invalidData();

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(wrongInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldBlankFields() {
        String status = "active";
        RegistrationInfo info = DataGenerator.Registration.setUpAll(status);
        RegistrationInfo blankField = DataGenerator.Registration.emptyData();

        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(blankField.getLogin());
        $("[data-test-id='password'] input").setValue(blankField.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
        $("[data-test-id='password'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
