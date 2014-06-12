package com.mborrajo.util.logging;

import java.io.IOException;

public class LoggerManager {
	
	private static Logger logger;
	private static String filename;
	private static Level level;
	
	
	public static Logger getLogger() throws Exception{
		
		if (filename == null){
			throw new Exception("Cannot initialice Logger: Filename not defined.");
		}
		if (level == null){
			level = Level.SEVERE;
		}
		
		if (logger == null){
			try{
				createLogger();
			}
			catch(IOException e){
				throw new Exception("Cannot initialice Logger.\nIOException:" + e.getMessage());
			}
			
		}
		return logger;
	}
	
	public static void createLogger() throws IOException{
		logger = new Logger(filename, level);
	}
	
	public static void setFilename(String fn){ filename = fn;}
	
	public static void setLevel(Level l){ level = l;}
	
	public static String getFilename(){ return filename;}
	
	public static Level getLevel(){ return level;}
	
	
}
