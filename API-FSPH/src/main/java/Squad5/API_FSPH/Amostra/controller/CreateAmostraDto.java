package Squad5.API_FSPH.Amostra.controller;

import Squad5.API_FSPH.Amostra.entity.Tipo;
import jakarta.validation.constraints.*;

import java.util.UUID;

// DTO usado para criar uma nova amostra.

public record CreateAmostraDto(
        @NotNull Tipo tipo,                   // Tipo da amostra (ex: MOSQUITO)
        @NotBlank String localCaptura,        // Local de captura (ex: área urbana, rural, matagal)
        @NotBlank String enderecoCaptura,     // Endereço da coleta da amostra
        @NotBlank String dataCaptura,         // Data da coleta no formato ISO (yyyy-MM-dd)
        @NotNull UUID municipioId,            // ID do município
        @NotBlank String municipioNome,       // Nome do município
        @NotBlank String protocoloLote        /** retirar daq pq sera adicionado automaticamente */
) {}
