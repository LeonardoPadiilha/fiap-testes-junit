package com.example.social_network.repository.service;

import com.example.social_network.model.Mensagem;
import com.example.social_network.repository.MensagemRepository;
import com.example.social_network.service.MensagemService;
import com.example.social_network.service.impl.MensagemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MensagemServiceTest {

    private MensagemService mensagemService;
    @Mock
    private MensagemRepository mensagemRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemService = new MensagemServiceImpl(mensagemRepository);
    }
    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem(){
        //arrange
        var mensagem = gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class))).thenAnswer(i -> i.getArgument(0));
        //act
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);
        //assert
        assertThat(mensagemRegistrada).isInstanceOf(Mensagem.class).isNotNull();
        assertThat(mensagemRegistrada.getConteudo()).isEqualTo(mensagem.getConteudo());
        assertThat(mensagemRegistrada.getUsuario()).isEqualTo(mensagem.getUsuario());
        assertThat(mensagem.getId()).isNotNull();
    }

    @Test
    void devePermitirBuscarMensagem(){
        fail("Teste nao implementado");
    }

    @Test
    void devePermitirAlterarMensagem(){
        fail("Teste nao implementado");
    }

    @Test
    void devePermitirRemoverMensagem(){
        fail("Teste nao implementado");
    }

    @Test
    void devePermitirListarMensagens(){
        fail("Teste nao implementado");
    }

    private Mensagem gerarMensagem(){
        return Mensagem.builder().usuario("Jose").conteudo("Alguma coisa").build();
    }
}
