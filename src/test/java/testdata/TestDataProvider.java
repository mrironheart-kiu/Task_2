package testdata;

import com.github.javafaker.Faker;

/**
 * Класс для формирования тестовых данных
 */
public class TestDataProvider {
    private static final Faker FAKER = new Faker();

    /**
     * Метод возвращает случайное имя из генератора JavaFaker
     *
     * @return String
     */
    public static String getRandomName() {
        return FAKER.name().name();
    }

    /**
     * Метод возвращает случайный пароль из генератора JavaFaker
     *
     * @return String
     */
    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    /**
     * Метод возвращает случайный email адрес из генератора JavaFaker
     *
     * @return String
     */
    public static String getRandomEmail() {
        return FAKER.internet().emailAddress();
    }

    /**
     * Метод возвращает случайный UUID из генератора JavaFaker
     *
     * @return String
     */
    public static String getRandomUuid() {
        return FAKER.internet().uuid().replace("-", "");
    }
}
