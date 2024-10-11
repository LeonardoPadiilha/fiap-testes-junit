package com.example.social_network.service;

import com.example.social_network.model.Mensagem;

import java.util.UUID;

public interface MensagemService {

    Mensagem registrarMensagem(Mensagem mensagem);

    Mensagem buscarMensagem(UUID id);

    Mensagem alterarMensagem(UUID id, Mensagem mensagemNova);

    boolean removerMensagem(UUID id);
}
