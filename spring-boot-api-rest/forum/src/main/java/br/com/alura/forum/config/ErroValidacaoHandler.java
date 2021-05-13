package br.com.alura.forum.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErroValidacaoHandler {

    private MessageSource messageSource;

    public ErroValidacaoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<?> handler(MethodArgumentNotValidException ex) {
        record ErroValidacaoDTO (String campo, String mensagem) {};
        return ex.getFieldErrors().stream().map(f ->
                new ErroValidacaoDTO(f.getField(), messageSource.getMessage(f, LocaleContextHolder.getLocale())))
                .collect(Collectors.toList());
    }
}
