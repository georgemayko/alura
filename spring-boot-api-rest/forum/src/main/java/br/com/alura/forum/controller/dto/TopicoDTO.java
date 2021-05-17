package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record TopicoDTO(Long id, String titulo, String mensagem, LocalDateTime dataCriacao) {
    public TopicoDTO(Topico topico) {
       this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
    }

    public static Page<TopicoDTO> converter(Page<Topico> topicos){
        //return topicos.stream().map( TopicoDTO::new).collect(Collectors.toList());
        return topicos.map(TopicoDTO::new);
    }
}
