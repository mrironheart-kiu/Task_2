package basetest;

import io.restassured.response.Response;
import util.HttpManager;

/**
 * Базовый класс для тестов
 */
public class BaseTest {
    protected HttpManager httpManager;
    protected Response response;
}
