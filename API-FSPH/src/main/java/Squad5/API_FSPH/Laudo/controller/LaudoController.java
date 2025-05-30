package Squad5.API_FSPH.Laudo.controller;

import Squad5.API_FSPH.Laudo.entity.Laudo;
import Squad5.API_FSPH.Laudo.service.LaudoService;
import Squad5.API_FSPH.exception.BusinessRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/laudo")
@Tag(name = "Laudos", description = "Gerenciamento de Laudo")
public class LaudoController {

    private final LaudoService laudoService;

    public LaudoController(LaudoService laudoService) {
        this.laudoService = laudoService;
    }

    @PostMapping(value = "/upload/{protocoloLote}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload do Laudo")
    public ResponseEntity<String> uploadLaudo(@PathVariable String protocoloLote,
                                              @RequestPart("file") MultipartFile file) {
        try {
            Laudo laudoSalvo = laudoService.salvarLaudo(protocoloLote, file);
            return ResponseEntity.ok("Laudo salvo com sucesso. ID: " + laudoSalvo.getId());
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body("Erro de negócio: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download do Laudo")
    public ResponseEntity<byte[]> downloadLaudo(@PathVariable Long id) {
        Optional<Laudo> laudoOpt = laudoService.buscarLaudoPorId(id);
        if (laudoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Laudo laudo = laudoOpt.get();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + laudo.getNomeArquivo() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(laudo.getArquivoPdf());
    }
}