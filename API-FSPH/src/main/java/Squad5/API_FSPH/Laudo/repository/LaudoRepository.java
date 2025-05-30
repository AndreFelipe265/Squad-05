package Squad5.API_FSPH.Laudo.repository;

import Squad5.API_FSPH.Laudo.entity.Laudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaudoRepository extends JpaRepository<Laudo, Long> {
    List<Laudo> findByLoteProtocoloLote(String protocoloLote);
}
