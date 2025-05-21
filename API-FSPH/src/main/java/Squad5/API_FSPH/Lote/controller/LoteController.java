package Squad5.API_FSPH.Lote.controller;

import Squad5.API_FSPH.Lote.entity.Lote;
import Squad5.API_FSPH.Lote.service.LoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lotes")
@Tag(name = "Lotes", description = "Gerenciamento de lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @PostMapping
    public ResponseEntity<Lote> criarLote(@RequestBody CreateLoteDto dto) {
        Lote novoLote = loteService.criarLote(dto);
        return ResponseEntity.ok(novoLote);
    }

}
