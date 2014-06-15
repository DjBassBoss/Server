package com.mborrajo.util.logging;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {	
	
	private Level logLevel = Level.SEVERE;
	private String filename;
	
	private static String separator = "*************************************************************************\n";
		
	public Logger(String filename, Level logLevel) throws IOException{
		this.filename = filename;
		this.logLevel = logLevel;
		write("\n\n" + separator);
		writeln("\t\t\t\tSTARTING LOG - " + java.time.Instant.now());
		write(separator + "\n\n");
	}
		
	public boolean logMessage(Log log){
		if (log.level.compareTo(this.logLevel) <= 0){
			//LOG
			try{
				write(log);
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}
	
	public boolean logMessage(String message){
		return logMessage(new Log(message));
	}
	
	public boolean logMessage(Level level , String message){
		return logMessage(new Log(level,message));
	}
	
	public boolean logMessage(Level level , String message , Exception e){
		return logMessage(new Log(level,message,e));
	}
	
	
	private void write(Object s) throws IOException{
		FileWriter fw = new FileWriter(new File(filename) , true);
		fw.write(s.toString());
		fw.close();
	}
	private void writeln(Object s)throws IOException{
		write(s + "\n");
	}

}
