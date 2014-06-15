package com.es.mborrajo.Servidor.sessionControl;

import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;

public class User {
	public String username;
	public String ip;
	public Instant sessionStartInstant;
	public Instant lastActivityInstant;
	public Duration sessionDuration;
	
	public User(String username, String ip){
		this.username = username;
		this.ip = ip;
		this.sessionStartInstant = Instant.now();
		this.lastActivityInstant = Instant.now();
		this.sessionDuration = Duration.ZERO;
	}
	
	public String getStats(){
		StringWriter sw = new StringWriter();
		sw.write("Username: " + username + "\t IP:" + ip + "\n");
		sw.write("Start:" + sessionStartInstant + "\n");
		
		long s = sessionDuration.getSeconds();
		sw.write("Duration:");
		sw.write(String.format("%02d:%02d:%02d", s/3600, (s%3600)/60, (s%60)) + "\n" );
		
		sw.write("Last Activity:" + lastActivityInstant + "\n");
		
		return sw.toString();
		
		
	}
	
	public boolean isUser(String username){
		
		return (this.username == username);
		
	}
}
