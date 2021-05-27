package br.com.alura.forum.config.security;

import br.com.alura.forum.config.TokenService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    public AutenticacaoViaTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Optional<String> possivelToken = getToken(request);
            if(possivelToken.isPresent()) {
                String token = possivelToken.get();
                boolean valido = tokenService.isTokenValido(token);
            }
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader == null || tokenHeader.isBlank() || !tokenHeader.startsWith("Bearer "))
            return Optional.empty();
        return Optional.of(tokenHeader.substring(7));
    }

}
