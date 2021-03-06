package controllers;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import entidades.Defeito;
import entidades.DefeitoTv;
import entidades.TipoStatus;
import model.AtendimentoServico;
import model.RotinasServicos;
import util.JSFUtil;

@ManagedBean
@ApplicationScoped
@Singleton
public class PainelDefeitosBean {

    public List<Defeito> listaDefeitos;

    public List<DefeitoTv> listaDefeitosTv;

    Timer timerBuscaDefeitosAtivos = new Timer();
    //Timer timerRemoveDefeitoAntigo = new Timer();

    TimerTask buscaDefeitoAtivos = new TimerTask() {

        @Override
        public void run() {

            buscarDefeitosAtivos();
            buscarDefeitosAtivosTv();

        }
    };

    /*TimerTask removeDefeitoAntigo = new TimerTask() {

		@Override
		public void run() {

			removeDefeitoAntigo();

		}
	};*/
    @EJB
    private AtendimentoServico atendimentoServico;

    @EJB
    private RotinasServicos rotinasServicos;

//	@EJB
//	private ImportServicoNew importServicoNew;
    public PainelDefeitosBean() {

    }

    @PostConstruct
    public void init() {

        //buscarDefeitosAtivos();
        //buscarDefeitosAtivosTv();
        timerBuscaDefeitosAtivos.scheduleAtFixedRate(buscaDefeitoAtivos, 5000, 80000);
        //timerRemoveDefeitoAntigo.scheduleAtFixedRate(removeDefeitoAntigo, 30000, 30000);
        //timerVoltaDefeitoParaFila.scheduleAtFixedRate(voltaDefeitoParaFila, 5000, 5000);

    }

    public void buscarDefeitosAtivos() {

        this.listaDefeitos = this.atendimentoServico.listarDefeitosAtivos();

    }

    public void buscarDefeitosAtivosTv() {

        this.listaDefeitosTv = this.atendimentoServico.listarDefeitosAtivosTv();

    }

    /*public void removeDefeitoAntigo() {

		List<Defeito> listaDefeitosAntigos = new ArrayList<Defeito>();

		listaDefeitosAntigos = this.atendimentoServico.listaDefeitosAntigos();

		this.atendimentoServico.removeDefeitoAntigo(listaDefeitosAntigos);

		for (Defeito defeito : listaDefeitosAntigos) {

			this.listaDefeitos.remove(defeito);

		}

	}*/
    public void voltaDefeito() {

        this.rotinasServicos.voltarDefeitoParaFila(this.rotinasServicos.listarDefeitosAssumidos());

    }

    public void removeDefeitoAoAssumir(Defeito defeito) {

        Defeito status = new Defeito();

        try {

            status = this.atendimentoServico.consultarSS(defeito.getSs());

            TipoStatus statusValue = status.getStatus();

            if (statusValue.equals(TipoStatus.EMTRATAMENTO)) {

                this.listaDefeitos.remove(defeito);

                this.buscarDefeitosAtivos();

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage(e.getMessage());
        }

    }

    public void removeDefeitoAoAssumirTv(DefeitoTv defeito) {

        DefeitoTv status = new DefeitoTv();

        try {

            status = this.atendimentoServico.consultarSSTv(defeito.getSs());

            TipoStatus statusValue = status.getStatus();

            if (statusValue.equals(TipoStatus.EMTRATAMENTO)) {

                this.listaDefeitos.remove(defeito);

                this.buscarDefeitosAtivosTv();

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage(e.getMessage());
        }

    }

    public List<Defeito> getListaDefeitos() {
        return listaDefeitos;
    }

    public void setListaDefeitos(List<Defeito> listaDefeitos) {
        this.listaDefeitos = listaDefeitos;
    }

    public List<DefeitoTv> getListaDefeitosTv() {
        return listaDefeitosTv;
    }

    public void setListaDefeitosTv(List<DefeitoTv> listaDefeitosTv) {
        this.listaDefeitosTv = listaDefeitosTv;
    }

}
