package com.example.social_network.repository.service;

import com.example.social_network.repository.MensagemRepository;
import com.example.social_network.service.MensagemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
public class MensagemServiceIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private MensagemService mensagemService;

    @Test
    void devePermitirRegistrarMensagem(){
        fail("Not yet implemented");
    }

    @Test
    void devePermitirBuscarMensagem(){
        fail("Not yet implemented");
    }

    @Test
    void deveGerarExcecao_QuandoBuscarMensagem_IdNaoExiste(){
        fail("Not yet implemented");
    }

    @Test
    void devePermitirAlterarMensagem(){
        fail("Not yet implemented");
    }

    @Test
    void deveGerarExcecao_QuandoAlterarMensagem_IdNaoExiste(){
        fail("Not yet implemented");
    }

    @Test
    void deveGerarExcecao_QuandoAlterarMensagem_IdDaMensagemNovaApresentaValorDiferente(){
        fail("Not yet implemented");
    }

    @Test
    void devePermitirRemoverMensagem(){
        fail("Not yet implemented");
    }

    @Test
    void deveGerarExcecao_QuandoRemoverMensagem_IdNaoExiste(){
        fail("Not yet implemented");
    }

    @Test
    void devePermitirListarMensagens(){
        fail("Not yet implemented");
    }
}
