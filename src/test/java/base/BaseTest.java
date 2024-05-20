package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static WebDriver driver;
    private static final int TIMOUT = 15;

    @BeforeEach
    public void setUp(){
        Configuration.remote="http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    public static void switchTo(int window){
        Set<String> tabs = driver.getWindowHandles();
        ArrayList<String> tabsArray = new ArrayList<>(tabs);
        driver.switchTo().window(tabsArray.get(window));
    }

    public static void switchTo(String pagePath){
        Set<String> tabs = driver.getWindowHandles();
        for(String tab : tabs){
            driver.switchTo().window(tab);
            if(driver.getCurrentUrl().startsWith(pagePath)){
                return;
            }
        }
        throw new RuntimeException("Вкладка не найдена");
    }

    public static boolean checkCookieContains(String key, String value){
        return driver.manage().getCookieNamed(key).getValue().contains(value);
    }

    public static boolean checkCookieEquals(String key, String value){
        return driver.manage().getCookieNamed(key).getValue().equals(value);
    }

}
