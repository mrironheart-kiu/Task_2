package testdata;

import org.junit.jupiter.params.provider.Arguments;
import pojo.user.UserRqBody;

import java.util.stream.Stream;

import static constant.ConstantErrorMessage.*;

public class UserParameterizedTestData extends TestDataProvider {
    private static final UserRqBody USER_RQ_BODY = new UserRqBody();

    private static Stream<Arguments> userRegisterTestData() {
        return Stream.of(
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(null)
                                .password(getRandomPassword())
                                .name(getRandomName())
                                .build(),
                        ERROR_USER_CREATE_NULL_CREDENTIALS
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .password(null)
                                .name(getRandomName())
                                .build(),
                        ERROR_USER_CREATE_NULL_CREDENTIALS
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .password(getRandomPassword())
                                .name(null)
                                .build(),
                        ERROR_USER_CREATE_NULL_CREDENTIALS
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(null)
                                .password(null)
                                .name(null)
                                .build(),
                        ERROR_USER_CREATE_NULL_CREDENTIALS
                )
        );
    }

    private static Stream<Arguments> userLoginTestData() {
        return Stream.of(
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .password(getRandomPassword())
                                .build(),
                        ERROR_USER_LOGIN_NULL_CREDENTIALS
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(null)
                                .password(getRandomPassword())
                                .build(),
                        ERROR_USER_LOGIN_NULL_CREDENTIALS
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .password(null)
                                .build(),
                        ERROR_USER_LOGIN_NULL_CREDENTIALS
                )
        );
    }

    private static Stream<Arguments> userUpdateUnauthorisedTestData() {
        return Stream.of(
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .name(getRandomName())
                                .build(),
                        ERROR_USER_UNAUTHORIZED
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(null)
                                .name(getRandomName())
                                .build(),
                        ERROR_USER_UNAUTHORIZED
                ),
                Arguments.of(USER_RQ_BODY.toBuilder()
                                .email(getRandomEmail())
                                .name(null)
                                .build(),
                        ERROR_USER_UNAUTHORIZED
                )
        );
    }
}
