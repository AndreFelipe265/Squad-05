package Squad5.API_FSPH.Lote.repository;

import Squad5.API_FSPH.Lote.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LoteRepository extends JpaRepository<Lote, String> {

    long countByDataCriacao(LocalDate dataCriacao);

}
