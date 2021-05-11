package br.com.alura.forum.controller.form;

import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.util.Assert;

public record TopicoForm(String titulo, String mensagem, String nomeCurso) {

    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assert.notNull(curso, "Ao criar um tópico o curso, não pode ser null. O parâmetro nomeCurso não foi encontrado!");
        return new Topico(titulo, mensagem, curso);
    }
}
