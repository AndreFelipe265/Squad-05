package Squad5.API_FSPH.service;

import Squad5.API_FSPH.dto.CreateAmostraDto;
import Squad5.API_FSPH.dto.UpdateAmostraDto;
import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.entity.Tipo;
import Squad5.API_FSPH.exception.BusinessRuleException;
import Squad5.API_FSPH.repository.AmostraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AmostraService {

    private final AmostraRepository amostraRepository;

    public AmostraService(AmostraRepository amostraRepository) {
        this.amostraRepository = amostraRepository;
    }

    /**
     * Cria uma nova amostra com base nos dados do DTO.
     */
    @Transactional
    public String criarAmostra(CreateAmostraDto dto) {
        validarDados(dto);
        validarLaminasPCE(dto.protocoloLote(), dto.tipo());

        Amostra amostra = new Amostra();
        amostra.setProtocolo(gerarProtocolo());
        amostra.setTipo(dto.tipo());
        amostra.setQuantidade(dto.quantidade());
        amostra.setEnderecoCaptura(dto.enderecoCaptura());
        amostra.setDataCaptura(LocalDate.parse(dto.dataCaptura()));
        amostra.setInsetoLarva(dto.insetoLarva());
        amostra.setMunicipioId(dto.municipioId());
        amostra.setMunicipioNome(dto.municipioNome());
        amostra.setProtocoloLote(dto.protocoloLote());
        amostra.setStatus("CADASTRADA");

        verificarPrazos(amostra);

        return amostraRepository.save(amostra).getProtocolo();
    }

    /**
     * Busca uma amostra pelo seu protocolo.
     */
    public Optional<Amostra> buscarPorProtocolo(String protocolo) {
        return amostraRepository.findById(protocolo);
    }

    /**
     * Lista todas as amostras de um município.
     */
    public List<Amostra> listarPorMunicipio(UUID municipioId) {
        return amostraRepository.findByMunicipioId(municipioId);
    }

    public void deletarAmostra(String protocolo) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new BusinessRuleException("Amostra não encontrada com o protocolo: " + protocolo));

        amostraRepository.delete(amostra);
    }

    /**
     * Atualiza os campos de status e observação de uma amostra.
     */
    @Transactional
    public void atualizarStatus(String protocolo, UpdateAmostraDto dto) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new BusinessRuleException("Amostra não encontrada com o protocolo: " + protocolo));

        if (dto.status() != null && !dto.status().isBlank()) {
            amostra.setStatus(dto.status());
        }

        if (dto.observacao() != null) {
            amostra.setObservacao(dto.observacao());
        }

        amostraRepository.save(amostra);
    }

    private String gerarProtocolo() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("MMyyyy"));
        long sequencial = amostraRepository.countByDataCriacao(LocalDate.now()) + 1;
        return data + "-" + String.format("%05d", sequencial);
    }

    private void validarDados(CreateAmostraDto dto) {
        if (dto.quantidade() <= 0) {
            throw new BusinessRuleException("Quantidade deve ser maior que zero");
        }
        if (LocalDate.parse(dto.dataCaptura()).isAfter(LocalDate.now())) {
            throw new BusinessRuleException("Data de captura não pode ser futura");
        }
        if (dto.protocoloLote() == null || dto.protocoloLote().isBlank()) {
            throw new BusinessRuleException("Protocolo do lote é obrigatório");
        }
    }

    private void validarLaminasPCE(String protocoloLote, Tipo tipo) {
        if (tipo == Tipo.LAMINAS_PCE) {
            if (amostraRepository.existsByProtocoloLoteAndTipoNot(protocoloLote, Tipo.LAMINAS_PCE)) {
                throw new BusinessRuleException("Lâminas PCE não podem estar no mesmo lote que outros tipos");
            }
        } else {
            if (amostraRepository.existsByProtocoloLoteAndTipo(protocoloLote, Tipo.LAMINAS_PCE)) {
                throw new BusinessRuleException("Não é possível adicionar este tipo ao lote (já contém Lâminas PCE)");
            }
        }
    }

    private void verificarPrazos(Amostra amostra) {
        LocalDate dataAtual = LocalDate.now();
        long diasDiferenca = ChronoUnit.DAYS.between(amostra.getDataCaptura(), dataAtual);

        switch (amostra.getTipo()) {
            case MOSQUITO:
                if (diasDiferenca > 2) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                    amostra.setObservacao("Amostra de mosquito excedeu o prazo de 48h para envio");
                }
                break;
            case BARBEIRO:
            case BESOURO:
            case CARRAPATO:
                if (diasDiferenca > 5) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                    amostra.setObservacao("Amostra excedeu o prazo de 5 dias para envio");
                }
                break;
            case CARAMUJO:
                if (!amostra.getDataCaptura().equals(dataAtual)) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                    amostra.setObservacao("Caramujos devem ser enviados no mesmo dia da coleta");
                }
                break;
            case LAMINAS_PCE:
                if (diasDiferenca > 7) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                    amostra.setObservacao("Lâminas PCE devem ser enviadas semanalmente");
                }
                break;
            case ESCORPIAO:
                if (amostra.getInsetoLarva() != null &&
                        amostra.getInsetoLarva().toLowerCase().contains("esmagado")) {
                    amostra.setStatus("INVALIDA");
                    amostra.setObservacao("Escorpião não pode estar esmagado para análise");
                }
                break;
            case LARVAS:
                if (diasDiferenca > 3) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                    amostra.setObservacao("Larvas devem ser enviadas em até 72h após coleta");
                }
                break;
        }
    }
}
