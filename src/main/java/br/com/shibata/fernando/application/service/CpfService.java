package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.configuration.PautaProperties;
import br.com.shibata.fernando.application.entity.CpfStatus;
import br.com.shibata.fernando.application.exception.PautaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CpfService {

    private final RestTemplate restTemplate;
    private final PautaProperties properties;

    public void validarUsuario(String cpf) {
        String url = properties.getValidationUrl().replace("{cpf}", cpf);

        try {
            CpfStatus cpfStatus = restTemplate.exchange(url, HttpMethod.GET, null, CpfStatus.class)
                    .getBody();

            if (cpfStatus.getStatus().equals("UNABLE_TO_VOTE")) {
                throw new PautaException("O usuário não pode votar");
            }
        } catch (HttpStatusCodeException e) {
            throw new PautaException("CPF inválido");
        }
    }

}
