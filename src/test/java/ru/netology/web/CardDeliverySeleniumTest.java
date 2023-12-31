package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliverySeleniumTest {
    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        open("http://localhost:9999");
    }

    public String generateTestDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format((DateTimeFormatter.ofPattern(pattern)));
    }

    @Test
    void shouldSendFormOfTest() {
        String planningDate = generateTestDate(4, "dd.MM.yyyy");

        $("[data-test-id=city] input").sendKeys("Омск");
        $("[data-test-id=city] input").shouldBe(Condition.value("Омск"));

        $(By.cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=date] input")).shouldBe(Condition.value(planningDate));

        $("[data-test-id=name] input").sendKeys("Сергей Смирнов-Петров");
        $("[data-test-id=name] input").shouldBe(Condition.value("Сергей Смирнов-Петров"));

        $("[data-test-id=phone] input").sendKeys("+79061779689");
        $("[data-test-id=phone] input").shouldBe(Condition.value("+79061779689"));

        $("[data-test-id=agreement]").click();
        $("[data-test-id=agreement] input").shouldBe(Condition.selected);

        $("div.grid-col button.button_view_extra").click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
}