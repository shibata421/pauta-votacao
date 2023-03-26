package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.entity.Associado;
import br.com.shibata.fernando.application.entity.Pauta;
import br.com.shibata.fernando.application.entity.Voto;
import br.com.shibata.fernando.application.exception.PautaException;
import br.com.shibata.fernando.application.repository.AssociadoRepository;
import br.com.shibata.fernando.application.repository.PautaRepository;
import br.com.shibata.fernando.application.repository.VotoRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.jeasy.random.FieldPredicates.named;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private CpfService cpfService;

    @Mock
    private RabbitMqService rabbitMqService;

    private Pauta pauta;
    private Voto voto;
    private Associado associado;

    @BeforeEach
    void setup () {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(named("voto"), () -> "S");

        EasyRandom easyRandom = new EasyRandom(parameters);

        pauta = easyRandom.nextObject(Pauta.class);
        voto = easyRandom.nextObject(Voto.class);
        associado = easyRandom.nextObject(Associado.class);
    }

    @Test
    void deveCriarPauta() {
        assertDoesNotThrow(() -> pautaService.criarPauta(pauta));
    }

    @Test
    void deveAbrirPauta() {
        pauta.setHorarioFim(null);

        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        Pauta pautaObtida = pautaService.abrirPauta(pauta);

        assertNotNull(pautaObtida.getHorarioFim());
    }

    @Test
    void naoDeveAbrirPautaInexistente() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(PautaException.class, () -> pautaService.abrirPauta(pauta));
    }

    @Test
    void naoDeveAbrirPautaJaAberta() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        assertThrows(PautaException.class, () -> pautaService.abrirPauta(pauta));
    }

    @Test
    void naoDeveAceitarVotoSemAssociado() {
        assertThrows(PautaException.class, () -> pautaService.votar(new Voto()));
    }

    @Test
    void naoDeveAceitarVotoSemPauta() {
        voto.setPauta(null);
        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void naoDevePermitirVotarEmPautaInexistente() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void naoDevePermitirVotarEmPautaTerminada() {
        pauta.setHorarioFim(LocalDateTime.MIN);

        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void naoDevePermitirVotarAssociadoInexistente() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        when(associadoRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void naoDevePermitirVotarVotoLetraErrada() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        when(associadoRepository.findById(anyInt()))
                .thenReturn(Optional.of(associado));

        voto.setVoto("a");

        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void naoDevePermitirDoisVotosDeUmMesmoAssociadoNaMesmaPauta() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        when(associadoRepository.findById(anyInt()))
                .thenReturn(Optional.of(associado));

        when(votoRepository.findByAssociadoIdAndPautaId(anyInt(), anyInt()))
                .thenReturn(Optional.of(voto));

        assertThrows(PautaException.class, () -> pautaService.votar(voto));
    }

    @Test
    void devePermitirVotar() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        when(associadoRepository.findById(anyInt()))
                .thenReturn(Optional.of(associado));

        when(votoRepository.findByAssociadoIdAndPautaId(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> pautaService.votar(voto));
    }

    @Test
    void deveContabilizarVotos() {
        when(pautaRepository.findById(anyInt()))
                .thenReturn(Optional.of(pauta));

        assertDoesNotThrow(() -> pautaService.contabilizarVotos(1));
    }
}