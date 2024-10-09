package com.example.social_network.repository;

import com.example.social_network.model.Mensagem;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MensagemRepositoryIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Test
    void devePermitirCriarTabela(){
        var totalDeRegistros = mensagemRepository.count();
        assertThat(totalDeRegistros).isGreaterThan(0);
    }

    @Test
    void devePermitirRegistrarMensagem(){
        //arrange
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);

        //act
        var mensagemRecebida = mensagemRepository.save(mensagem);

        //assert
        assertThat(mensagemRecebida).isInstanceOf(Mensagem.class).isNotNull();
        assertThat(mensagemRecebida.getId()).isEqualTo(id);
        assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());
        assertThat(mensagemRecebida.getUsuario()).isEqualTo(mensagem.getUsuario());
    }
    @Test
    void devePermitirBuscarMensagem(){
        //arrange
        var id = UUID.fromString("b635d717-517b-4466-9822-1a5a1ebd3128");

        //act
        var mensagemRecebidaOptional = mensagemRepository.findById(id);

        //assert
        assertThat(mensagemRecebidaOptional).isPresent();
        mensagemRecebidaOptional.ifPresent(mensagemRecebida -> {
            assertThat(mensagemRecebida.getId()).isEqualTo(id);
        });
    }
    @Test
    void devePermitirRemoverMensagem(){
        //arrange
        var id = UUID.fromString("0f4f68f4-ce7a-48ef-9809-953481835bfd");

        //act
        mensagemRepository.deleteById(id);
        var mensagemRecebidaOptional = mensagemRepository.findById(id);

        //assert
        assertThat(mensagemRecebidaOptional).isEmpty();

    }
    @Test
    void devePermitirListarMensagens(){
        //arrange nao possui pois e uma lista

        //act
        var resultadosObtidos = mensagemRepository.findAll();

        //assert
        assertThat(resultadosObtidos).hasSizeGreaterThan(0);
    }

    private Mensagem gerarMensagem(){
        return Mensagem.builder().usuario("Jose").conteudo("Alguma coisa").build();
    }
    
}
