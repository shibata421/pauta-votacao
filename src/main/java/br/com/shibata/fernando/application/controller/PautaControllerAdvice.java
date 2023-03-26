package br.com.shibata.fernando.application.controller;

import br.com.shibata.fernando.application.entity.Erro;
import br.com.shibata.fernando.application.exception.PautaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PautaControllerAdvice {

    @ExceptionHandler(PautaException.class)
    public ResponseEntity<Erro> handlePautaException(PautaException e) {
        return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
