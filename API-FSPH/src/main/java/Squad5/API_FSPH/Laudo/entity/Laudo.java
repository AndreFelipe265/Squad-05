package Squad5.API_FSPH.Laudo.entity;

import Squad5.API_FSPH.Lote.entity.Lote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_laudo")
@Getter
@Setter
public class Laudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;

    @Lob
    private byte[] arquivoPdf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocolo_lote", referencedColumnName = "protocoloLote")
    private Lote lote;

}