package Squad5.API_FSPH.Lote.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdateListaDto(
        List<@NotNull @Pattern(regexp = "\\d{2}\\d{4}-\\d{5}", message = "Formato inválido, deve ser DDYYYY-XXXXX") String> amostrasId
        // Lista de protocolos das amostras associadas
) {}

