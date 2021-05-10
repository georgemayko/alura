package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.repository.TopicoRepository;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicoController {

    private TopicoRepository repo;

    public TopicoController(TopicoRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/topicos")
    public List<TopicoDTO> listaTopicos(){
        return TopicoDTO.converter(repo.findAll());
    }
}
