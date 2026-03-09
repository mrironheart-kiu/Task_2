import assertstep.AssertStep;
import basetest.OrdersBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.order.*;
import pojo.user.*;

import static constant.ConstantErrorMessage.ERROR_ORDERS_EMPTY_INGREDIENTS_LIST;
import static constant.ConstantStoryTitle.TITLE_ORDERS_CREATE;
import static constant.ConstantUrl.URL_ORDERS;

public class OrdersCreateTest extends OrdersBaseTest {

    @Test
    @Story(TITLE_ORDERS_CREATE)
    @DisplayName("POST на " + URL_ORDERS + ". Успешное создание нового заказа без авторизации")
    @Description("Создаём новый заказ через вызов API " + URL_ORDERS)
    void ordersCreateUnauthorisedSuccessTest() {
        createOrder();

        response.then().assertThat().statusCode(200);
        AssertStep.checkBodyStatus(true, response.body().as(OrderCreateRsBody.class).getSuccess());
    }

    @Test
    @Story(TITLE_ORDERS_CREATE)
    @DisplayName("POST на " + URL_ORDERS + ". Успешное создание нового заказа c авторизацией")
    @Description("Создаём новый заказ через вызов API " + URL_ORDERS)
    void ordersCreateAuthorisedSuccessTest() {
        createOrderAuthorised();

        response.then().assertThat().statusCode(200);
        AssertStep.checkBodyStatusAndAttribute(
                true, response.body().as(OrderGetRsBody.class).getSuccess(),
                userRqBody.getEmail(), response.body().as(OrderCreateRsBody.class).getOrder().getOwner().getEmail());
    }

    @Test
    @Story(TITLE_ORDERS_CREATE)
    @DisplayName("POST на " + URL_ORDERS + ". Невозможно создать заказ c указанием несуществующих ингридиентов")
    @Description("Выполняем попытку создания нового заказа через вызов API " + URL_ORDERS)
    void ordersCreateWrongIngredientsTest() {
        createOrderWrongIngredients();

        response.then().assertThat().statusCode(500);
    }

    @Test
    @Story(TITLE_ORDERS_CREATE)
    @DisplayName("POST на " + URL_ORDERS + ". Невозможно создать заказ без указания существующих ингридиентов")
    @Description("Выполняем попытку создания нового заказа через вызов API " + URL_ORDERS)
    void ordersCreateNullIngredientsTest() {
        createOrderNullIngredients();

        response.then().assertThat().statusCode(400);
        AssertStep.checkBodyStatusAndAttribute(
                false, response.body().as(OrderCreateRsBody.class).getSuccess(),
                ERROR_ORDERS_EMPTY_INGREDIENTS_LIST, response.body().as(OrderCreateRsBody.class).getMessage());
    }
}
