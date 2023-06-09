package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.WithText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    String generateDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String data = generateDate();
    @Test
    void shouldCardDeliveryOrder() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Томск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(data);
        $("[data-test-id = 'name'] input").setValue("Валькевич Елизавета");
        $("[data-test-id = 'phone'] input").setValue("+79138119144");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $("[class = 'button__content']").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));

    }
}
