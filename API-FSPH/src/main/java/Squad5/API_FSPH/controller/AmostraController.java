package Squad5.API_FSPH.controller;

import Squad5.API_FSPH.dto.CreateAmostraDto;
import Squad5.API_FSPH.dto.UpdateAmostraDto;
import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.service.AmostraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<String> criarAmostra(@RequestBody CreateAmostraDto dto) {
        String protocolo = amostraService.criarAmostra(dto);
        return ResponseEntity.ok().body(protocolo); // Retorna o protocolo da amostra criada
    }

    // Endpoint para buscar uma amostra pelo protocolo.
    @GetMapping("/{protocolo}")
    @Operation(summary = "Busca amostra por protocolo")
    public ResponseEntity<Amostra> buscarPorProtocolo(@PathVariable String protocolo) {
        return amostraService.buscarPorProtocolo(protocolo)
                .map(amostra -> ResponseEntity.ok().body(amostra)) // Evita ambiguidade do método .ok()
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrar
    }

    // Endpoint para deletar uma amostra pelo protocolo.
    @DeleteMapping("/{protocolo}")
    @Operation(summary = "Deleta uma amostra por protocolo")
    public ResponseEntity<Void> deletarAmostra(@PathVariable String protocolo) {
        amostraService.deletarAmostra(protocolo);
        return ResponseEntity.noContent().build(); // 204 No Content se deletado com sucesso
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
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable String protocolo,
            @RequestBody UpdateAmostraDto dto) {
        amostraService.atualizarStatus(protocolo, dto);
        return ResponseEntity.noContent().build(); // 204 No Content ao atualizar com sucesso
    }
}
