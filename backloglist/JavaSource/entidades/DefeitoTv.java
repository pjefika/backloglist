package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "backloglist_Defeito_Tv_2")
public class DefeitoTv {

    @Id
    @NotEmpty
    @Length(min = 8, max = 10, message = "Padrão: 8-AAAAAA")
    private String ss;

//    @ManyToOne
//    private Tipificacao tipificacao;
    private String tipificacao;

    private String instancia;

    private Date dataAbertura;

    private Date dataVencimento;

    private Date dataDeIntegracao;

    private Date dataEncerrado;

    @Enumerated(EnumType.STRING)
    private TipoStatus status;

    @ManyToOne
    private UsuarioEfika usuario;

    @OneToOne
    private MotivoEncerramento motivoEncerramento;

    @OneToMany(mappedBy = "defeito")
    private List<LogDefeitoTv> logs;

    @OneToMany(mappedBy = "defeito", fetch = FetchType.EAGER)
    private List<ComentariosDefeitosTv> comentarios;

    private Boolean encerradoAdm = false;

    private Boolean encerradoDQTT;

    private Date dataDQTT;

    public DefeitoTv() {

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

    public Date getDataDeIntegracao() {
        return dataDeIntegracao;
    }

    public void setDataDeIntegracao(Date dataDeIntegracao) {
        this.dataDeIntegracao = dataDeIntegracao;
    }

    public Date getDataEncerrado() {
        return dataEncerrado;
    }

    public void setDataEncerrado(Date dataEncerrado) {
        this.dataEncerrado = dataEncerrado;
    }

    public TipoStatus getStatus() {
        return status;
    }

    public void setStatus(TipoStatus status) {
        this.status = status;
    }

    public UsuarioEfika getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEfika usuario) {
        this.usuario = usuario;
    }

    public MotivoEncerramento getMotivoEncerramento() {
        return motivoEncerramento;
    }

    public void setMotivoEncerramento(MotivoEncerramento motivoEncerramento) {
        this.motivoEncerramento = motivoEncerramento;
    }

    public List<LogDefeitoTv> getLogs() {
        return logs;
    }

    public void setLogs(List<LogDefeitoTv> logs) {
        this.logs = logs;
    }

    public List<ComentariosDefeitosTv> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentariosDefeitosTv> comentarios) {
        this.comentarios = comentarios;
    }

    public Boolean getEncerradoAdm() {
        return encerradoAdm;
    }

    public void setEncerradoAdm(Boolean encerradoAdm) {
        this.encerradoAdm = encerradoAdm;
    }

    public Boolean getEncerradoDQTT() {
        return encerradoDQTT;
    }

    public void setEncerradoDQTT(Boolean encerradoDQTT) {
        this.encerradoDQTT = encerradoDQTT;
    }

    public Date getDataDQTT() {
        return dataDQTT;
    }

    public String dataDQTTFormatada() {

        SimpleDateFormat formmater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dataFormatada;

        if (getDataDQTT() == null) {

            dataFormatada = "";

        } else {

            dataFormatada = formmater.format(getDataDQTT());

        }

        return dataFormatada;

    }

    public void setDataDQTT(Date dataDQTT) {
        this.dataDQTT = dataDQTT;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ss == null) ? 0 : ss.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DefeitoTv other = (DefeitoTv) obj;
        if (ss == null) {
            if (other.ss != null) {
                return false;
            }
        } else if (!ss.equals(other.ss)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DefeitoTv [ss=" + ss + "]";
    }

}
