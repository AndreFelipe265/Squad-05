package Squad5.API_FSPH.Amostra.controller;

// DTO usado para atualizar status e observações da amostra.

public record UpdateAmostraDto(
        String status,
        String observacao
) {}
