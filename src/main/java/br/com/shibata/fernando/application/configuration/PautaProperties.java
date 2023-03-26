package br.com.shibata.fernando.application.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "cpf")
@Data
public class PautaProperties {

    private String validationUrl;
}
