package br.unb.loa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesLoader {
    
	private static final String ARQUIVO_PROPERTIES = "ambiente.properties";
	
    public static String getProperty(String nome) throws IOException {
        Properties properties = new Properties();  
        String valorPropriedade = null;
        InputStream in;
        
        in = new FileInputStream(System.getProperty("jboss.server.config.dir") + "/" + ARQUIVO_PROPERTIES);
        	
        properties.load(in);
        valorPropriedade = properties.getProperty(nome);
        
        return valorPropriedade;   
    }
	
}
