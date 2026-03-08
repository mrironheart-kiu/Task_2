package basetest;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pojo.user.*;
import testdata.TestDataProvider;
import util.HttpManager;

import static constant.ConstantUrl.*;

public class UserBaseTest {
    private HttpManager httpManager;
    private UserRqBody userRqBody = new UserRqBody();
    protected Response response;

    @Step("Создаём тестовые данные перед выполнением теста")
    @BeforeEach
    void setUp() {
        httpManager = new HttpManager(URL_BASE);
        userRqBody = userRqBody.toBuilder()
                .email(TestDataProvider.getRandomEmail())
                .password(TestDataProvider.getRandomEmail())
                .name(TestDataProvider.getRandomName())
                .build();
    }

    @Step("Очищаем тестовые данные после выполнения теста")
    @AfterEach
    void tearDown() {
        if (response.body().as(UserRsBody.class).getAccessToken() != null) {
            httpManager.httpDelete(
                    URL_USER,
                    httpManager.httpPost(URL_USER_LOGIN, userRqBody)
                            .body().as(UserRsBody.class).getAccessToken().split(" ")[1]);
        }
    }

    public void createUser() {
        response = httpManager.httpPost(URL_USER_REGISTER, userRqBody);
    }

    public void createUser(UserRqBody userRqBody) {
        response = httpManager.httpPost(URL_USER_REGISTER, userRqBody);
    }
}
