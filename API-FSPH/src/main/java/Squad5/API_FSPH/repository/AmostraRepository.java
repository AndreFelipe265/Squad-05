package Squad5.API_FSPH.repository;

import Squad5.API_FSPH.entity.Amostra;
import Squad5.API_FSPH.entity.Tipo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//declarar que é um repositorio
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, UUID> {
    // Corrigindo os métodos com a sintaxe correta
    List<Amostra> findByProtocoloLote(String protocoloLote);

    boolean existsByProtocoloLoteAndTipo(String protocoloLote, Tipo tipo);

    // Métod para verificar se existem outros tipos no lote
    boolean existsByProtocoloLoteAndTipoNot(String protocoloLote, Tipo tipo);

    // Métod para contar amostras por lote
    long countByProtocoloLote(String protocoloLote);
}

