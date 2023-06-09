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
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + data), Duration.ofSeconds(15));
    }

    @Test
    void shouldIfCityIsNotInListAdministrativeEntities() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Асино");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(data);
        $("[data-test-id = 'name'] input").setValue("Валькевич Елизавета");
        $("[data-test-id = 'phone'] input").setValue("+79138119144");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $("[class = 'button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldIfFieldNameIsFilledInLatin() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Томск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(data);
        $("[data-test-id = 'name'] input").setValue("Valkevich Elizaveta");
        $("[data-test-id = 'phone'] input").setValue("+79138119144");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $("[class = 'button__content']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldIfNoPlusInPhoneField() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Томск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(data);
        $("[data-test-id = 'name'] input").setValue("Валькевич Елизавета");
        $("[data-test-id = 'phone'] input").setValue("79138119144");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $("[class = 'button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldIfDoNotCheckTheCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Томск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(data);
        $("[data-test-id = 'name'] input").setValue("Валькевич Елизавета");
        $("[data-test-id = 'phone'] input").setValue("+79138119144");
        $("[class = 'button__content']").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void shouldDateTest() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Томск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id = 'name'] input").setValue("Валькевич Елизавета");
        $("[data-test-id = 'phone'] input").setValue("+79138119144");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $("[class = 'button__content']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));

    }



}
