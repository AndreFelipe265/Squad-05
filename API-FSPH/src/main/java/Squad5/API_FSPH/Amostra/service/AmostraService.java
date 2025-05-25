package Squad5.API_FSPH.Amostra.service;

import Squad5.API_FSPH.Amostra.controller.CreateAmostraDto;
import Squad5.API_FSPH.Amostra.controller.UpdateAmostraDto;
import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.entity.Tipo;
import Squad5.API_FSPH.exception.BusinessRuleException;
import Squad5.API_FSPH.exception.InvalidOperationException;
import Squad5.API_FSPH.Amostra.repository.AmostraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AmostraService {

    private final AmostraRepository amostraRepository;

    public AmostraService(AmostraRepository amostraRepository) {
        this.amostraRepository = amostraRepository;
    }

    // Cria uma nova amostra com base nos dados do DTO.

    @Transactional
    public Amostra criarAmostra(CreateAmostraDto dto) {
        validarDados(dto);

        Amostra amostra = new Amostra();
        amostra.setProtocoloAmostra(gerarProtocolo());
        amostra.setTipo(dto.tipo());
        amostra.setLocalCaptura(dto.localCaptura());
        amostra.setEnderecoCaptura(dto.enderecoCaptura());
        amostra.setDataCaptura(LocalDate.parse(dto.dataCaptura()));
        amostra.setMunicipioId(dto.municipioId());
        amostra.setMunicipioNome(dto.municipioNome());
        amostra.setStatus("CADASTRADA");

        verificarPrazos(amostra);

        return amostraRepository.save(amostra);
    }

    @Transactional
    public List<Amostra> criarAmostras(List<CreateAmostraDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException("Lista de amostras vazia ou nula");
        }

        List<Amostra> amostrasCriadas = new ArrayList<>();

        for (CreateAmostraDto dto : dtos) {
            try {
                Amostra amostraCriada = criarAmostra(dto); // Aqui o protocolo é gerado internamente
                amostrasCriadas.add(amostraCriada);
            } catch (BusinessRuleException e) {
                // Identificação baseada nos campos disponíveis no DTO
                String info = "Tipo: " + dto.tipo() + ", Local: " + dto.localCaptura()
                        + ", Data: " + dto.dataCaptura() + ", Município: " + dto.municipioNome();
                throw new BusinessRuleException("Erro ao criar amostra (" + info + "). " + e.getMessage());
            }
        }

        return amostrasCriadas;
    }

    // Busca uma amostra pelo seu protocolo.

    public Optional<?> buscarPorProtocolo(String protocoloAmostra) {
        return amostraRepository.findById(protocoloAmostra);
    }

    public List<Amostra> listarTodas() {
        return amostraRepository.findAll();
    }

    // Lista todas as amostras de um município.
    public List<Amostra> listarPorMunicipio(UUID municipioId) {
        return amostraRepository.findByMunicipioId(municipioId);
    }

    public boolean deletarAmostra(String protocoloAmostra) {
        if (!amostraRepository.existsById(protocoloAmostra)) return false;

        amostraRepository.deleteById(protocoloAmostra);
        return true;
    }

    //Atualiza os campos de status e observação de uma amostra.

    @Transactional
    public boolean atualizarStatus(String protocoloAmostra, UpdateAmostraDto dto) {
        Optional<Amostra> optional = amostraRepository.findById(protocoloAmostra);
        if (optional.isEmpty()) return false;

        Amostra amostra = amostraRepository.findByProtocoloAmostra(protocoloAmostra)
                .orElseThrow(() -> new BusinessRuleException("Amostra não encontrada com o protocolo: " + protocoloAmostra));

        if ("CONCLUIDA".equalsIgnoreCase(amostra.getStatus())) {
            throw new BusinessRuleException("Não é possível editar uma amostra com status CONCLUIDA.");
        }

        if (dto.status() != null && !dto.status().isBlank()) {
            amostra.setStatus(dto.status());
        }

        if (dto.observacao() != null) {
            amostra.setObservacao(dto.observacao());
        }

        amostraRepository.save(amostra);
        return true;
    }

    private String gerarProtocolo() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("ddyyyy"));
        long sequencial = amostraRepository.countByDataCriacao(LocalDate.now()) + 1;
        return data + "-" + String.format("%05d", sequencial);
    }

    private void validarDados(CreateAmostraDto dto) {
        if (LocalDate.parse(dto.dataCaptura()).isAfter(LocalDate.now())) {
            throw new BusinessRuleException("Data de captura não pode ser futura");
        }

    }

    private void verificarPrazos(Amostra amostra) {
        LocalDate dataAtual = LocalDate.now();
        long diasDiferenca = ChronoUnit.DAYS.between(amostra.getDataCaptura(), dataAtual);

        switch (amostra.getTipo()) {
            case MOSQUITO:
                if (diasDiferenca > 2) {
                    throw new BusinessRuleException("Amostra de mosquito excedeu o prazo de 48h para envio");
                }
                break;


            case BARBEIRO:
                if (diasDiferenca > 5) {
                    throw new BusinessRuleException("Amostra de barbeiro excedeu o prazo de 5 dias para envio");
                }
                break;

            case CARAMUJO:
                if (!amostra.getDataCaptura().equals(dataAtual)) {
                    throw new BusinessRuleException("Caramujo deve ser enviado no mesmo dia da coleta");
                } else {
                    LocalTime agora = LocalTime.now();
                    if (agora.isBefore(LocalTime.of(7, 0)) || agora.isAfter(LocalTime.of(12, 0))) {
                        throw new BusinessRuleException("Caramujo deve ser enviado entre 07h e 12h do mesmo dia da coleta");
                    }
                }
                break;

            case LAMINA_PCE:
                if (diasDiferenca > 7) {
                    throw new BusinessRuleException("Lâminas PCE devem ser enviadas semanalmente (até 7 dias)");
                }
                break;

            case ESCORPIAO:
                // Nenhuma regra específica após remoção do campo insetoLarva
                break;

            case CARRAPATO:
                // Carrapato não possui prazo definido. Nenhuma validação necessária.
                break;

            case LARVAS:
                // Larvas têm prazo indefinido. Nenhuma validação necessária.
                break;

            default:
                throw new InvalidOperationException("Tipo de amostra não reconhecido: " + amostra.getTipo());
        }
    }
}
