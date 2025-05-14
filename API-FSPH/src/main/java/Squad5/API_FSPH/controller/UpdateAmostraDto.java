package Squad5.API_FSPH.dto;

/**
 * DTO usado para atualizar status e observações da amostra.
 */
public record UpdateAmostraDto(
        String status,
        String observacao
) {}
