package br.com.alura.forum.config.security;

import br.com.alura.forum.config.TokenService;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Optional<String> possivelToken = getToken(request);
            if(possivelToken.isPresent() && tokenService.isTokenValido(possivelToken.get())) {
                String token = possivelToken.get();
                autenticaCliente(token);
            }
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }

    private void autenticaCliente(String token) {
        String emailUsuario = tokenService.getEmailUsuario(token);
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).get();
        UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader == null || tokenHeader.isBlank() || !tokenHeader.startsWith("Bearer "))
            return Optional.empty();
        return Optional.of(tokenHeader.substring(7));
    }

}
