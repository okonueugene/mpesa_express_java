package com.mpesa_express.mpesa_express_java.controller;

public class AccessTokenException extends RuntimeException {
    public AccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
