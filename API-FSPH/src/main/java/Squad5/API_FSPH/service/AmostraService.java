package Squad5.API_FSPH.service;

import Squad5.API_FSPH.controller.CreateAmostraDto;
import Squad5.API_FSPH.controller.UpdateAmostraDto;
import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.repository.AmostraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
//declarar que é service
public class AmostraService {
    //requisitos de dominio e as regras de negócios
    private final AmostraRepository amostraRepository;

    public AmostraService(AmostraRepository amostraRepository) {
        this.amostraRepository = amostraRepository;
    }

    public UUID createAmostra(CreateAmostraDto createAmostra) {

        var entity = new Amostra(
                createAmostra.idProtocolo(),
                createAmostra.tipo(),
                java.sql.Date.valueOf(createAmostra.dataCadastro()),
                createAmostra.dataColeta(),
                createAmostra.localColeta(),
                createAmostra.municipioNome(),
                createAmostra.municipioID(),
                createAmostra.protocoloLote(),
                createAmostra.Status(),
                createAmostra.ObsStatus()
        );

        var amostraSaved = amostraRepository.save(entity);
        return amostraSaved.getIdProtocolo();
    }

    public Optional<Amostra> getIdProtocolo(String idProtocolo) {

        return amostraRepository.findById(UUID.fromString(idProtocolo));
    }

    public List<Amostra> listAmostras() {
        return amostraRepository.findAll();
    }

    public void updateAmostraById(String idProtocolo,
                                  UpdateAmostraDto updateAmostraDto) {

        var id = UUID.fromString(idProtocolo);

        var amostraEntity = amostraRepository.findById(id);

        if (amostraEntity.isPresent()) {
            var amostra = amostraEntity.get();

            if (updateAmostraDto.ObsStatus() != null) {
                amostra.setObsStatus(updateAmostraDto.ObsStatus());
            }

            if (updateAmostraDto.dataColeta() != null) {
                amostra.setDataColeta(updateAmostraDto.dataColeta());
            }

            if (updateAmostraDto.localColeta() != null) {
                amostra.setLocalColeta(updateAmostraDto.localColeta());
            }

            if (updateAmostraDto.Status() != null) {
                amostra.setStatus(updateAmostraDto.Status());
            }

            amostraRepository.save(amostra);
        }
    }

    public void deleteById(String idProtocolo) {
        var id = UUID.fromString(idProtocolo);

        var amostraExists = amostraRepository.existsById(id);

        if (amostraExists) {
            amostraRepository.deleteById(id);
        }
    }

}
