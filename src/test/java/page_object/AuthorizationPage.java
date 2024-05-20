package page_object;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AuthorizationPage {

    @Step("Нажать на кнопку '{text}'")
    public void buttonClick(String text) {
        $x("//button[contains(text(), '" + text + "')]").shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Кликнуть на селектор")
    public void selectCustomerClick() {
        $x("//select").shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
    }

    @Step("Выбрать клиента с именем '{name}'")
    public void selectCustomerChoose(String name) {
        $x("//option[contains(text(), '"+ name +"')]").shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
    }

}
