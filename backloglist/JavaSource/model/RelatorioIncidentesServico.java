package model;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.primefaces.model.UploadedFile;

import com.opencsv.CSVReader;

import entidades.Defeito;
import entidades.TipoStatus;
import util.JSFUtil;

@Stateless
public class RelatorioIncidentesServico {

	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public RelatorioIncidentesServico() {

	}

	@SuppressWarnings("unchecked")
	public List<Defeito> listaDefeitosStatusAdm(Boolean dqttEncerrado) {

		try {

			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.encerradoAdm =:param1 AND d.status =:param2");
			query.setParameter("param1", dqttEncerrado);
			query.setParameter("param2", TipoStatus.ENCERRADO);
			return query.getResultList();

		} catch (Exception e) {

			return new ArrayList<Defeito>();

		}

	}

	public void mudaStatusIncidente(Defeito defeito) {

		defeito.setEncerradoAdm(true);

		this.entityManager.merge(defeito);

	}

	public void importaDefeitosEncerradosDQTT(UploadedFile file) throws Exception {

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

			Defeito defeito = new Defeito();
			
			row = (String[]) object;

			String ss = row[0];		
			
			try {
				
				defeito = this.buscaDefeitoEspecifico(ss);
				
				defeito.setEncerradoAdm(true);
				
			} catch (Exception e) {
				
				
				
			}
					
		}

		csvReader.close();
	}

	public Defeito buscaDefeitoEspecifico(String ss) throws Exception {

		try {

			Query query = this.entityManager.createQuery("FROM Defeito d WHERE d.ss =:param1");
			query.setParameter("param1", ss);
			return (Defeito) query.getSingleResult();

		} catch (Exception e) {

			throw new Exception("Defeito não encontrado");

		}


	}

}
