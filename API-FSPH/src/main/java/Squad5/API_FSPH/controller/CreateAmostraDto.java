package Squad5.API_FSPH.dto;

import Squad5.API_FSPH.entity.Tipo;
import jakarta.validation.constraints.*;

import java.util.UUID;

/**
 * DTO usado para criar uma nova amostra.
 */
public record CreateAmostraDto(
        @NotNull Tipo tipo,                   // Tipo da amostra (ex: MOSQUITO)
        @Positive Integer quantidade,         // Quantidade da amostra, deve ser maior que 0
        @NotBlank String enderecoCaptura,     // Endereço da coleta da amostra
        @NotBlank String dataCaptura,         // Data da coleta no formato ISO (yyyy-MM-dd)
        String insetoLarva,                   // Nome do inseto ou larva, se aplicável
        @NotNull UUID municipioId,            // ID do município
        @NotBlank String municipioNome,       // Nome do município
        @NotBlank String protocoloLote        // Protocolo do lote a que pertence
) {}
