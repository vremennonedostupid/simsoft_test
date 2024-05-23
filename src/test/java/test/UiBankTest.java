package test;

import base.BaseTest;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import page_object.AuthorizationPage;
import page_object.MainPage;
import util.Utils;

import java.time.LocalDate;

public class UiBankTest extends BaseTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    Integer amount = Utils.fibonacciCount(LocalDate.now().getDayOfMonth());

    @Test
    public void transactionTest(){
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        authorizationPage.customerLoginButtonClick();
        authorizationPage.selectCustomerClick();
        authorizationPage.selectCustomerChoose("Harry Potter");
        authorizationPage.loginButtonClick();
        mainPage.checkTitle();
        mainPage.depositButtonClick();
        mainPage.depositBalance(amount.toString());
        mainPage.checkSuccessDepositMessage();
        mainPage.withdrawlButtonClick();
        mainPage.withdrawBalance(amount.toString());
        mainPage.checkSuccessWithdrawMessage();
        mainPage.checkBalanceIsZero();
        mainPage.transactionButtonClick();
        mainPage.checkTransactions(amount.toString());
        mainPage.saveTransactions();
    }

}
