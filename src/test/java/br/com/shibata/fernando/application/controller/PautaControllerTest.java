package br.com.shibata.fernando.application.controller;

import br.com.shibata.fernando.application.entity.Pauta;
import br.com.shibata.fernando.application.entity.Voto;
import br.com.shibata.fernando.application.service.PautaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PautaControllerTest {

    @InjectMocks
    private PautaController controller;

    @Mock
    private PautaService service;

    @Test
    void deveCriarPauta() {
        assertThat(controller.criarPauta(mock(Pauta.class)).getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void deveAbrirPauta() {
        assertThat(controller.abrirPauta(mock(Pauta.class)).getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void deveVotar() {
        assertThat(controller.votar(mock(Voto.class)).getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void deveContabilizarVotos() {
        assertThat(controller.contabilizar(1).getStatusCode(), is(HttpStatus.OK));
    }
}