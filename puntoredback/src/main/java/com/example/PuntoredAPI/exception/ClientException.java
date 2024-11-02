package com.example.PuntoredAPI.exception;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}