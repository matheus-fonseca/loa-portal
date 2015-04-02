package br.unb.loa.service;

import java.io.IOException;

import br.unb.loa.util.PropertiesLoader;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;

public class EndpointSPARQL {

//	private static final String ENDPOINT_URL = "http://localhost:8890/sparql/";
	private String endpointURL;
	
	public EndpointSPARQL() {
		try {
			this.endpointURL =  PropertiesLoader.getProperty("endpointURL");
		} catch (IOException e) {
			e.printStackTrace();
			this.endpointURL  = "http://orcamento.dados.gov.br/sparql/";
		}
		
		System.out.println(this.endpointURL);
	}
	
	public ResultSet execSPARQLQuery(String query){
		QueryExecution queryExecution;
		ResultSet result;
		
		try {
			
			queryExecution = QueryExecutionFactory.sparqlService(endpointURL, query);
			result = queryExecution.execSelect();
		} catch(Exception e){
			e.printStackTrace();
			result = null;
		}
			
		return result;
	}
}
