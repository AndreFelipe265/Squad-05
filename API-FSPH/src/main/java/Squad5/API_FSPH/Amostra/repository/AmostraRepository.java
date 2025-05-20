package Squad5.API_FSPH.Amostra.repository;

import Squad5.API_FSPH.Amostra.entity.Amostra;
import Squad5.API_FSPH.Amostra.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface AmostraRepository extends JpaRepository<Amostra, String> {

    Optional<Amostra> findByProtocoloAmostra(String protocoloAmostra);

    // Verifica se já existe amostra do mesmo tipo no lote
    boolean existsByProtocoloLoteAndTipo(String protocoloLote, Tipo tipo);

    // Verifica se existe outro tipo diferente de LAMINAS_PCE no lote
    boolean existsByProtocoloLoteAndTipoNot(String protocoloLote, Tipo tipo);

    // Busca todas as amostras de um município
    List<Amostra> findByMunicipioId(UUID municipioId);

    // Conta quantas amostras foram criadas em uma data específica
    long countByDataCriacao(LocalDate data);
}
