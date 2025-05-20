package Squad5.API_FSPH.Lote.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.util.Date;

// DTO usado para atualizar status e a data de envio.

public record UpdateLoteDto(
        String status,                          // Novo status do lote
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date dataEnvio                          // Nova data de envio
) {}