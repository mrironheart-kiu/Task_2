package pojo.order;

import lombok.Getter;

/**
 * Класс для обработки тела ответа API создания заказа POST /orders
 */
@Getter
public class OrderCreateRsBody {
    private Boolean success;
    private String message;
    private String name;
    private Order order;

    @Getter
    private static class Order {
        private Integer number;
    }
}
