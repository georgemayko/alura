package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record AtualizacaoTopicoForm(@NotNull @NotEmpty String titulo, @NotBlank String mensagem) {


    public Topico atualizar(Long id, TopicoRepository repo) {
        Topico topico = repo.getOne(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
