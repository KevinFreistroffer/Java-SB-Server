package com.example.api.defs;

// // To get the code value
// int code = StatusCodes.USER_NOT_FOUND.getCode();

// // To compare status codes
// if (status == StatusCodes.SUCCESS) {
//     // handle success
// }

public enum HttpErrorCodes {
    USER_NOT_FOUND(1000),
    USERS_NOT_FOUND(1001),
    RESOURCE_NOT_FOUND(1002),
    INVALID_USERNAME_OR_EMAIL_AND_PASSWORD(1003),
    INVALID_PASSWORD(1004),
    INVALID_REQUEST(1005),
    MISSING_PARAMETERS(1006),
    MISSING_BODY_FIELDS(1007),
    ACCESS_DENIED(1008),
    USERNAME_OR_EMAIL_ALREADY_REGISTERED(1010),
    USERNAME_AVAILABLE(1011),
    EMAIL_AVAILABLE(1012),
    ERROR_INSERTING_USER(1013),
    COULD_NOT_CREATE(1014),
    RESOURCE_EXISTS(1015),
    SUCCESS(2000);

    private final int code;

    HttpErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
