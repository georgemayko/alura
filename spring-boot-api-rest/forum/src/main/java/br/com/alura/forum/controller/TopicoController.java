package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalheDoTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.modelo.Topico;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(TopicoController.TOPICOS_PATH)
public class TopicoController {

    public static final String TOPICOS_PATH = "/topicos";
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
    public ResponseEntity<TopicoDTO> cadastraTopico( @RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico novoTopico = repo.save(form.converter(cursoRepository));
        URI uri = uriBuilder.path(TOPICOS_PATH + "/{id}").buildAndExpand(novoTopico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(novoTopico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheDoTopicoDTO> detalhar(@PathVariable("id") Long id){
        Optional<Topico> possivelTopico = repo.findById(id);
        if(possivelTopico.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DetalheDoTopicoDTO(possivelTopico.get()));
    }
}
