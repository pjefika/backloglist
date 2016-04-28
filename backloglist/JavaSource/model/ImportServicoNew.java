package model;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.UploadedFile;

import com.opencsv.CSVReader;

import entidades.Defeito;
import entidades.DefeitoIntegracao;
import entidades.LogIntegracao;
import entidades.Tipificacao;
import entidades.TipoLogIntegracao;
import entidades.TipoStatus;
import util.JSFUtil;

@Stateless
public class ImportServicoNew {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	@EJB
	private AtendimentoServico atendimentoServico;

	public ImportServicoNew() {

	}

	public void salvaLote(UploadedFile file) throws Exception{
		try {

			byte[] conteudo = file.getContents();

			String nome = JSFUtil.gerarStringAleatoria(20);

			String fullname = "C:\\UploadedFiles\\" + nome + ".csv";

			FileOutputStream fos = new FileOutputStream(fullname);

			fos.write(conteudo);
			fos.close();

			this.importCSV(nome);

		} catch (ParseException e) {

			JSFUtil.addErrorMessage(e.getMessage());

		}

	}

	@SuppressWarnings("rawtypes")
	public void importCSV(String nomeArquivo) throws Exception {

		String[] row = null;
		String csvFilename = "C:\\UploadedFiles\\" + nomeArquivo + ".csv";

		CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';');
		List content = csvReader.readAll();


		for (Object object : content) {			

			DefeitoIntegracao defeito = new DefeitoIntegracao();
			Tipificacao tipificacao = new Tipificacao();		

			row = (String[]) object;				
			
			try {
				
				String ss = row[0];
				
				this.atendimentoServico.consultarSSIntegracaoEspecifico(ss);
				
			} catch (Exception e) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

				Date dataAbertura = formatter.parse(row[3]);			
				Date dataVencimento = formatter.parse(row[4]);				

				defeito.setSs(row[0]);
				defeito.setInstancia(row[2]);
				tipificacao = this.acaoTipificacao(row[1].trim());
				defeito.setTipificacao(tipificacao);
				defeito.setDataAbertura(dataAbertura);
				defeito.setDataVencimento(dataVencimento);
				defeito.setStatus(TipoStatus.ABERTO);				

				this.entityManager.persist(defeito);
			}

		}
		
		csvReader.close();
	}

	public Tipificacao acaoTipificacao(String nomeTipificacao) {

		Query query = this.entityManager.createQuery("FROM Tipificacao t WHERE t.nomeTipificacao =:param1");
		query.setParameter("param1", nomeTipificacao);

		Tipificacao tipificacao = new Tipificacao();	

		try {

			tipificacao = (Tipificacao) query.getSingleResult();

		} catch (Exception e) {

			tipificacao.setNomeTipificacao(nomeTipificacao);			
			this.entityManager.persist(tipificacao);		

		}

		return tipificacao;		

	}

	public void salvaLogIntegracao(DefeitoIntegracao defeitosIntegracao, TipoLogIntegracao tipoLogIntegracao) {

		LogIntegracao logIntegracao = new LogIntegracao();
		Date data = new Date();

		logIntegracao.setDefeitoIntegracao(defeitosIntegracao);
		logIntegracao.setHoraAcao(data);
		logIntegracao.setTipoLogIntegracao(tipoLogIntegracao);

		this.entityManager.persist(logIntegracao);

	}

	public void fulltest(DefeitoIntegracao defeitosIntegracao) throws Exception {		

		System.out.println("Entrou Fulltest");

		URL link;

		link = new URL("http://efika/novosite/modulos/backloglist/services/loadInstanciaBackloglist.php?instancia=" + defeitosIntegracao.getInstancia());

		BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));

		String inputLine;
		ArrayList<String> resultado = new ArrayList<String>();

		while((inputLine = in.readLine()) != null) {
			resultado.add(inputLine.trim());
		}

		String cadastroInicio = "<cadastro>";
		String cadastroFim = "</cadastro>";		
		String cadastro = resultado.get(4).substring(resultado.get(4).lastIndexOf(cadastroInicio) + cadastroInicio.length(), resultado.get(4).lastIndexOf(cadastroFim));

		String sincronismoInicio = "<sincronismo>";
		String sincronismoFim = "</sincronismo>";	
		String sincronismo = resultado.get(5).substring(resultado.get(5).lastIndexOf(sincronismoInicio) + sincronismoInicio.length(), resultado.get(5).lastIndexOf(sincronismoFim));	

		String parametrosInicio = "<parametros>";
		String parametrosFim = "</parametros>";	
		String parametros = resultado.get(6).substring(resultado.get(6).lastIndexOf(parametrosInicio) + parametrosInicio.length(), resultado.get(6).lastIndexOf(parametrosFim));

		String redeInicio = "<rede>";
		String redeFim = "</rede>";
		String rede = resultado.get(7).substring(resultado.get(7).lastIndexOf(redeInicio) + redeInicio.length(), resultado.get(7).lastIndexOf(redeFim));

		if (cadastro.equalsIgnoreCase("OK") && sincronismo.equalsIgnoreCase("OK") && parametros.equalsIgnoreCase("OK") && !rede.equalsIgnoreCase("NOK")){

			System.out.println("All Fulltest Ok");

			Defeito defeito = new Defeito();

			defeito.setSs(defeitosIntegracao.getSs());
			defeito.setTipificacao(defeitosIntegracao.getTipificacao());
			defeito.setInstancia(defeitosIntegracao.getInstancia());
			defeito.setDataAbertura(defeitosIntegracao.getDataAbertura());
			defeito.setDataVencimento(defeitosIntegracao.getDataVencimento());

			Long diferenca = defeito.getDataVencimento().getTime() - defeito.getDataAbertura().getTime();

			Double porcentagem = 0.025;
			diferenca = (long) (diferenca * porcentagem);

			diferenca = defeito.getDataAbertura().getTime() + diferenca;

			Date sla = new Date(diferenca);

			Date dataIntegracao = new Date();

			defeito.setDataSLATriagem(sla);
			defeito.setDataDeIntegracao(dataIntegracao);
			defeito.setStatus(TipoStatus.ABERTO);

			defeitosIntegracao.setStatus(TipoStatus.ENCERRADO);

			this.entityManager.merge(defeitosIntegracao);			
			this.entityManager.persist(defeito);
			salvaLogIntegracao(defeitosIntegracao, TipoLogIntegracao.INTEGRADO);

		}else{

			System.out.println("Fulltest negativo");

			defeitosIntegracao.setStatus(TipoStatus.ENCERRADO);
			this.entityManager.merge(defeitosIntegracao);
			this.salvaLogIntegracao(defeitosIntegracao, TipoLogIntegracao.NEGATIVAFULLTEST);

		}		

		in.close();

	}

	public void trocaStatusDefeitoIntegracao(DefeitoIntegracao defeitoIntegracao) {

		defeitoIntegracao.setStatus(TipoStatus.EMTRATAMENTO);
		this.entityManager.merge(defeitoIntegracao);

	}


}
