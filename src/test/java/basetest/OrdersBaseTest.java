package basetest;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import pojo.order.OrderRqBody;

import java.util.List;

import static constant.ConstantIngredients.*;
import static constant.ConstantUrl.*;
import static testdata.TestDataProvider.*;

public class OrdersBaseTest extends UserBaseTest {
    protected OrderRqBody orderRqBody = new OrderRqBody();

    @Step("Создаём тестовые данные перед выполнением теста")
    @BeforeEach
    void setUp() {
        //TODO убрать логирование по завершение
        super.setUp();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        orderRqBody = orderRqBody.toBuilder()
                .ingredients(List.of(INGREDIENT_BUN_R2_D3, INGREDIENT_MEAT_PROTOSTOMIA, INGREDIENT_SAUCE_SPICY_X))
                .build();
    }

    /**
     * Метод вызывается без токена пользователся и создаёт заказ с существующими ингридиентами
     */
    public void createOrder() {
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается без токена пользователся и создаёт заказ с несуществующими ингридиентами
     */
    public void createOrderWrongIngredients() {
        orderRqBody.setIngredients(List.of(getRandomUuid()));
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается без токена пользователся и создаёт заказ с пустым списком ингридиентов
     */
    public void createOrderNullIngredients() {
        orderRqBody.setIngredients(null);
        response = httpManager.httpPost(URL_ORDERS, orderRqBody);
    }

    /**
     * Метод вызывается без токена пользователся и создаёт заказ с существующими ингридиентами
     */
    public void createOrderAuthorised() {
        createUser();
        response = httpManager.httpPost(userToken, URL_ORDERS, orderRqBody);
    }
}
