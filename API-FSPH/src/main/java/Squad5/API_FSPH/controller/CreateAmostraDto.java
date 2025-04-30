package Squad5.API_FSPH.controller;

import Squad5.API_FSPH.entity.Tipo;

import java.util.UUID;
import java.time.LocalDate;

public record CreateAmostraDto(UUID idProtocolo, Tipo tipo, LocalDate dataCadastro, String dataColeta, String localColeta,
                               String municipioNome, UUID municipioID, String protocoloLote, String Status, String ObsStatus) {
}
