package Squad5.API_FSPH.Lote.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

// DTO usado para atualizar status e a data de envio.

public record UpdateLoteDto(
        String status,                                                               // Novo status do lote
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataEnvio,                          // Nova data de envio
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataRecebimento                    // Data de recebimento

) {}
