package com.mborrajo.util.logging;

import java.io.StringWriter;
import java.time.Instant;

public class Log extends Object{
	
	Level level;
	String message;
	Exception exception;
	Instant time;
	LogType logType;
	
	private static String separator = "--------------------------------------------------------\n";
	
	public Log(Level level,String msg,Exception e,LogType logType){
		this.level= level;
		this.message=msg;
		this.exception = e;
		this.time = Instant.now();
		this.logType = logType;
	}
	
	public Log(Level level,String msg,Exception e){
		this(level,msg,e,LogType.EXCEPTION);
	}
	
	public Log(Level level,String msg){
		 this(level,msg,null,LogType.LEVEL_TIME_MESSAGE);
	}
	
	public Log(String msg){
		this(Level.INFO,msg,null,LogType.MESSAGE);
	}
	
	@Override public String toString(){
		
		StringWriter aux = new StringWriter();
		if (logType == LogType.EXCEPTION | logType == LogType.LEVEL_TIME_MESSAGE){
			aux.write(separator);
			aux.write("[" + level + "] - [" + time + "] --- " + message + "\n");
		}
		else if (logType == LogType.MESSAGE){
			aux.write(message);
		}
		if (logType == LogType.EXCEPTION & exception != null){
			aux.write("\tException:" + exception.getMessage() + "\n" + stackTraceString(exception));
		}
		if (logType == LogType.EXCEPTION | logType == LogType.LEVEL_TIME_MESSAGE){
			aux.write(separator);
		}
		aux.write("\n");
		
		return aux.toString();
	}
	
	private String stackTraceString(Exception e){
		StringWriter stackString = new StringWriter();
		int depth = 0;
		for(StackTraceElement element : e.getStackTrace()){
			stackString.write("\t");
			for (int i = 0 ; i <= depth ; i++){
				stackString.write("   ");
			}
			stackString.write(element.getClassName() + ":" + element.getMethodName());
			if (element.getFileName() != null){
				stackString.write(" - in file " + element.getFileName());
			}
			
			stackString.write("\n");
			depth++;
		}
		
		return stackString.toString();
	}
	
}
