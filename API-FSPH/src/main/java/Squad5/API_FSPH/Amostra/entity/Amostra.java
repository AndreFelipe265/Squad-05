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
    private String protocolo; // Identificador único da amostra (gerado automaticamente)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo; // Enum que define o tipo da amostra

    @Column(name = "local_captura", nullable = false)
    private String localCaptura; // Novo campo: descrição do local onde foi capturada (ex: casa do fulano)

    @Column(name = "endereco_captura", nullable = false)
    private String enderecoCaptura; // Local onde foi capturada

    @Column(name = "data_captura", nullable = false)
    private LocalDate dataCaptura; // Data da captura

    @Column(name = "inseto_larva")
    private String insetoLarva; // Informações adicionais (ex: tipo de larva) (ex: caso de escorpiao "esmagado")

    @Column(name = "municipio_id", nullable = false)
    private UUID municipioId;

    @Column(name = "municipio_nome", nullable = false)
    private String municipioNome;

    @Column(name = "protocolo_lote", nullable = false)
    private String protocoloLote;

    @Column(name = "status")
    private String status; // Status atual da amostra (ex: CADASTRADA, PRAZO_EXCEDIDO)

    @Column(name = "observacao")
    private String observacao; // Observações adicionais sobre a amostra

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao = LocalDate.now(); // Data de criação da amostra
}
