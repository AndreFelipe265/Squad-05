package Squad5.API_FSPH.Lote.controller;

import Squad5.API_FSPH.Lote.entity.Lote;
import Squad5.API_FSPH.Lote.service.LoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
@Tag(name = "Lotes", description = "Gerenciamento de lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    // Endpoint para criação de Lote
    @PostMapping
    public ResponseEntity<Lote> criarLote(@RequestBody CreateLoteDto dto) {
        Lote novoLote = loteService.criarLote(dto);
        return ResponseEntity.ok(novoLote);
    }

    // Endpoint para buscar um Lote por protocolo
    @GetMapping("/{protocoloLote}")
    public ResponseEntity<?> listarLote(@PathVariable String protocoloLote) {
        Lote lote = loteService.listarLote(protocoloLote);

        if (lote != null) {
            return ResponseEntity.ok(lote);
        } else {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Lote não encontrado para o protocolo: " + protocoloLote);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }

    // Endpoint para atualizar o Lote (status, dataEnvio e dataRecebimento)
    @PatchMapping("/{protocoloLote}")
    public ResponseEntity<?> atualizarStatusEData(
            @PathVariable String protocoloLote,
            @RequestBody UpdateLoteDto dto
    ) {
        Optional<Lote> updatedLote = loteService.atualizarLote(protocoloLote, dto);

        return updatedLote
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Endpoint para atualizar o Lote (Lista de Amostras)
    @PatchMapping("/{protocoloLote}/amostras")
    public ResponseEntity<?> atualizarAmostrasLote(
            @PathVariable String protocoloLote,
            @RequestBody UpdateListaDto dto
    ) {
        Optional<Lote> updatedLote = loteService.atualizarListaAmostras(protocoloLote, dto.amostrasId());

        return updatedLote
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
