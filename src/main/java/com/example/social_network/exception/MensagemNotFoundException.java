package com.example.social_network.exception;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String mensagem) {
        super(mensagem);
    }
}
