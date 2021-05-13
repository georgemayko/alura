package br.com.alura.forum.controller.form;

import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record TopicoForm (
    @NotNull @NotEmpty String titulo,
    @NotBlank String mensagem,
    @NotBlank String nomeCurso) {

    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assert.notNull(curso, "Ao criar um tópico o curso, não pode ser null. O parâmetro nomeCurso não foi encontrado!");
        return new Topico(titulo, mensagem, curso);
    }
}
