package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TopicoDTO(Long id, String titulo, String mensagem, LocalDateTime dataCriacao) {
    public TopicoDTO(Topico topico) {
       this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
    }

    public static List<TopicoDTO> converter(List<Topico> topicos){
        return topicos.stream().map( TopicoDTO::new).collect(Collectors.toList());
    }
}
