package Squad5.API_FSPH.Lote.entity;


import Squad5.API_FSPH.Amostra.entity.Amostra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_lote")
@Getter @Setter
public class Lote {

    @Id
    private String protocoloLote; // Identificador único do Lote (gerado automaticamente)

    @Column(name = "lote_lamina")
    private boolean loteLamina; // Decidir se vai usar isso para validar se eh lote para laminas ou n

    @Column(name = "status_lote")
    private String status; // Status do lote como um total

    @Column(name = "data_envio")
    private LocalDate dataEnvio; // data que o Lote sera enviado ao LACEN

    @Column(name = "data_recebimento_lote")
    private LocalDate dataRecebimento; // adicionada por PATCH quando for recebida pelo LACEN

    @Column(name = "data_criacao")
    private LocalDate dataCriacao = LocalDate.now(); // Data de criação do Lote

    @OneToMany
    @JoinTable(
            name = "lote_amostras",
            joinColumns = @JoinColumn(name = "lote_id"),
            inverseJoinColumns = @JoinColumn(name = "amostra_id")
    )
    private List<Amostra> amostras = new ArrayList<>(); // Lista de Amostras pertencentes a essa Lote

    public Lote() {
    }

    public Lote(String protocoloLote, boolean loteLamina, String status, LocalDate dataEnvio, LocalDate dataRecebimento, LocalDate dataCriacao, List<Amostra> amostras) {
        this.protocoloLote = protocoloLote;
        this.loteLamina = loteLamina;
        this.status = status;
        this.dataEnvio = dataEnvio;
        this.dataRecebimento = dataRecebimento;
        this.dataCriacao = dataCriacao;
        this.amostras = amostras;
    }


}
