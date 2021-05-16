package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Resposta;

import java.util.List;
import java.util.stream.Collectors;

public record RespostaDTO(Long id, String mensage, String nomeAutor) {

    public RespostaDTO(Resposta resposta){
        this(resposta.getId(), resposta.getMensagem(), resposta.getAutor().getNome());
    }

    public static List<RespostaDTO> converter(List<Resposta> respostas) {
        return respostas.stream().map(RespostaDTO::new).collect(Collectors.toList());
    }
}
