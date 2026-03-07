import basetest.UserBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.user.UserRsBody;

import static constant.ConstantStoryTitle.TITLE_USER_REGISTER;
import static constant.ConstantTestFailComment.DEFAULT_ERROR_COMMENT;
import static constant.ConstantUrl.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserCreateTest extends UserBaseTest {
    private Response response;

    @Test
    @Story(TITLE_USER_REGISTER)
    @DisplayName("POST на " + URL_USER_REGISTER + ". Успешная регистрация нового пользователя")
    @Description("Регистрируем нового пользователя через вызов API " + URL_USER_REGISTER)
    void userRegisterSuccessTest() {
        response = createUser();

        response.then().assertThat().statusCode(200);
        assertTrue(response.body().as(UserRsBody.class).getSuccess(), DEFAULT_ERROR_COMMENT);
    }

    @Test
    @Story(TITLE_USER_REGISTER)
    @DisplayName("POST на " + URL_USER_REGISTER + ". Невозможно создать двух одинаковых пользователей")
    @Description("Выполняем попытку создания пользователя с данными, " +
            "от уже существующего через вызов API " + URL_USER_REGISTER)
    void userRegisterDuplicateTest() {
        createUser();
        response = createUser();

        response.then().assertThat().statusCode(403);
        assertFalse(response.body().as(UserRsBody.class).getSuccess(), DEFAULT_ERROR_COMMENT);
    }
}
