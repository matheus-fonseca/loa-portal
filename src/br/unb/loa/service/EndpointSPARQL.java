package br.unb.loa.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import br.unb.loa.util.PropertiesLoader;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;

public class EndpointSPARQL {

	private static final String DEFAULT_ENDPOINT_URL = "http://orcamento.dados.gov.br/sparql/";
	private static final Logger logger = LoggerFactory.getLogger(EndpointSPARQL.class); 
	private String endpointURL;
	
	
	public EndpointSPARQL() {
		try {
			this.endpointURL =  PropertiesLoader.getProperty("endpointURL");
		} catch (IOException e) {
			e.printStackTrace();
			this.endpointURL  = DEFAULT_ENDPOINT_URL;
		}
		
		logger.info("Using SPARQL endpoint = " + this.endpointURL);
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
