package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import entidades.Defeito;

@Stateless
public class IntegracaoServico {
	
	@PersistenceContext(unitName="vu")  
	private EntityManager entityManager;

	public IntegracaoServico() {
		
	}	
	
	public void trataExcel() throws FileNotFoundException{		
		
		ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
		strat.setType(Defeito.class);
		String[] columns = new String[] {"ss, tipificacao, instancia, dataAbertura, dataVencimento"};
		strat.setColumnMapping(columns);
		
		
		CsvToBean csv = new CsvToBean();
		
		String csvFileName = "C:\\UploadedFiles\\File.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFileName));
		
		List list = csv.parse(strat, csvReader);
		
		for (Object object : list) {
			
			Defeito defeito = (Defeito) object;
									
		}
	}
	
	public void salvaIntegracao(Defeito defeito) {		
		
		this.entityManager.persist(defeito);		
		
	}
	
	

}
