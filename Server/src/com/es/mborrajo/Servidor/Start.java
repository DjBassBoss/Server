package com.es.mborrajo.Servidor;

import java.io.StringWriter;

import com.mborrajo.util.logging.Log;
import com.mborrajo.util.logging.Logger;
import com.mborrajo.util.logging.LoggerManager;

public class Start {

	private static Logger logger = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Setup Event Logging
		
		StringWriter sw = new StringWriter();
		sw.write("C:/Logs/Log-");
		sw.write(java.time.LocalDate.now().toString());
		
		LoggerManager.setFilename(sw.toString());
		LoggerManager.setLevel(com.mborrajo.util.logging.Level.ALL);
		
		try{
			logger = LoggerManager.getLogger();
			logger.logMessage("Starting log...");
			System.out.println(new Log(com.mborrajo.util.logging.Level.INFO, "Starting Logfile " + sw.toString()));
		}
		catch(Exception e){
			System.err.println("Unable to start logger.");
			System.err.println("Logger Manager Error:" + e.getMessage());
			System.err.println(new Log(com.mborrajo.util.logging.Level.SEVERE, "Logger Manager Error",e));
			System.exit(-1);
		}
		
		//Setup server
		
		Servidor server = new Servidor(6543);
		if (args.length == 1 && args[0].equals("-nogui"))
			server.run();
		else{
			server.run();
		}
		System.exit(0);
	}

}
