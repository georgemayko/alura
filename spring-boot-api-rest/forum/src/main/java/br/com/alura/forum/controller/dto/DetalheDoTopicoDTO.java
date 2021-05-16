package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record DetalheDoTopicoDTO (Long id,
                                  String titulo,
                                  String mensagem,
                                  LocalDateTime dataCriacao,
                                  String nomeAutor,
                                  StatusTopico status,
                                  List<RespostaDTO> respostas
                                  ) {

    public DetalheDoTopicoDTO (Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getNome(),
                topico.getStatus(),
                RespostaDTO.converter(topico.getRespostas()));
    }
}
