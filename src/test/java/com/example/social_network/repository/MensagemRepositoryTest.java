package com.example.social_network.repository;

import com.example.social_network.model.Mensagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.*;

public class MensagemRepositoryTest {

    @Mock
    private MensagemRepository mensagemRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarMensagem(){
        var mensagem = gerarMensagem();

        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        var mensagemRegistrada = mensagemRepository.save(mensagem);

        assertThat(mensagemRegistrada).isNotNull().isEqualTo(mensagem);
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));
    }

    @Test
    void devePermitirBuscarMensagem(){
        //arrange
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);

        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        //act
        var mensagemRecebidaOpcional = mensagemRepository.findById(id);

        //assert
        assertThat(mensagemRecebidaOpcional).isPresent().containsSame(mensagem);

        mensagemRecebidaOpcional.ifPresent(mensagemRecebida -> {
            assertThat(mensagemRecebida.getId()).isEqualTo(mensagem.getId());
            assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());
        });
        verify(mensagemRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void devePermitirExcluirMensagem(){
        //arrange
        var id = UUID.randomUUID();
        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        //act
        mensagemRepository.deleteById(id);

        //assert
        verify(mensagemRepository, times(1)).deleteById(any(UUID.class));
    }

    private Mensagem gerarMensagem(){
        return Mensagem.builder().usuario("Jose").conteudo("Algouma coisa").build();
    }
}
