package basetest;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import pojo.order.OrderRqBody;

import java.util.List;

import static constant.ConstantIngredients.*;
import static constant.ConstantUrl.*;
import static testdata.TestDataProvider.*;

/**
 * Базовый класс для тестов API /orders
 */
public class OrdersBaseTest extends UserBaseTest {
    protected OrderRqBody orderRqBody = new OrderRqBody();

    @Step("Создаём тестовые данные перед выполнением теста")
    @BeforeEach
    void setUp() {
        super.setUp();
        orderRqBody = orderRqBody.toBuilder()
                .ingredients(List.of(INGREDIENT_BUN_R2_D3, INGREDIENT_MEAT_PROTOSTOMIA, INGREDIENT_SAUCE_SPICY_X))
                .build();
        Allure.step("Создаём тестовый заказ: " + orderRqBody.toString());
    }

    /**
     * Метод вызывается без токена пользователя и создаёт заказ с существующими ингридиентами
     */
    public void createOrder() {
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается без токена пользователя и создаёт заказ с несуществующими ингридиентами
     */
    public void createOrderWrongIngredients() {
        orderRqBody.setIngredients(List.of(getRandomUuid()));
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается без токена пользователя и создаёт заказ с пустым списком ингридиентов
     */
    public void createOrderNullIngredients() {
        orderRqBody.setIngredients(null);
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается с токеном пользователя и создаёт заказ с существующими ингридиентами
     */
    public void createOrderAuthorised() {
        createUser();
        response = httpManager.httpPost(URL_ORDERS, orderRqBody, userToken);
    }

    /**
     * Метод получения списка заказов конкретного пользоветеля вызывается без токена пользователя
     */
    public void getUserListUnauthorised() {
        response = httpManager.httpGet(URL_ORDERS);
    }

    /**
     * Метод получения списка заказов конкретного пользоветеля вызывается с токеном пользователя
     */
    public void getUserListAuthorised() {
        createOrderAuthorised();
        response = httpManager.httpGet(URL_ORDERS, userToken);
    }
}
