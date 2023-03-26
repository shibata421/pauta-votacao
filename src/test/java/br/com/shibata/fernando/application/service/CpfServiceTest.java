package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.configuration.PautaProperties;
import br.com.shibata.fernando.application.entity.CpfStatus;
import br.com.shibata.fernando.application.exception.PautaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CpfServiceTest {

    @InjectMocks
    private CpfService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PautaProperties properties;

    @Test
    void deveValidarCpf() {
        CpfStatus cpfStatus = new CpfStatus();
        cpfStatus.setStatus("ABLE_TO_VOTE");

        when(properties.getValidationUrl())
                .thenReturn("{cpf}");

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(null), eq(CpfStatus.class)))
                .thenReturn(new ResponseEntity<>(cpfStatus, HttpStatus.OK));

        assertDoesNotThrow(() -> service.validarUsuario("1"));
    }

    @Test
    void deveValidarCpfUnableToVote() {
        CpfStatus cpfStatus = new CpfStatus();
        cpfStatus.setStatus("UNABLE_TO_VOTE");

        when(properties.getValidationUrl())
                .thenReturn("{cpf}");

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(null), eq(CpfStatus.class)))
                .thenReturn(new ResponseEntity<>(cpfStatus, HttpStatus.OK));

        assertThrows(PautaException.class, () -> service.validarUsuario("1"));
    }

    @Test
    void deveValidarCpfInvalido() {
        CpfStatus cpfStatus = new CpfStatus();
        cpfStatus.setStatus("UNABLE_TO_VOTE");

        when(properties.getValidationUrl())
                .thenReturn("{cpf}");

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(null), eq(CpfStatus.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(PautaException.class, () -> service.validarUsuario("1"));
    }

}