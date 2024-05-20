package util;

import com.codeborne.selenide.SelenideElement;
import com.opencsv.CSVWriter;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public static void makeCsv(List<SelenideElement> rows) {
        String filePath = "table-data.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            for (SelenideElement row : rows) {
                List<SelenideElement> cells = row.$$(By.xpath("./td"));
                String[] cellValues = cells.stream().map(SelenideElement::getText).toArray(String[]::new);
                writer.writeNext(cellValues);
            }
            System.out.println("Data has been written to CSV file successfully.");
            String csvContent = new String(Files.readAllBytes(Paths.get(filePath)));
            Allure.addAttachment("data", csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int fibonacciCount(int number) {
        if (number <= 1) {
            return number;
        }
        return fibonacciCount(number - 1) + fibonacciCount(number - 2);
    }
}
