package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.dto.DetalheDoTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
    @Transactional
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

    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable("id") Long id, @Valid @RequestBody AtualizacaoTopicoForm form){
        try {
            Topico topico = form.atualizar(id, repo);
            return ResponseEntity.ok(new TopicoDTO(topico));
        } catch(EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity a (@PathVariable("id") Long id){
        Optional<Topico> possivelTopico = repo.findById(id);git
        if(possivelTopico.isEmpty())
            return ResponseEntity.notFound().build();
        repo.delete(possivelTopico.get());
        return ResponseEntity.ok().build();
    }
}
