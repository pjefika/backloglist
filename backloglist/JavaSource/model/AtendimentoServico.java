package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.ComentariosDefeitos;
import entidades.ComentariosDefeitosTv;
import entidades.Defeito;
import entidades.DefeitoIntegracao;
import entidades.DefeitoTv;
import entidades.LogDefeito;
import entidades.LogDefeitoTv;
import entidades.MotivoEncerramento;
import entidades.TipoLog;
import entidades.TipoStatus;
import entidades.UsuarioEfika;

@Stateless
public class AtendimentoServico {

    @PersistenceContext(unitName = "vu")
    private EntityManager entityManager;

    public AtendimentoServico() {

    }

    /*
	 * Montar lista com todos os defeitos ativos.
	 * */
    @SuppressWarnings("unchecked")
    public List<Defeito> listarDefeitosAtivos() {

        try {

            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1 ORDER BY d.dataDeIntegracao DESC");
            query.setParameter("param1", TipoStatus.ABERTO);
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<Defeito>();

        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarDefeitosAtivosTv() {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.status =:param1 ORDER BY d.dataDeIntegracao DESC");
            query.setParameter("param1", TipoStatus.ABERTO);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<DefeitoTv>();
        }

    }

    /*
	 * Montar lista com todos os defeitos ativos que estão assumidos pelo colaborador.
	 * */
    @SuppressWarnings("unchecked")
    public List<Defeito> listarDefeitosColaborador(UsuarioEfika usuario) {

        try {
            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status =:param2");
            query.setParameter("param1", usuario);
            query.setParameter("param2", TipoStatus.EMTRATAMENTO);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<Defeito>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarDefeitosTvColaborador(UsuarioEfika usuario) {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.usuario =:param1 AND d.status =:param2");
            query.setParameter("param1", usuario);
            query.setParameter("param2", TipoStatus.EMTRATAMENTO);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<DefeitoTv>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarDefeitosColaboradorTv(UsuarioEfika usuario) {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.usuario =:param1 AND d.status =:param2");
            query.setParameter("param1", usuario);
            query.setParameter("param2", TipoStatus.EMTRATAMENTO);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<DefeitoTv>();
        }

    }

    /*
	 * Assumir defeito da lista	 * */
    public UsuarioEfika assumirDefeito(Defeito defeito, UsuarioEfika usuario) throws Exception {

        usuario.setAssumidos(this.listarDefeitosColaborador(usuario));

        if (usuario.getAssumidos().size() >= 2) {
            throw new Exception("Não ã permitido assumir mais de 2 (dois) defeitos por Usuãrio!");
        }

        defeito.setStatus(TipoStatus.EMTRATAMENTO);
        defeito.setUsuario(usuario);
        this.entityManager.merge(defeito);

        LogDefeito log = new LogDefeito(defeito, TipoLog.ASSUMIR, usuario);

        this.entityManager.persist(log);

        return usuario;

    }

    public UsuarioEfika assumirDefeitoTv(DefeitoTv defeitotv, UsuarioEfika usuario) throws Exception {

        if (this.consultarSSTv(defeitotv.getSs()).getUsuario() != null) {

            throw new Exception("Defeito inexistente ou já está associado para outro usuário!");

        } 
        if (this.listarDefeitosTvColaborador(usuario).size() >= 5) {
                throw new Exception("Não é permitido assumir mais de 5 (cinco) defeitos por Usuário!");
            }

            defeitotv.setStatus(TipoStatus.EMTRATAMENTO);
            defeitotv.setUsuario(usuario);
            this.entityManager.merge(defeitotv);

            LogDefeitoTv log = new LogDefeitoTv(defeitotv, TipoLog.ASSUMIR, usuario);

            this.entityManager.persist(log);

        return usuario;

    }
    /*
	 * Finalizar atendimento do defeito.
	 * */
    public void encerrarDefeito(Defeito defeito, UsuarioEfika usuario) throws Exception {

        try {

            defeito.getMotivoEncerramento().getMotivo().isEmpty();

            Date date = new Date();

            defeito.setStatus(TipoStatus.ENCERRADO);
            defeito.setDataEncerrado(date);

            this.entityManager.merge(defeito);

            LogDefeito log = new LogDefeito(defeito, TipoLog.ENCERRAR, usuario);

            this.entityManager.persist(log);
        } catch (Exception e) {

            throw new Exception("Por favor selecione o motivo, se não existir motivo contate o administrador!");

        }

    }

    public void encerrarDefeitoTv(DefeitoTv defeito, UsuarioEfika usuario) throws Exception {

        try {

            defeito.getMotivoEncerramento().getMotivo().isEmpty();

            Date date = new Date();

            defeito.setStatus(TipoStatus.ENCERRADO);
            defeito.setDataEncerrado(date);

            this.entityManager.merge(defeito);

            LogDefeitoTv log = new LogDefeitoTv(defeito, TipoLog.ENCERRAR, usuario);

            this.entityManager.persist(log);
        } catch (Exception e) {

            throw new Exception("Por favor selecione o motivo, se não existir motivo contate o administrador!");

        }

    }

    public void enviarCampo(Defeito defeito, UsuarioEfika usuario) {

        Date date = new Date();

        defeito.setStatus(TipoStatus.ENVIADOACAMPO);
        defeito.setDataEncerrado(date);
        defeito.setMotivoEncerramento(null);

        this.entityManager.merge(defeito);

        LogDefeito log = new LogDefeito(defeito, TipoLog.ENVIADOCAMPO, usuario);

        this.entityManager.persist(log);

    }

    public void enviarCampoTv(DefeitoTv defeito, UsuarioEfika usuario) {

        Date date = new Date();

        defeito.setStatus(TipoStatus.ENVIADOACAMPO);
        defeito.setDataEncerrado(date);
        defeito.setMotivoEncerramento(null);

        this.entityManager.merge(defeito);

        LogDefeitoTv log = new LogDefeitoTv(defeito, TipoLog.ENVIADOCAMPO, usuario);

        this.entityManager.persist(log);

    }


    /*
	 * Consulta defeito por operador ss especifica
	 * */
    public Defeito consultarDefeitoOperadorPorSS(String ss, UsuarioEfika usuario) throws Exception {

        try {

            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1 AND d.usuario =:param2 AND d.status =:param3");
            query.setParameter("param1", ss);
            query.setParameter("param2", usuario);
            query.setParameter("param3", TipoStatus.EMTRATAMENTO);

            return (Defeito) query.getSingleResult();

        } catch (Exception e) {

            throw new Exception("Este defeito não exite ou não estã atribuido a sua matricula.");

        }

    }

    public DefeitoTv consultarDefeitoTvOperadorPorSS(String ss, UsuarioEfika usuario) throws Exception {

        try {

            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.ss =:param1 AND d.usuario =:param2 AND d.status =:param3");
            query.setParameter("param1", ss);
            query.setParameter("param2", usuario);
            query.setParameter("param3", TipoStatus.EMTRATAMENTO);

            return (DefeitoTv) query.getSingleResult();

        } catch (Exception e) {

            throw new Exception("Este defeito não exite ou não estã atribuido a sua matricula.");

        }

    }

    /**
     * Consulta Entidade Defeito por id SS (sem critãrios);
     *
     * @param ss
     * @return Defeito
     * @author G0034481
     * @throws Exception
     */
    public Defeito consultarSS(String ss) throws Exception {

        try {
            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1");
            query.setParameter("param1", ss);
            return (Defeito) query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Este defeito não foi integrado na ferramenta.");
        }

    }

    public DefeitoTv consultarSSTv(String ss) throws Exception {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.ss =:param1");
            query.setParameter("param1", ss);
            return (DefeitoTv) query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Este defeito não foi integrado na ferramenta.");
        }

    }

    public DefeitoIntegracao consultarSSIntegracao() throws Exception {

        try {

            Query query = this.entityManager.createQuery("FROM DefeitoIntegracao d WHERE d.status =:param1");
            query.setParameter("param1", TipoStatus.ABERTO);
            query.setMaxResults(1);
            return (DefeitoIntegracao) query.getSingleResult();

        } catch (Exception e) {

            throw new Exception("Não possui defeitos para serem integrados!");

        }

    }

    public DefeitoIntegracao consultarSSIntegracaoEspecifico(String ss) throws Exception {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoIntegracao d WHERE d.ss =:param1");
            query.setParameter("param1", ss);
            return (DefeitoIntegracao) query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception("Este defeito não foi integrado na ferramenta.");
        }

    }

    public DefeitoTv consultarSSeDefeitoTv(String ss) throws Exception {

        try {

            Query query = this.entityManager.createQuery("From DefeitoTv d WHERE d.ss =:param1");
            query.setParameter("param1", ss);
            return (DefeitoTv) query.getSingleResult();

        } catch (Exception e) {

            throw new Exception("Este defeito não foi integrado na ferramenta.");

        }

    }

    /*
	 * Lista os motivos de encerramentos.
	 * */
    @SuppressWarnings("unchecked")
    public List<MotivoEncerramento> listarMotivoEncerramento() {

        try {

            Query query = this.entityManager.createQuery("FROM MotivoEncerramento m WHERE m.status = 1");
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<MotivoEncerramento>();

        }

    }

    public void voltarDefeitoParaFila(Defeito defeito, UsuarioEfika usuario) {

        LogDefeito log = new LogDefeito(defeito, TipoLog.VOLTOUFILA, usuario);

        this.entityManager.persist(log);

        defeito.setStatus(TipoStatus.ABERTO);
        defeito.setUsuario(null);
        defeito.setMotivoEncerramento(null);
        this.entityManager.merge(defeito);

    }

    public void voltarDefeitoTvParaFila(DefeitoTv defeito, UsuarioEfika usuario) {

        LogDefeitoTv log = new LogDefeitoTv(defeito, TipoLog.VOLTOUFILA, usuario);

        this.entityManager.persist(log);

        defeito.setStatus(TipoStatus.ABERTO);
        defeito.setUsuario(null);
        defeito.setMotivoEncerramento(null);
        this.entityManager.merge(defeito);

    }

    public void realizarFulltest(Defeito defeito, UsuarioEfika usuario) {

        LogDefeito log = new LogDefeito(defeito, TipoLog.FULLTEST, usuario);
        this.entityManager.persist(log);

    }

    public void removeDefeitoAntigo(List<Defeito> defeitos) {

        for (Defeito defeito : defeitos) {

            Date date = new Date();

            defeito.setStatus(TipoStatus.VENCIDOSLA);
            defeito.setDataEncerrado(date);

            this.entityManager.merge(defeito);

            UsuarioEfika usuario = new UsuarioEfika();

            usuario = null;

            LogDefeito log = new LogDefeito(defeito, TipoLog.VENCIDO, usuario);

            this.entityManager.persist(log);

        }

    }

    @SuppressWarnings("unchecked")
    public List<Defeito> listaDefeitosAntigos() {

        try {
            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.status =:param1 AND CURRENT_DATE > d.dataSLATriagem");
            query.setParameter("param1", TipoStatus.ABERTO);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<Defeito>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<Defeito> listarRelatorioDoUsuarioAberto(UsuarioEfika usuario, TipoStatus tipoStatus) {

        try {
            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status =:param2");
            query.setParameter("param1", usuario);
            query.setParameter("param2", tipoStatus);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<Defeito>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarRelatorioDoUsuarioAbertoTv(UsuarioEfika usuario, TipoStatus tipoStatus) {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.usuario =:param1 AND d.status =:param2");
            query.setParameter("param1", usuario);
            query.setParameter("param2", tipoStatus);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<DefeitoTv>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<Defeito> listarRelatorioDoUsuario(UsuarioEfika usuario, TipoStatus tipoStatus) {

        try {
            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.status =:param2 AND d.dataEncerrado > CURRENT_DATE");
            query.setParameter("param1", usuario);
            query.setParameter("param2", tipoStatus);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<Defeito>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarRelatorioDoUsuarioTv(UsuarioEfika usuario, TipoStatus tipoStatus) {

        try {
            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.usuario =:param1 AND d.status =:param2 AND d.dataEncerrado > CURRENT_DATE");
            query.setParameter("param1", usuario);
            query.setParameter("param2", tipoStatus);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<DefeitoTv>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<Defeito> listarDefeitosEncerradosDQTT(UsuarioEfika usuario) {

        try {

            Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.usuario =:param1 AND d.encerradoAdm =:param2 AND d.encerradoDQTT =:param3 AND d.dataEncerrado > CURRENT_DATE");
            query.setParameter("param1", usuario);
            query.setParameter("param2", true);
            query.setParameter("param3", true);
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<Defeito>();

        }

    }

    @SuppressWarnings("unchecked")
    public List<DefeitoTv> listarDefeitosEncerradosDQTTtv(UsuarioEfika usuario) {

        try {

            Query query = this.entityManager.createQuery("FROM DefeitoTv d WHERE d.usuario =:param1 AND d.encerradoAdm =:param2 AND d.encerradoDQTT =:param3 AND d.dataEncerrado > CURRENT_DATE");
            query.setParameter("param1", usuario);
            query.setParameter("param2", true);
            query.setParameter("param3", true);
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<DefeitoTv>();

        }

    }

    public void inserirComentario(Defeito defeito, String detalhes) throws Exception {

        if (!detalhes.isEmpty()) {

            ComentariosDefeitos comentariosDefeitos = new ComentariosDefeitos();

            Date dataDeAgr = new Date();

            comentariosDefeitos.setDefeito(defeito);
            comentariosDefeitos.setComentario(detalhes);
            comentariosDefeitos.setUsuario(defeito.getUsuario());
            comentariosDefeitos.setData(dataDeAgr);

            this.entityManager.persist(comentariosDefeitos);

        } else {

            throw new Exception("Por favor preencha o campo detalhes!");

        }

    }

    public void inserirComentarioTv(DefeitoTv defeito, String detalhes) throws Exception {

        if (!detalhes.isEmpty()) {

            ComentariosDefeitosTv comentariosDefeitos = new ComentariosDefeitosTv();

            Date dataDeAgr = new Date();

            comentariosDefeitos.setDefeito(defeito);
            comentariosDefeitos.setComentario(detalhes);
            comentariosDefeitos.setUsuario(defeito.getUsuario());
            comentariosDefeitos.setData(dataDeAgr);

            this.entityManager.persist(comentariosDefeitos);

        } else {

            throw new Exception("Por favor preencha o campo detalhes!");

        }

    }

    @SuppressWarnings("unchecked")
    public List<ComentariosDefeitos> listarComentariosDefeito(Defeito defeito) {

        try {

            Query query = this.entityManager.createQuery("FROM ComentariosDefeitos c WHERE C.defeito =:param1");
            query.setParameter("param1", defeito);
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<ComentariosDefeitos>();

        }

    }

    @SuppressWarnings("unchecked")
    public List<ComentariosDefeitosTv> listarComentariosDefeitoTv(DefeitoTv defeito) {

        try {

            Query query = this.entityManager.createQuery("FROM ComentariosDefeitosTv c WHERE C.defeito =:param1");
            query.setParameter("param1", defeito);
            return query.getResultList();

        } catch (Exception e) {

            return new ArrayList<ComentariosDefeitosTv>();

        }

    }

}
