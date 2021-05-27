package br.com.alura.forum.auth;

import br.com.alura.forum.auth.form.FormLogin;
import br.com.alura.forum.config.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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
    private TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid FormLogin form){
        try {
            final String AUTH_METHOD = "Bearer";
            record TokenDTO(String token, String tipo){};
            Authentication authentication = authenticationManager.authenticate(form.converter());
            String token = this.tokenService.criar(authentication);
            return ResponseEntity.ok(new TokenDTO(token, AUTH_METHOD));
        } catch (AuthenticationException ex){
            return  ResponseEntity.badRequest().build();
        }
    }
}
