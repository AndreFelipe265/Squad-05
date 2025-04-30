package Squad5.API_FSPH.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

//declarar que é um arquivo de entidade, declarar tabela, declarar os construtores com e sem argumentos
@Entity
@Table(name = "tb_amostras")
public class Amostra {
    // criar colunas de acordo com os requisitos:
    // idProtocolo / tipo / dataCadastro / dataColeta / localColeta / municipioNome / minicipioID / protocoloLote / Status / ObsStatus

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //alterar dps pro protocolo
    private UUID idProtocolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo") //alterar para enum!!
    private Tipo tipo;

    @Column(name = "dataCadastro") //usar o tipo de Instant?
    private Date dataCadastro;

    @Column(name = "dataColeta")
    private String dataColeta;

    @Column(name = "localColeta")
    private String localColeta;

    @Column(name = "municipioNome")
    private String municipioNome;

    @Column(name = "municipioId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID municipioId;

    @Column(name = "protocoloLote")
    private String protocoloLote;

    @Column(name = "status")
    private String Status;

    @Column(name = "obsStatus")
    private String obsStatus;

    public Amostra() {
    }

    public Amostra(UUID idProtocolo, Tipo tipo, Date dataCadastro, String dataColeta, String localColeta, String municipioNome, UUID municipioId, String protocoloLote, String status, String obsStatus) {
        this.idProtocolo = idProtocolo;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
        this.dataColeta = dataColeta;
        this.localColeta = localColeta;
        this.municipioNome = municipioNome;
        this.municipioId = municipioId;
        this.protocoloLote = protocoloLote;
        Status = status;
        this.obsStatus = obsStatus;
    }

    public UUID getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(UUID idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getLocalColeta() {
        return localColeta;
    }

    public void setLocalColeta(String localColeta) {
        this.localColeta = localColeta;
    }

    public String getMunicipioNome() {
        return municipioNome;
    }

    public void setMunicipioNome(String municipioNome) {
        this.municipioNome = municipioNome;
    }

    public UUID getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(UUID municipioId) {
        this.municipioId = municipioId;
    }

    public String getProtocoloLote() {
        return protocoloLote;
    }

    public void setProtocoloLote(String protocoloLote) {
        this.protocoloLote = protocoloLote;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getObsStatus() {
        return obsStatus;
    }

    public void setObsStatus(String obsStatus) {
        this.obsStatus = obsStatus;
    }

}
