package com.example.social_network.service.impl;

import com.example.social_network.exception.MensagemNotFoundException;
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
        return mensagemRepository.findById(id).orElseThrow(() -> new MensagemNotFoundException("Mensagem não encontrada"));
    }

    @Override
    public Mensagem alterarMensagem(UUID id, Mensagem mensagemAtualizada) {
        var mensagem = buscarMensagem(id);
        if(!mensagem.getId().equals(mensagemAtualizada.getId())){
            throw new MensagemNotFoundException("Mensagem atualizada nao apresenta o ID correto");
        }
        mensagem.setConteudo(mensagemAtualizada.getConteudo());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public boolean removerMensagem(UUID id) {
        return false;
    }
}
