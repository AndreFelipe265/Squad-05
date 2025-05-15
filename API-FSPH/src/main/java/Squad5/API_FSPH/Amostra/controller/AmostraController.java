package Squad5.API_FSPH.Amostra.controller;

import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.service.AmostraService;
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

    // Endpoint para criação de uma nova amostra.
    @PostMapping
    @Operation(summary = "Cria uma nova amostra")
    public ResponseEntity<Amostra> criar(@RequestBody @Valid CreateAmostraDto dto) {
        Amostra amostra = amostraService.criarAmostra(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(amostra);
    }

    // Endpoint para buscar uma amostra pelo protocolo.
    @GetMapping("/{protocolo}")
    @Operation(summary = "Busca amostra por protocolo")
    public ResponseEntity<Amostra> buscarPorProtocolo(@PathVariable String protocolo) {
        return amostraService.buscarPorProtocolo(protocolo)
                .map(amostra -> ResponseEntity.ok().body(amostra)) // Evita ambiguidade do métod .ok()
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrar
    }

    // Endpoint para deletar uma amostra pelo protocolo.
    @DeleteMapping("/{protocolo}")
    @Operation(summary = "Deleta uma amostra por protocolo")
    public ResponseEntity<?> deletarAmostra(@PathVariable String protocolo) {
        boolean deletado = amostraService.deletarAmostra(protocolo);
        if (deletado) {
            return ResponseEntity.ok(Map.of("message", "Amostra deletada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Amostra não encontrada: " + protocolo));
        }
    }

    // Endpoint para listar amostras por município.

    @GetMapping("/municipio/{municipioId}")
    @Operation(summary = "Lista amostras por município")
    public ResponseEntity<List<Amostra>> listarPorMunicipio(@PathVariable UUID municipioId) {
        List<Amostra> amostras = amostraService.listarPorMunicipio(municipioId);
        return amostras.isEmpty() ?
                ResponseEntity.noContent().build() : // 204 se lista estiver vazia
                ResponseEntity.ok().body(amostras);  // Retorna lista com 200
    }

    // Endpoint para atualizar status e/ou observação de uma amostra.
    @PatchMapping("/{protocolo}")
    @Operation(summary = "Atualiza status da amostra")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable String protocolo,
            @RequestBody UpdateAmostraDto dto) {
        boolean atualizado = amostraService.atualizarStatus(protocolo, dto);
        if (atualizado) {
            return ResponseEntity.ok(Map.of("message", "Status atualizado com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Amostra não encontrada: " + protocolo));
        }
    }
}
