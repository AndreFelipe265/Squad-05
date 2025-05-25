package Squad5.API_FSPH.Amostra.controller;

import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.service.AmostraService;
import Squad5.API_FSPH.exception.BusinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/amostras")
@Tag(name = "Amostras", description = "Gerenciamento de amostras")
public class AmostraController {

    private final AmostraService amostraService;

    public AmostraController(AmostraService amostraService) {
        this.amostraService = amostraService;
    }

    // Endpoint para criação de amostras.
    @PostMapping
    @Operation(summary = "Criar amostras de uma vez")
    public ResponseEntity<?> criarAmostras(@RequestBody List<CreateAmostraDto> dtos) {
        try {
            List<Amostra> salvas = amostraService.criarAmostras(dtos);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    //Gabriel Almeida esteve aqui
    // Endpoint para buscar uma amostra pelo protocolo.
    @GetMapping("/{protocoloAmostra}")
    @Operation(summary = "Busca amostra por protocolo")
    public ResponseEntity<?> buscarPorProtocolo(@PathVariable String protocoloAmostra) {
        return amostraService.buscarPorProtocolo(protocoloAmostra)
                .map(amostra -> ResponseEntity.ok(amostra))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Amostra não encontrada: " + protocoloAmostra)));
    }

    // Endpoint para listar todas as amostras cadastradas no sistema
    @GetMapping("/todas")
    @Operation(summary = "Lista todas as amostras cadastradas")
    public ResponseEntity<?> listarTodas() {
        List<Amostra> amostras = amostraService.listarTodas();
        if (amostras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Nenhuma amostra cadastrada no sistema."));
        }
        return ResponseEntity.ok(amostras);
    }

    // Endpoint para deletar uma amostra pelo protocolo.
    @DeleteMapping("/{protocoloAmostra}")
    @Operation(summary = "Deleta uma amostra por protocolo")
    public ResponseEntity<?> deletarAmostra(@PathVariable String protocoloAmostra) {
        boolean deletado = amostraService.deletarAmostra(protocoloAmostra);
        if (deletado) {
            return ResponseEntity.ok(Map.of("message", "Amostra deletada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Amostra não encontrada: " + protocoloAmostra));
        }
    }

    // Endpoint para atualizar status e/ou observação de uma amostra.
    @PatchMapping("/{protocoloAmostra}")
    @Operation(summary = "Atualiza status da amostra")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable String protocoloAmostra,
            @RequestBody UpdateAmostraDto dto) {
        try {
            boolean atualizado = amostraService.atualizarStatus(protocoloAmostra, dto);
            if (atualizado) {
                return ResponseEntity.ok(Map.of("message", "Status atualizado com sucesso"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Amostra não encontrada: " + protocoloAmostra));
            }
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}