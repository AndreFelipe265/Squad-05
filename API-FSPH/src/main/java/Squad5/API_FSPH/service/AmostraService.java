package Squad5.API_FSPH.service;

import Squad5.API_FSPH.controller.CreateAmostraDto;
import Squad5.API_FSPH.controller.UpdateAmostraDto;
import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.entity.Tipo;
import Squad5.API_FSPH.exception.BusinessRuleException;
import Squad5.API_FSPH.exception.InvalidOperationException;
import Squad5.API_FSPH.repository.AmostraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AmostraService {

    private final AmostraRepository amostraRepository;

    public AmostraService(AmostraRepository amostraRepository) {
        this.amostraRepository = amostraRepository;
    }

    // ==================== MÉTODOS PRINCIPAIS ====================

    @Transactional
    public UUID createAmostra(CreateAmostraDto createAmostra) {
        validarDadosObrigatorios(createAmostra);
        validarTipo(createAmostra.tipo());
        validarCompatibilidadeLaminasPCE(createAmostra.protocoloLote(), createAmostra.tipo());

        UUID protocolo = createAmostra.idProtocolo() != null ? createAmostra.idProtocolo() : UUID.randomUUID();

        Amostra amostra = new Amostra(
                protocolo,
                createAmostra.tipo(),
                java.sql.Date.valueOf(createAmostra.dataCadastro()),
                createAmostra.dataColeta(),
                createAmostra.localColeta(),
                createAmostra.municipioNome(),
                createAmostra.municipioID(),
                createAmostra.protocoloLote(),
                createAmostra.Status(),
                createAmostra.ObsStatus()
        );

        verificarPrazoEnvio(amostra);
        tratarAmostraInvalida(amostra);

        return amostraRepository.save(amostra).getIdProtocolo();
    }

    public Optional<Amostra> getIdProtocolo(String idProtocolo) {
        try {
            UUID id = UUID.fromString(idProtocolo);
            return amostraRepository.findById(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public List<Amostra> listAmostras() {
        return amostraRepository.findAll();
    }

    @Transactional
    public void updateAmostraById(String idProtocolo, UpdateAmostraDto updateAmostraDto) {
        Amostra amostra = amostraRepository.findById(UUID.fromString(idProtocolo))
                .orElseThrow(() -> new BusinessRuleException("Amostra não encontrada"));

        validarLoteNaoDespachado(amostra);

        if (updateAmostraDto.ObsStatus() != null) {
            amostra.setObsStatus(updateAmostraDto.ObsStatus());
        }
        if (updateAmostraDto.dataColeta() != null) {
            amostra.setDataColeta(updateAmostraDto.dataColeta());
        }
        if (updateAmostraDto.localColeta() != null) {
            amostra.setLocalColeta(updateAmostraDto.localColeta());
        }
        if (updateAmostraDto.Status() != null) {
            if ("INVALIDO".equalsIgnoreCase(updateAmostraDto.Status())) {
                notificarPrefeitura(amostra);
            }
            amostra.setStatus(updateAmostraDto.Status());
        }

        tratarAmostraInvalida(amostra);
        amostraRepository.save(amostra);
    }

    @Transactional
    public void deleteById(String idProtocolo) {
        Amostra amostra = amostraRepository.findById(UUID.fromString(idProtocolo))
                .orElseThrow(() -> new BusinessRuleException("Amostra não encontrada"));

        validarLoteNaoDespachado(amostra);
        amostraRepository.delete(amostra);
    }

    // olhar se tera que mudar
    @Transactional
    public void excluirLote(String protocoloLote) {
        if (amostraRepository.countByProtocoloLote(protocoloLote) > 0) {
            throw new BusinessRuleException("Lote não pode ser excluído: contém amostras vinculadas");
        }
        // Implementação da exclusão do lote, se necessário
    }

    public List<Amostra> listarAmostrasPorLote(String protocoloLote) {
        return amostraRepository.findByProtocoloLote(protocoloLote);
    }

    // ==================== REGRAS DE NEGÓCIO ====================

    private void validarDadosObrigatorios(CreateAmostraDto dto) {
        if (dto.tipo() == null) {
            throw new IllegalArgumentException("Tipo de amostra é obrigatório.");
        }
        if (dto.dataColeta() == null || dto.dataColeta().isBlank()) {
            throw new IllegalArgumentException("Data de coleta é obrigatória.");
        }
        if (dto.municipioID() == null) {
            throw new IllegalArgumentException("ID do município é obrigatório.");
        }
        if (dto.protocoloLote() == null || dto.localColeta() == null || dto.dataCadastro() == null) {
            throw new IllegalArgumentException("Campos obrigatórios estão faltando.");
        }
    }

    private void validarTipo(Tipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de amostra inválido.");
        }
    }

    private void validarCompatibilidadeLaminasPCE(String protocoloLote, Tipo tipo) {
        if (tipo == Tipo.LaminasPCE) {
            if (amostraRepository.existsByProtocoloLoteAndTipoNot(protocoloLote, Tipo.LaminasPCE)) {
                throw new BusinessRuleException("Lâminas PCE não podem estar no mesmo lote que outros tipos");
            }
        } else {
            if (amostraRepository.existsByProtocoloLoteAndTipo(protocoloLote, Tipo.LaminasPCE)) {
                throw new BusinessRuleException("Não é possível adicionar este tipo ao lote (já contém Lâminas PCE)");
            }
        }
    }

    private void validarLoteNaoDespachado(Amostra amostra) {
        if ("DESPACHADO".equalsIgnoreCase(amostra.getStatus())) {
            throw new InvalidOperationException("Operação não permitida: lote já despachado");
        }
    }

    private void tratarAmostraInvalida(Amostra amostra) {
        if ("INVÁLIDA".equalsIgnoreCase(amostra.getStatus()) || "INVALIDO".equalsIgnoreCase(amostra.getStatus())) {
            System.out.println("⚠ Atenção: Amostra inválida. Notificar o Lacen ou prefeitura.");
        }
    }

    private void notificarPrefeitura(Amostra amostra) {
        String mensagem = String.format(
                "Notificação: Amostra %s (%s) do município %s foi marcada como INVÁLIDA",
                amostra.getIdProtocolo(),
                amostra.getTipo(),
                amostra.getMunicipioNome()
        );
        System.out.println(mensagem); // Substituir por notificação real (e-mail, SMS etc)
    }

    private void verificarPrazoEnvio(Amostra amostra) {
        Tipo tipo = amostra.getTipo();
        LocalDate dataCadastro = new java.sql.Date(amostra.getDataCadastro().getTime()).toLocalDate();
        long diasDesdeCadastro = ChronoUnit.DAYS.between(dataCadastro, LocalDate.now());

        switch (tipo) {
            case Mosquito:
                if (diasDesdeCadastro > 2) amostra.setStatus("PRAZO_EXCEDIDO");
                break;
            case Besouro:
            case Carrapato:
                if (diasDesdeCadastro > 5) amostra.setStatus("PRAZO_EXCEDIDO");
                break;
            case Caramujo:
                LocalDate dataColeta = LocalDate.parse(amostra.getDataColeta(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (!dataColeta.equals(LocalDate.now()) || LocalTime.now().isAfter(LocalTime.of(12, 0))) {
                    amostra.setStatus("PRAZO_EXCEDIDO");
                }
                break;
            case LaminasPCE:
            case Escorpiao:
            case Larvas:
                // Não exige prazo
                break;
        }
    }

    // Utilitário opcional: gerador de protocolo (se necessário usar)
    private String gerarProtocolo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
        String data = LocalDate.now().format(formatter);
        int random = new Random().nextInt(9999);
        return data + "-" + String.format("%04d", random);
    }
}
