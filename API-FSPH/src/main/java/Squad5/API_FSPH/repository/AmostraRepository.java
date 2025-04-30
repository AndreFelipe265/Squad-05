package Squad5.API_FSPH.repository;

import Squad5.API_FSPH.entity.Amostra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

//declarar que é um repositorio
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, UUID> {

}
