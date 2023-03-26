package br.com.shibata.fernando.application.controller;

import br.com.shibata.fernando.application.exception.PautaException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PautaControllerAdviceTest {

    @Test
    void handle() {
        assertThat(new PautaControllerAdvice().handlePautaException(new PautaException("")).getStatusCode(),
                is(HttpStatus.BAD_REQUEST));
    }

}