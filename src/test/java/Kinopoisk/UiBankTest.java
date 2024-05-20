package Kinopoisk;

import base.BaseTest;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import page_object.AuthorizationPage;
import page_object.MainPage;

public class UiBankTest extends BaseTest {

    AuthorizationPage authorizationPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();

    @Test
    public void transactionTest(){
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        authorizationPage.buttonClick("Customer Login");
        authorizationPage.selectCustomerClick();
        authorizationPage.selectCustomerChoose("Harry Potter");
        authorizationPage.buttonClick("Login");
        mainPage.checkTitle();
        mainPage.depositButtonClick();
        mainPage.depositBalance();
        mainPage.checkSuccessDepositMessage();
        mainPage.withdrawlButtonClick();
        mainPage.withdrawBalance();
        mainPage.checkSuccessWithdrawMessage();
        mainPage.checkBalanceIsZero();
        mainPage.transactionButtonClick();
        mainPage.checkTransactions();
        mainPage.saveTransactions();
    }

}
