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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        var id = UUID.fromString("d88346de-8ad5-47f8-b8f1-ea5254a15d07");
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
        var id = UUID.fromString("ebf82e7d-40f4-4646-8463-45b85daebdd0");
        when(mensagemRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mensagemService.buscarMensagem(id))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem não encontrada");
        verify(mensagemRepository, times(1)).findById(id);
    }

    @Test
    void devePermitirAlterarMensagem(){
        //arrange
        var id = UUID.fromString("e49e258d-ded3-46ba-9c38-e95fc4cb77a2");
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
    void deveGerarExcecao_QuandoAlterarMensagem_IdNaoExiste(){
        //arrange
        var id = UUID.fromString("bf1a9e18-d29b-4a70-b766-a72f25587829");
        var mensagem = gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(id)).thenReturn(Optional.empty());

        //act & assert
        assertThatThrownBy(() -> mensagemService.alterarMensagem(id, mensagem))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem não encontrada");
        verify(mensagemRepository, times(1)).findById(id);
        verify(mensagemRepository, never()).save(any(Mensagem.class));
    }

    @Test
    void deveGerarExcecao_QuandoAlterarMensagem_IdDaMensagemNovaApresentaValorDiferente(){
        //arrange
        var id = UUID.fromString("2f87b1e1-4b18-4283-97ef-fccfa0dfeb8a");
        var mensagemaAntiga = gerarMensagem();
        mensagemaAntiga.setId(UUID.fromString("d1ffcaba-3cef-48cd-8123-35ae28bfca98"));

        var mensagemNova = gerarMensagem();
        mensagemNova.setId(UUID.fromString("dac24108-2430-4876-940d-7d83f38b419f"));
        mensagemNova.setConteudo("Novo conteudo");

        when(mensagemRepository.findById(id)).thenReturn(Optional.of(mensagemaAntiga));

        //act & assert
        assertThatThrownBy(() -> mensagemService.alterarMensagem(id, mensagemNova))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem atualizada nao apresenta o ID correto");
        verify(mensagemRepository, times(1)).findById(id);
        verify(mensagemRepository, never()).save(any(Mensagem.class));
  }

  @Test
  void devePermitirRemoverMensagem(){
        //arrange
        var id = UUID.fromString("7ad1c889-ee4b-4e77-b7db-1510e4516a62");
        var mensagem = gerarMensagem();
        when(mensagemRepository.findById(id)).thenReturn(Optional.of(mensagem));
        doNothing().when(mensagemRepository).deleteById(id);

        //act
      var mensagemFoiRemovida = mensagemService.removerMensagem(id);

      //assert
      assertThat(mensagemFoiRemovida).isTrue();
      verify(mensagemRepository, times(1)).findById(id);
      verify(mensagemRepository, times(1)).deleteById(id);
  }

    @Test
    void deveGerarExcecao_QuandoRemoverMensagem_IdNaoExiste(){
        //arrange
        var id = UUID.fromString("bd7de67c-0743-4eec-ab0d-45acc43f1dc0");
        when(mensagemRepository.findById(id)).thenReturn(Optional.empty());

        //act & assert
        assertThatThrownBy(() -> mensagemService.removerMensagem(id))
                .isInstanceOf(MensagemNotFoundException.class)
                .hasMessage("Mensagem não encontrada");
        verify(mensagemRepository, times(1)).findById(id);
        verify(mensagemRepository, never()).deleteById(id);
    }

    @Test
    void devePermitirListarMensagens(){
        //arrange
        Page<Mensagem> listaDeMensagem = new PageImpl<>(Arrays.asList(gerarMensagem(), gerarMensagem()));
        when(mensagemRepository.listarMensagens(any(Pageable.class))).thenReturn(listaDeMensagem);

        //act
        var resultadoObtido = mensagemService.listarMensagens(Pageable.unpaged());

        //assert
        assertThat(resultadoObtido).hasSize(2);
        assertThat(resultadoObtido.getContent()).asList().allSatisfy(mensagem -> {
            assertThat(mensagem)
                    .isNotNull()
                    .isInstanceOf(Mensagem.class);
        });
        verify(mensagemRepository, times(1)).listarMensagens(any(Pageable.class));
    }

    private Mensagem gerarMensagem(){
        return Mensagem.builder().usuario("Jose").conteudo("Alguma coisa").build();
    }
}
