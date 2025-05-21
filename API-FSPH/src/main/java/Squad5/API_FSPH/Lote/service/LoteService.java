package Squad5.API_FSPH.Lote.service;

import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.repository.AmostraRepository;
import Squad5.API_FSPH.Lote.controller.CreateLoteDto;
import Squad5.API_FSPH.Lote.entity.Lote;
import Squad5.API_FSPH.Lote.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private AmostraRepository amostraRepository;

    public Lote criarLote(CreateLoteDto dto) {
        List<Amostra> amostras = dto.amostrasId().stream()
                .map(id -> amostraRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Amostra não encontrada: " + id)))
                .collect(Collectors.toList());

        String protocoloLote = gerarProtocoloLote();

        Lote lote = new Lote();
        lote.setProtocoloLote(protocoloLote);
        lote.setLoteLamina(dto.loteLamina());
        lote.setStatus("Criado"); // ou outro valor default
        lote.setDataEnvio(dto.dataEnvio());
        lote.setDataRecebimento(dto.dataRecebimento());
        lote.setDataCriacao(LocalDate.now());
        lote.setAmostras(amostras);

        return loteRepository.save(lote);
    }

    private String gerarProtocoloLote() {
        LocalDate hoje = LocalDate.now();
        String dataStr = hoje.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

        long quantidadeHoje = loteRepository.countByDataCriacao(hoje);
        long sequencial = quantidadeHoje + 1;
        String sufixo = String.format("%05d", sequencial);

        return dataStr + "-" + sufixo;
    }
}
