package Squad5.API_FSPH.Lote.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

// DTO usado para atualizar status e a data de envio.

public record UpdateLoteDto(
        String status,                          // Novo status do lote
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date dataEnvio,                          // Nova data de envio
        List<@NotNull @Pattern(regexp = "\\d{2}\\d{4}-\\d{5}", message = "Formato inválido, deve ser DDYYYY-XXXXX") String> amostrasId // Lista de protocolos das amostras associadas

) {}
