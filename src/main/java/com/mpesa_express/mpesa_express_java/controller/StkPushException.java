package com.mpesa_express.mpesa_express_java.controller;

public class StkPushException extends RuntimeException {
    public StkPushException(String message, Exception e) {
        super(message);
    }

}
