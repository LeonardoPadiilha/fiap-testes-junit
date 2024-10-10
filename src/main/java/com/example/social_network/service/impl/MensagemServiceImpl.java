package com.example.social_network.service.impl;

import com.example.social_network.model.Mensagem;
import com.example.social_network.repository.MensagemRepository;
import com.example.social_network.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemServiceImpl  implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Override
    public Mensagem registrarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem buscarMensagem(UUID id) {
        return null;
    }

    @Override
    public Mensagem alterarMensagem(Mensagem mensagemAtual, Mensagem mensagemNova) {
        return null;
    }

    @Override
    public boolean removerMensagem(UUID id) {
        return false;
    }
}
