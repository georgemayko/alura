package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicoController {

    @GetMapping("/topicos")
    public List<TopicoDTO> listaTopicos(){
        Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
        //TopicoDTO dto = new TopicoDTO(topico);
        //return Arrays.asList(dto, dto, dto);
        return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
    }
}
