package util;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Вспомогательный класс для вызова API
 */
public class HttpManager {
    private static final int DEFAULT_TIME_IN_MILLISECONDS = 5000;

    public HttpManager(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    /**
     * Метод выставляет таймауты по умолчанию для HTTP методов
     *
     * @return объект RestAssuredConfig, который используется для вызова HTTP методов
     */
    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", DEFAULT_TIME_IN_MILLISECONDS)           // Connection timeout
                        .setParam("http.socket.timeout", DEFAULT_TIME_IN_MILLISECONDS)               // Read timeout
                        .setParam("http.connection-manager.timeout", DEFAULT_TIME_IN_MILLISECONDS)  // Connection request timeout
        );
    }

    /**
     * Вызов метода GET
     *
     * @param path Путь до API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов GET на метод {0}")
    public Response httpGet(String path) {
        return given()
                .contentType(ContentType.JSON)
                .get(path);
    }

    /**
     * Вызов метода GET с токеном пользователя
     *
     * @param userToken Токен пользователя
     * @param path      Путь до API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов GET на метод {0} с токеном пользователя")
    public Response httpGet(String userToken, String path) {
        return userToken == null ? httpGet(path) :
                given().contentType(ContentType.JSON)
                        .and().auth().oauth2(userToken)
                        .get(path);
    }

    /**
     * Вызов метода POST
     *
     * @param path Путь до API
     * @param body Тело запроса API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов POST на метод {0}")
    public Response httpPost(String path, Object body) {
        return given().config(getConfig()).contentType(ContentType.JSON).and().body(body).when().post(path);
    }

    /**
     * Вызов метода POST с токеном пользователя
     *
     * @param userToken Токен пользователя
     * @param path      Путь до API
     * @param body      Тело запроса API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов POST на метод {0}")
    public Response httpPost(String userToken, String path, Object body) {
        return userToken == null ? httpPost(path, body) :
                given().config(getConfig()).contentType(ContentType.JSON)
                        .and().auth().oauth2(userToken)
                        .and().body(body)
                        .when().post(path);
    }

    /**
     * Вызов метода PATCH
     *
     * @param path Путь до API
     * @param body Тело запроса API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов PATCH на метод {0}")
    public Response httpPatch(String path, Object body) {
        return given().config(getConfig()).contentType(ContentType.JSON).and().body(body).when().patch(path);
    }

    /**
     * Вызов метода PATCH с токеном пользователя
     *
     * @param userToken Токен пользователя
     * @param path      Путь до API
     * @param body      Тело запроса API
     * @return Объект Response для дальнейшей обработки
     */
    @Step("Выполняем вызов PATCH на метод {0} с токеном пользователя")
    public Response httpPatch(String userToken, String path, Object body) {
        return userToken == null ? httpPatch(path, body) :
                given().config(getConfig())
                        .contentType(ContentType.JSON)
                        .and().auth().oauth2(userToken)
                        .and().body(body)
                        .when().patch(path);
    }

    /**
     * Вызов метода DELETE с токеном пользователя
     *
     * @param userToken Токен пользователя
     * @param path      Путь до API
     */
    @Step("Выполняем вызов DELETE на метод {0}")
    public void httpDelete(String userToken, String path) {
        given().config(getConfig()).auth().oauth2(userToken).delete(path);
    }
}
