package Squad5.API_FSPH.Laudo.service;

import Squad5.API_FSPH.Laudo.entity.Laudo;
import Squad5.API_FSPH.Laudo.repository.LaudoRepository;
import Squad5.API_FSPH.Lote.entity.Lote;
import Squad5.API_FSPH.Lote.repository.LoteRepository;
import Squad5.API_FSPH.exception.BusinessRuleException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class LaudoService {

    private final LaudoRepository laudoRepository;
    private final LoteRepository loteRepository;

    public LaudoService(LaudoRepository laudoRepository, LoteRepository loteRepository) {
        this.laudoRepository = laudoRepository;
        this.loteRepository = loteRepository;
    }

    public Laudo salvarLaudo(String protocoloLote, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BusinessRuleException("Arquivo do laudo não pode ser vazio.");
        }

        Lote lote = loteRepository.findById(protocoloLote)
                .orElseThrow(() -> new BusinessRuleException("Lote com protocolo '" + protocoloLote + "' não encontrado."));

        Laudo laudo = new Laudo();
        laudo.setNomeArquivo(file.getOriginalFilename());
        laudo.setArquivoPdf(file.getBytes());
        laudo.setLote(lote);

        return laudoRepository.save(laudo);
    }

    public Optional<Laudo> buscarLaudoPorId(Long id) {
        return laudoRepository.findById(id);
    }
}