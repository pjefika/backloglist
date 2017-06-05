package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "backloglist_Defeito_Integracao_3")
public class DefeitoIntegracao {

    @Id
    private String ss;

//	@ManyToOne
//	private Tipificacao tipificacao;
    private String tipificacao;

    private String instancia;

    private Date dataAbertura;

    private Date dataVencimento;

    @Enumerated(EnumType.STRING)
    private TipoStatus status;

    @ManyToOne
    private Lote lote;

    public DefeitoIntegracao() {

    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getTipificacao() {
        return tipificacao;
    }

    public void setTipificacao(String tipificacao) {
        this.tipificacao = tipificacao;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public TipoStatus getStatus() {
        return status;
    }

    public void setStatus(TipoStatus status) {
        this.status = status;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

}
