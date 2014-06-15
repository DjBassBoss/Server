package com.es.mborrajo.Servidor;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.mborrajo.util.logging.Level;
import com.mborrajo.util.logging.Log;
import com.mborrajo.util.logging.Logger;
import com.mborrajo.util.logging.LoggerManager;

public class PageServer {
	
	private Logger logger;
	
	public PageServer(){
		try {
			logger = LoggerManager.getLogger();
		} catch (Exception e) {
			System.err.println(new Log(Level.SEVERE, "Logger Manager Error",e));
		}
	}
	
	public String getPage(String s){
		String page ="";
		InputStream fis;
		BufferedReader br;
		
		logger.logMessage("PageServer:Served Page " + s );
		
		try{
			fis = new FileInputStream(s + ".xml");
			br = new BufferedReader(new InputStreamReader(fis));
			
			String line = br.readLine();
			while ( line != null){
				//System.out.println(line);
				page = page + line + "\n";
				line = br.readLine();
			}
			br.close();
			fis.close();
		}
		catch(IOException e){
			logger.logMessage(Level.WARNING,"Page Server:IO Error.",e);
		}
		return page;		
	}
	
}
