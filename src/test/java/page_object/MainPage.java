package page_object;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import util.Utils;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement depositButton = $x("//button[@ng-click=\"deposit()\"]").as("Кнопка Deposit");
    private final SelenideElement amountField = $x("//input[@placeholder='amount']").as("Поле ввода суммы");
    private final SelenideElement depositInFormButton = $x("//form[@ng-submit='deposit()']/button[@type='submit']").as("Кнопка Deposit в форме");
    private final SelenideElement withdrawInFormButton = $x("//form[@ng-submit='withdrawl()']/button[@type='submit']").as("Кнопка Withdraw в форме");
    private final SelenideElement successDepositMessage = $x("//span[contains(text(), 'Deposit Successful')]").as("Строка об успешном зачислении");
    private final SelenideElement successWithdrawMessage = $x("//span[contains(text(), 'Transaction successful')]").as("Строка об успешной операции");
    private final SelenideElement withdrawlButton = $x("//button[@ng-click='withdrawl()']").as("Кнопка Withdrawl");
    private final SelenideElement balanceElement = $x("//div[contains(string(), 'Balance : 0')]").as("Строка с балансом");
    private final SelenideElement transactionButton = $x("//button[contains(text(), 'Transactions')]").as("Кнопка \"Транзакции\"");
    private final SelenideElement withdrawAmountFieldLabel = $x("//label[text()='Amount to be Withdrawn :']").as("Текст над строчкой с полей ввода для списания");
    private final SelenideElement depositAmountFieldLabel = $x("//label[text()='Amount to be Deposited :']").as("Текст над строчкой с полей ввода для зачисления");
    private final SelenideElement mainPageGreetingMessage = $x("//strong[contains(text(), 'Welcome')]").as("Приветственная надпись");
    private final List<SelenideElement> tableCheck = $$x("//table/tbody").as("Тело таблицы");
    private final List<SelenideElement> tableRow = $$x("//table/tbody/tr").as("Строки таблицы");

    //Этот универсальный локатор не нарушает POM и справляется со своей целью
    private SelenideElement tableRow(String amount, String type) {
        return $x("//td[text()=" + amount + "]/following::td[text()='" + type + "']");
    }

    @Step("Проверить, что заголовок страницы присутствует")
    public void checkTitle() {
        mainPageGreetingMessage.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Нажать на кнопку \"Deposit\"")
    public void depositButtonClick() {
        depositButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Пополнить баланс")
    public void depositBalance(String amount) {
        depositAmountFieldLabel.shouldBe(Condition.visible, Duration.ofSeconds(10));
        amountField.shouldBe(Condition.visible, Duration.ofSeconds(10)).setValue(amount);
        depositInFormButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить сообщение об успешном пополнении")
    public void checkSuccessDepositMessage() {
        successDepositMessage.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Нажать кнопку \"Withdrawl\"")
    public void withdrawlButtonClick() {
        withdrawlButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Сделать списание с баланса")
    public void withdrawBalance(String amount) {
        withdrawAmountFieldLabel.shouldBe(Condition.visible, Duration.ofSeconds(10));
        amountField.shouldBe(Condition.visible, Duration.ofSeconds(10)).setValue(amount);
        withdrawInFormButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить сообщение об успешном списании")
    public void checkSuccessWithdrawMessage() {
        successWithdrawMessage.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверить, что баланс равен нулю")
    public void checkBalanceIsZero() {
        balanceElement.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Нажать на кнопку Transactions")
    public void transactionButtonClick() {
        transactionButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    @Step("Проверить транзакции")
    public void checkTransactions(String amount) {
        if (tableCheck.isEmpty()) {
            Selenide.refresh();
            transactionButtonClick();
        }
        tableRow(amount, "Credit").shouldBe(Condition.visible, Duration.ofSeconds(10));
        tableRow(amount, "Debit").shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Сохраняем транзакции в csv")
    public void saveTransactions() {
        tableRow.get(0).shouldBe(Condition.visible, Duration.ofSeconds(10));
        Utils.makeCsv(tableRow);
    }

}
