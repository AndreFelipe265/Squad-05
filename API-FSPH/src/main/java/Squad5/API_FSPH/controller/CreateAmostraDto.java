package Squad5.API_FSPH.controller;

import java.util.UUID;
import java.time.LocalDate;

public record CreateAmostraDto(UUID idProtocolo,String tipo,LocalDate dataCadastro,String dataColeta,String localColeta,
                               String municipioNome,UUID municipioID,String protocoloLote,String Status,String ObsStatus) {
}
