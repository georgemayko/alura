package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.controller.repository.TopicoRepository;
import br.com.alura.forum.modelo.Topico;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoRepository repo;
    private CursoRepository cursoRepository;

    public TopicoController(TopicoRepository repo, CursoRepository cursoRepository) {
        this.repo = repo;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<TopicoDTO> listaTopicos(String nomeCurso){
       if(nomeCurso != null){
           return TopicoDTO.converter(repo.findByCurso_nomeContainingIgnoreCase(nomeCurso));
       }
       return TopicoDTO.converter(repo.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicoDTO> cadastraTopico(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico novoTopico = repo.save(form.converter(cursoRepository));
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new TopicoDTO(novoTopico));
    }
}
