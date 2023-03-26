package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.entity.Associado;
import br.com.shibata.fernando.application.entity.Pauta;
import br.com.shibata.fernando.application.entity.Voto;
import br.com.shibata.fernando.application.entity.VotoEnum;
import br.com.shibata.fernando.application.exception.PautaException;
import br.com.shibata.fernando.application.repository.AssociadoRepository;
import br.com.shibata.fernando.application.repository.PautaRepository;
import br.com.shibata.fernando.application.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PautaService {

    private PautaRepository pautaRepository;
    private AssociadoRepository associadoRepository;
    private VotoRepository votoRepository;
    private CpfService cpfService;
    private RabbitMqService rabbitMqService;

    public void criarPauta(Pauta pauta) {
        pautaRepository.save(pauta);
    }

    public Pauta abrirPauta(Pauta pauta) {
        Pauta pautaObtida = pautaRepository.findById(pauta.getId())
                .orElseThrow(() -> new PautaException("Pauta não encontrada"));

        LocalDateTime horarioFim = pautaObtida.getHorarioFim();

        if (horarioFim != null) {
            throw new PautaException("Pauta já foi aberta");
        }

        if (pauta.getHorarioFim() == null) {
            pautaObtida.setHorarioFim(LocalDateTime.now().plusMinutes(1));
        }

        pautaRepository.save(pautaObtida);

        return pautaObtida;
    }

    public void votar(Voto voto) {
        if (voto.getAssociado() == null || voto.getPauta() == null) {
            throw new PautaException("O voto deve conter o associado e a pauta");
        }

        Integer pautaId = voto.getPauta().getId();
        Integer associadoId = voto.getAssociado().getId();

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new PautaException("Pauta inexistente"));

        Associado associado = associadoRepository.findById(associadoId).
                orElseThrow(() -> new PautaException("Associado inexistente"));

        voto.setPauta(pauta);
        voto.setAssociado(associado);

        if (pauta.getHorarioFim().isBefore(LocalDateTime.now())) {
            throw new PautaException("Esta pauta já terminou");
        }

        if (!VotoEnum.votoCorreto(voto.getVoto())) {
            throw new PautaException("Voto incorreto");
        }

        cpfService.validarUsuario(voto.getAssociado().getCpf());

        if (votoRepository.findByAssociadoIdAndPautaId(associadoId, pautaId).isPresent()) {
            throw new PautaException("Não pode haver mais de um voto de um mesmo associado para uma mesma pauta");
        }

        votoRepository.save(voto);
    }

    public Pauta contabilizarVotos(Integer id) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new PautaException("Pauta não encontrada"));

        pauta.setSomaSim(pauta.getVotos().stream().filter(v -> v.getVoto().equals("S")).count());
        pauta.setSomaNao(pauta.getVotos().stream().filter(v -> v.getVoto().equals("N")).count());

        rabbitMqService.enviarMensagem(pauta);

        return pauta;
    }
}
