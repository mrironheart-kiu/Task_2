package basetest;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pojo.user.*;
import testdata.TestDataProvider;
import util.HttpManager;

import java.util.ArrayList;
import java.util.List;

import static constant.ConstantUrl.*;

/**
 * Базовый класс для тестов API управления пользователем /register, /login и /user
 */
@Getter
public class UserBaseTest extends BaseTest {
    protected UserRqBody userRqBody = new UserRqBody();
    protected String userToken;
    protected final List<String> userTokenList = new ArrayList<>();

    @Step("Создаём тестовые данные перед выполнением теста")
    @BeforeEach
    void setUp() {
        httpManager = new HttpManager(URL_BASE);
        userRqBody = userRqBody.toBuilder()
                .email(TestDataProvider.getRandomEmail())
                .password(TestDataProvider.getRandomPassword())
                .name(TestDataProvider.getRandomName())
                .build();
        Allure.step("Устанавливаем URL по умолчанию: " + URL_BASE);
        Allure.step("Создаём тестового клиента: " + userRqBody.toString());
    }

    @Step("Очищаем тестовые данные после выполнения теста")
    @AfterEach
    void tearDown() {
        deleteUser(userTokenList);
    }

    /**
     * Метод создаёт пользователя со случайными данными
     */
    public void createUser() {
        response = httpManager.httpPost(URL_USER_REGISTER, userRqBody);

        if (response.body().as(UserRsBody.class).getAccessToken() != null) {
            userToken = response.body().as(UserRsBody.class).getAccessToken().split(" ")[1];
            userTokenList.add(userToken);
        }
    }

    /**
     * Метод создаёт пользователя с указанными данными
     */
    public void createUser(UserRqBody userRqBody) {
        response = httpManager.httpPost(URL_USER_REGISTER, userRqBody);
    }

    /**
     * Метод авторизируется созданным случайным пользователем
     */
    public void loginUser() {
        createUser();
        response = httpManager.httpPost(URL_USER_LOGIN, userRqBody);
    }

    /**
     * Метод авторизируется с указанными данными
     */
    public void loginUser(UserRqBody userRqBody) {
        response = httpManager.httpPost(URL_USER_LOGIN, userRqBody);
    }

    /**
     * Метод обновляет имя пользователя случайными данными
     */
    public void updateUserName() {
        createUser();
        userRqBody.setName(TestDataProvider.getRandomName());
        response = httpManager.httpPatch(URL_USER, userRqBody, userToken);
    }

    /**
     * Метод обновляет email адрес пользователя случайными данными
     */
    public void updateUserEmail() {
        createUser();
        userRqBody.setEmail(TestDataProvider.getRandomEmail());
        response = httpManager.httpPatch(URL_USER, userRqBody, userToken);
    }

    /**
     * Метод обновляет email адрес пользователя случайными данными
     */
    public void updateUserEmailDuplicate() {
        createUser();
        String existUserEmail = userRqBody.getEmail();
        userRqBody = userRqBody.toBuilder()
                .email(TestDataProvider.getRandomEmail())
                .password(TestDataProvider.getRandomPassword())
                .name(TestDataProvider.getRandomName())
                .build();
        createUser();
        userRqBody.setEmail(existUserEmail);
        response = httpManager.httpPatch(URL_USER, userRqBody, userToken);
    }

    /**
     * Метод обновляет имя и email адрес пользователя случайными данными
     */
    public void updateUserNameAndEmail() {
        createUser();
        userRqBody.setName(TestDataProvider.getRandomName());
        userRqBody.setEmail(TestDataProvider.getRandomEmail());
        response = httpManager.httpPatch(URL_USER, userRqBody, userToken);
    }

    /**
     * Метод выполняет запрос на обновление атрибутов пользователя без токена авторизации
     */
    public void updateUserUnauthorised(UserRqBody userRqBody) {
        response = httpManager.httpPatch(URL_USER, userRqBody);
    }

    /**
     * Метод удаялет пользователя по его токену
     */
    public void deleteUser(List<String> userTokenList) {
        if (!userTokenList.isEmpty()) {
            for (String userToken : userTokenList) {
                httpManager.httpDelete(URL_USER, userToken);
            }
        }
    }
}
