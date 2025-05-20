package Squad5.API_FSPH.Lote.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

// DTO usado para criar um novo lote.

public record CreateLoteDto(
        @NotNull boolean loteLamina,                            // Indica se o lote é de lâminas
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date dataEnvio,                                         // Data de envio
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date dataRecebimento,                                   // Data de recebimento
        /** retirar isso e add como um Patch para atualizar quando receber? */
        List<@NotNull @Pattern(regexp = "\\d{2}\\d{4}-\\d{5}", message = "Formato inválido, deve ser DDYYYY-XXXXX") String> amostrasId // Lista de protocolos das amostras associadas

) {}
