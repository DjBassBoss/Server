package com.es.mborrajo.Servidor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PageServer {
	
	public PageServer(){
		
		
	}
	
	public String getPage(String s) throws IOException{
		String page ="";
		
		InputStream fis = new FileInputStream(s + ".xml");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		String line = br.readLine();
		while ( line != null){
			//System.out.println(line);
			page = page + line + "\n";
			line = br.readLine();
		}
		br.close();
		fis.close();
		return page;		
	}
	
}
