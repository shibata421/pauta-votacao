package br.com.shibata.fernando.application.controller;

import br.com.shibata.fernando.application.entity.Pauta;
import br.com.shibata.fernando.application.entity.Voto;
import br.com.shibata.fernando.application.service.PautaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class PautaController {

    private PautaService service;

    @PostMapping("/pauta/criar")
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        log.info("Criando pauta {}", pauta.getDescricao());

        service.criarPauta(pauta);

        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @PutMapping("/pauta/abrir")
    public ResponseEntity<Pauta> abrirPauta(@RequestBody Pauta pauta) {
        log.info("Abrindo pauta id: {}", pauta.getId());

        Pauta pautaObtida = service.abrirPauta(pauta);

        return new ResponseEntity<>(pautaObtida, HttpStatus.OK);
    }

    @PostMapping("/pauta/votar")
    public ResponseEntity<Voto> votar(@RequestBody Voto voto) {
        log.info("Votando {}", voto);

        service.votar(voto);

        return new ResponseEntity<>(voto, HttpStatus.CREATED);
    }

    @GetMapping("/pauta/{id}/contabilizar")
    public ResponseEntity<Pauta> contabilizar(@PathVariable Integer id) {
        log.info("Contabilizando votos da pauta de id {}", id);

        Pauta pauta = service.contabilizarVotos(id);

        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }
}
