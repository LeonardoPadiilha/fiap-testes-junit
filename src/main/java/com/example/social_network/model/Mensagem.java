package com.example.social_network.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Jacksonized
public class Mensagem {

    @Id
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "usuario nao pode ser vazio")
    private String usuario;

    @Column(nullable = false)
    @NotEmpty(message = "conteudo nao pode ser vazio")
    private String conteudo;

    private LocalDateTime dataCriacao;

    private int gostei;

    public Mensagem(UUID id, String usuario, String conteudo) {
        this.id = id != null ? id : UUID.randomUUID();
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.dataCriacao = LocalDateTime.now();
        this.gostei = 0;
    }
}
