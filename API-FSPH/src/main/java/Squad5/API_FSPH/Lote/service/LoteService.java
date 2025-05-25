package Squad5.API_FSPH.Lote.service;

import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.entity.Tipo;
import Squad5.API_FSPH.Amostra.repository.AmostraRepository;
import Squad5.API_FSPH.Lote.controller.CreateLoteDto;
import Squad5.API_FSPH.Lote.controller.UpdateLoteDto;
import Squad5.API_FSPH.Lote.entity.Lote;
import Squad5.API_FSPH.Lote.repository.LoteRepository;
import Squad5.API_FSPH.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        validarTiposDeAmostras(amostras);

        // Atualiza o campo protocoloLote em cada amostra
        for (Amostra amostra : amostras) {
            amostra.setProtocoloLote(protocoloLote);
        }

        // Salva as alterações nas amostras
        amostraRepository.saveAll(amostras);

        Lote lote = new Lote();
        lote.setProtocoloLote(protocoloLote);
        lote.setLoteLamina(dto.loteLamina());
        lote.setStatus("CADASTRADO");
        lote.setDataEnvio(dto.dataEnvio());
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

    public Lote listarLote(String protocoloLote) {
        return loteRepository.findByProtocoloLote(protocoloLote);
    }

    public Optional<Lote> atualizarLote(String protocoloLote, UpdateLoteDto dto) {
        Optional<Lote> optionalLote = loteRepository.findById(protocoloLote);

        if (optionalLote.isEmpty()) {
            return Optional.empty();
        }

        Lote lote = optionalLote.get();

        if (dto.status() != null) {
            lote.setStatus(dto.status());
        }

        if (dto.dataEnvio() != null) {
            lote.setDataEnvio(dto.dataEnvio());
        }

        if (dto.dataRecebimento() != null) {
            lote.setDataRecebimento(dto.dataRecebimento());
        }

        loteRepository.save(lote);

        return Optional.of(lote);
    }

    public Optional<Lote> atualizarListaAmostras(String protocoloLote, List<String> amostrasId) {
        Optional<Lote> optionalLote = loteRepository.findById(protocoloLote);

        if (optionalLote.isEmpty()) {
            return Optional.empty();
        }

        Lote lote = optionalLote.get();

        List<Amostra> amostras = amostrasId.isEmpty()
                ? Collections.emptyList()
                : amostraRepository.findAllById(amostrasId);

        if (amostras.size() != amostrasId.size()) {
            throw new IllegalArgumentException("Algumas amostras não foram encontradas.");
        }

        lote.setAmostras(amostras); // Remove todas se vier vazio
        loteRepository.save(lote);

        return Optional.of(lote);
    }

    public boolean deletarLote(String protocoloLote) {
        Optional<Lote> optionalLote = loteRepository.findById(protocoloLote);

        if (optionalLote.isPresent()) {
            Lote lote = optionalLote.get();

            if (lote.getAmostras() != null && !lote.getAmostras().isEmpty()) {
                throw new IllegalStateException("O lote não pode ser excluído porque possui amostras associadas.");
            }

            loteRepository.delete(lote);
            return true;
        }

        return false;
    }

    private void validarTiposDeAmostras(List<Amostra> amostras) {
        boolean contemLaminaPCE = amostras.stream()
                .anyMatch(a -> a.getTipo() == Tipo.LAMINA_PCE);

        boolean contemOutroTipo = amostras.stream()
                .anyMatch(a -> a.getTipo() != Tipo.LAMINA_PCE);

        if (contemLaminaPCE && contemOutroTipo) {
            throw new BusinessRuleException("Amostras do tipo LAMINAS_PCE não podem ser misturadas com outros tipos no mesmo lote.");
        }
    }

}
