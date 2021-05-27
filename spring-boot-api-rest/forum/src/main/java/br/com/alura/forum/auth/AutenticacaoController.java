package br.com.alura.forum.auth;

import br.com.alura.forum.auth.form.FormLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;

    public AutenticacaoController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid FormLogin form){
        try {
            authenticationManager.authenticate(form.converter());
            return ResponseEntity.ok().build();
        } catch (AuthenticationException ex){
            return  ResponseEntity.badRequest().build();
        }
    }
}
