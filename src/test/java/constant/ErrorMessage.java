package constant;

/**
 * Класс для хранения переменных, содержащих тексты ошибок API Courier
 */
public class ErrorMessage {
    public static final String ERROR_USER_CREATE_DUPLICATE = "User already exists";
    public static final String ERROR_USER_CREATE_NULL_CREDENTIALS = "Email, password and name are required fields";
    public static final String ERROR_USER_LOGIN_NULL_CREDENTIALS = "email or password are incorrect";
    public static final String ERROR_USER_UPDATE_DUPLICATE = "User with such email already exists";
    public static final String ERROR_USER_UNAUTHORIZED = "You should be authorised";

    public static final String ERROR_ORDERS_EMPTY_INGREDIENTS_LIST = "Ingredients ids must be provided";
}
