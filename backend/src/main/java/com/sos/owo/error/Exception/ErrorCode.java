package com.sos.owo.error.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    OK(200,"C000", "OK"),
    INVALID_INPUT_VALUE(400, "C001", "INVALID_INPUT_VALUE"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(404, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    UNAUTHORIZED(401, "C007", "UnAuthorized"),

    //User
    EMAIL_DUPLICATION(409, "U001", "Email is Duplication"),
    AUTH_EMAIL_SEND_FAIL(500, "U002", "Auth Email Send Fail"),
    NICKNAME_DUPLICATION(409, "U003", "Nickname is Duplication"),
    CURRENT_PASSWORD_NOT_MATCH_EXCEPTION(400, "U004", "Current password not match exception"),
    LOGIN_DUPLICATION(409, "U005", "Current user is logined. You can not login to this account"),


    //Room
    ROOM_IS_NOT_AVAILABLE(409, "R001", "Room Status is Not Available"),
    ROOM_IS_FULL(409, "R001", "Room is Full"),
    ROOM_IS_ON_GAME(409, "R002", "Room is on Game"),
    ROOM_IS_OFFLINE(404, "R003", "Room is OFF"),
    ROOM_PASSWORD_NOT_MATCH(400, "R004", "Room password is Not Match"),


    JWT_TOKEN_EXPIRED(401,"T001","JWT Token is expired");
    //Record

    //Goal
//    GOAL_NOT_EXIST(400,"G001",)




    private int status;
    private final String code;
    private final String message;


    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
