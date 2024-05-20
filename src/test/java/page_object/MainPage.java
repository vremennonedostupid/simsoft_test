package page_object;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import util.Utils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPage {
    private final SelenideElement depositButton = $x("//button[contains(text(), 'Deposit')]").as("Кнопка Deposit");
    private final SelenideElement amountField = $x("//input[@placeholder='amount']").as("Поле ввода суммы");
    private final SelenideElement depositInFormButton = $x("//form/button[contains(text(), 'Deposit')]").as("Кнопка Deposit в форме");
    private final SelenideElement withdrawInFormButton = $x("//form/button[contains(text(), 'Withdraw')]").as("Кнопка Withdraw в форме");
    private final SelenideElement successDepositMessage = $x("//span[contains(text(), 'Successful')]").as("Строка об успешном зачислении");
    private final SelenideElement successWithdrawMessage = $x("//span[contains(text(), 'successful')]").as("Строка об успешной операции");
    private final SelenideElement withdrawlButton = $x("//button[contains(text(), 'Withdrawl')]").as("Кнопка Withdrawl");
    private final SelenideElement balanceElement = $x("//div[contains(string(), 'Balance : 0')]").as("Строка с балансом");
    private final SelenideElement transactionButton = $x("//button[contains(text(), 'Transactions')]");
    int amount = 0;

    @Step("Проверить, что заголовок страницы присутствует")
    public void checkTitle() {
        $x("//strong[contains(text(), 'Welcome')]").shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Нажать на кнопку \"Deposit\"")
    public void depositButtonClick() {
        depositButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Пополнить баланс")
    public void depositBalance() {
        if (amount == 0) amount = Utils.fibonacciCount(LocalDate.now().getDayOfMonth());
        amountField.shouldBe(Condition.visible, Duration.ofSeconds(10)).setValue(Integer.toString(amount));
        depositInFormButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить сообщение об успешном пополнении")
    public void checkSuccessDepositMessage() {
        assertTrue(successDepositMessage.isDisplayed());
    }

    @Step("Нажать кнопку \"Withdrawl\"")
    public void withdrawlButtonClick() {
        withdrawlButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Сделать списание с баланса")
    public void withdrawBalance() {
        if (amount == 0) amount = Utils.fibonacciCount(LocalDate.now().getDayOfMonth());
        $x("//label[text()='Amount to be Withdrawn :']").shouldBe(Condition.visible, Duration.ofSeconds(10));
        amountField.shouldBe(Condition.visible, Duration.ofSeconds(10)).setValue(Integer.toString(amount));
        withdrawInFormButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить сообщение об успешном списании")
    public void checkSuccessWithdrawMessage() {
        assertTrue(successWithdrawMessage.shouldBe(Condition.visible, Duration.ofSeconds(10)).isDisplayed());
    }

    @Step("Проверить, что баланс равен нулю")
    public void checkBalanceIsZero() {
        assertTrue(balanceElement.isDisplayed());
    }

    @Step("Нажать на кнопку Transactions")
    public void transactionButtonClick() {
        transactionButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить транзакции")
    public void checkTransactions() {
        List<SelenideElement> check = $$x("//table/tbody");
        if (check.isEmpty()) {
            Selenide.refresh();
            transactionButtonClick();
        }
        assertTrue($x("//td[text()=" + amount + "]/following::td[text()='Credit']").shouldBe(Condition.visible, Duration.ofSeconds(5)).isDisplayed());
        assertTrue($x("//td[text()=" + amount + "]/following::td[text()='Debit']").shouldBe(Condition.visible, Duration.ofSeconds(5)).isDisplayed());
    }

    @Step("Сохраняем транзакции в csv")
    public void saveTransactions() {
        $x("//table/tbody/tr").shouldBe(Condition.visible, Duration.ofSeconds(10));
        List<SelenideElement> rows = $$x("//table/tbody/tr");
        Utils.makeCsv(rows);
    }

}
