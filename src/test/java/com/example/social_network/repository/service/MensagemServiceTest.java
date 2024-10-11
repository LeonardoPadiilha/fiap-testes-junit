package com.example.social_network.repository.service;

import com.example.social_network.exception.MensagemNotFoundException;
import com.example.social_network.model.Mensagem;
import com.example.social_network.repository.MensagemRepository;
import com.example.social_network.service.MensagemService;
import com.example.social_network.service.impl.MensagemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        //arrange
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(id)).thenReturn(Optional.of(mensagem));

        //act
        var mensagemObtida = mensagemService.buscarMensagem(id);

        //assert
        assertThat(mensagemObtida).isEqualTo(mensagem);
        verify(mensagemRepository, times(1)).findById(id);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarMensagem_IdNaoExiste(){
        var id = UUID.randomUUID();
        when(mensagemRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mensagemService.buscarMensagem(id))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem não encontrada");
        verify(mensagemRepository, times(1)).findById(id);
    }

    @Test
    void devePermitirAlterarMensagem(){
        //arrange
        var id = UUID.randomUUID();
        var mensagemAntiga = gerarMensagem();
        mensagemAntiga.setId(id);

        var mensagemNova =  new Mensagem();
        mensagemNova.setId(mensagemAntiga.getId());
        mensagemNova.setUsuario(mensagemAntiga.getUsuario());
        mensagemNova.setConteudo("Novo conteudo");

        when(mensagemRepository.findById(id)).thenReturn(Optional.of(mensagemAntiga));
        when(mensagemRepository.save(mensagemNova)).thenAnswer(i -> i.getArgument(0));

        //act
        var mensagemObtida = mensagemService.alterarMensagem(id, mensagemNova);

        //assert
        assertThat(mensagemObtida).isInstanceOf(Mensagem.class).isNotNull();
        assertThat(mensagemObtida).isEqualTo(mensagemNova);
        verify(mensagemRepository, times(1)).findById(id);
        verify(mensagemRepository, times(1)).save(mensagemNova);
    }

    @Test
    void deveGerarExcecao_QuandoAlterarMensagem_IdNaoApresentaMensagem(){
        //arrange
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(id)).thenReturn(Optional.empty());

        //act & assert
        assertThatThrownBy(() -> mensagemService.alterarMensagem(id, mensagem))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem não encontrada");
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
