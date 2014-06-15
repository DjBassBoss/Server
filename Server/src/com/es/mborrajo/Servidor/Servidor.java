package com.es.mborrajo.Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.mborrajo.util.logging.Level;
import com.mborrajo.util.logging.Log;
import com.mborrajo.util.logging.Logger;
import com.mborrajo.util.logging.LoggerManager;

public class Servidor {
	
	public int port = 6543;
	
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
		
	private Logger logger;
	
	public Servidor(int port){
	
		this.port = port;		
		try {
			logger = LoggerManager.getLogger();
		} catch (Exception e) {
			System.err.println(new Log(Level.SEVERE, "Logger Manager Error",e));
		}
		
	}
	
	public boolean run(){
		
		serverSocket = null;
		
		logger.logMessage("Starting Server...");
		
		while(true){
			try {
			    serverSocket = new ServerSocket(port);
			} 
			catch (IOException e) {
				logger.logMessage(Level.SEVERE,"Could not listen on port: " + port,e);
			    System.exit(-1);
			}
			logger.logMessage("Socket opened on port " + port + ".\nWaiting connection...");
			
			try {
			    clientSocket = serverSocket.accept();
			    recieveMessage();
			} 
			catch (IOException e) {
				logger.logMessage("Accept failed: 4444");
				
			    System.exit(-1);
			}
		}
		
	}

	private void recieveMessage(){

		
		try {
			BufferedReader in = null;
			//PrintWriter out = null;
			//out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
			        clientSocket.getInputStream()));
			String line = in.readLine();
			logger.logMessage(line);
			processMessage(line.toString());
		} catch (IOException e) {
			logger.logMessage(Level.WARNING,"Failed to open incoming socket.",e);
		    return;
		}
		catch (Exception e){
			logger.logMessage(Level.WARNING,"Unknown Exception while receiving the message. ",e);
		    return;
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.logMessage(Level.WARNING, "Error while closing the socket." , e);
			return;
		}
	}
	
	private void processMessage(String s){
		
		String[] tokens = s.split(" ");
		for(int i = 0; i < tokens.length; i++){
			
			//System.out.println(tokens[i]);
			
		}
		if (tokens[0] != null && tokens[0].contains("GET")){
			PageServer ps = new PageServer();
			sendMessage(ps.getPage(tokens[1].substring(1)));
		}
	}
	
	private void sendMessage(String message){
		
		Socket sendSocket = clientSocket;
	    PrintWriter out = null;
	    BufferedReader in = null;
		
		try {
            out = new PrintWriter(sendSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        sendSocket.getInputStream()));
        }
        catch (IOException e) {
        	logger.logMessage(Level.WARNING, "Error while sending message.", e);
            return;
        }
		out.println("HTTP/1.1 200 OK");
		out.print(message);
		out.close();
		try{
			in.close();
			sendSocket.close();
		}catch(Exception e){
			logger.logMessage(Level.WARNING, "Error while closing sockets after sending the message.", e);
			return;
		}
	}
	
}


