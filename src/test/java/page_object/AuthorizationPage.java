package page_object;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AuthorizationPage {

    private final SelenideElement customerLoginButton = $x("//button[@ng-click='customer()']").as("КНопка Customer Login");
    private final SelenideElement loginButton = $x("//button[@type='submit']").as("Кнопка Login");
    private final SelenideElement userSelector = $x("//select[@id='userSelect']").as("Селектор");
    private final ElementsCollection namesOptional = $$x("//option").as("Варианты пользователей");

    @Step("Нажать на кнопку \"Customer Login\"")
    public void customerLoginButtonClick() {
        customerLoginButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Кликнуть на селектор")
    public void selectCustomerClick() {
        userSelector.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Выбрать клиента с именем '{name}'")
    public void selectCustomerChoose(String name) {
        namesOptional.asDynamicIterable().stream().filter(optional -> optional.getText().equals(name)).findFirst().get().click();
    }

    @Step("Нажать на кнопку \"Login\"")
    public void loginButtonClick() {
        loginButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

}
