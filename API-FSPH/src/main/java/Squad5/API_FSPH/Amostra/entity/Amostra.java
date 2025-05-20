package Squad5.API_FSPH.Amostra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_amostras")
@Getter @Setter
public class Amostra {

    @Id
    private String protocoloAmostra; // Identificador único da amostra (gerado automaticamente)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo; // Enum que define o tipo da amostra

    @Column(name = "local_captura", nullable = false)
    private String localCaptura; // Novo campo: descrição do local onde foi capturada (ex: casa do fulano)

    @Column(name = "endereco_captura", nullable = false)
    private String enderecoCaptura; // Local onde foi capturada

    @Column(name = "data_captura", nullable = false)
    private LocalDate dataCaptura; // Data da captura

    @Column(name = "municipio_id", nullable = false)
    private UUID municipioId;

    @Column(name = "municipio_nome", nullable = false)
    private String municipioNome;

    @Column(name = "protocolo_lote")
    private String protocoloLote;
    /** fazer add o protocolo do lote quando a amostra for cadastrada em um */

    @Column(name = "status")
    private String status; // Status atual da amostra (ex: CADASTRADA, PRAZO_EXCEDIDO)

    @Column(name = "observacao")
    private String observacao; // Observações adicionais sobre a amostra

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao = LocalDate.now(); // Data de criação da amostra


    public Amostra() {
    }

    public Amostra(String protocoloAmostra, Tipo tipo, String localCaptura, String enderecoCaptura, LocalDate dataCaptura, UUID municipioId, String municipioNome, String protocoloLote, String status, String observacao, LocalDate dataCriacao) {
        this.protocoloAmostra = protocoloAmostra;
        this.tipo = tipo;
        this.localCaptura = localCaptura;
        this.enderecoCaptura = enderecoCaptura;
        this.dataCaptura = dataCaptura;
        this.municipioId = municipioId;
        this.municipioNome = municipioNome;
        this.protocoloLote = protocoloLote;
        this.status = status;
        this.observacao = observacao;
        this.dataCriacao = dataCriacao;
    }

}
