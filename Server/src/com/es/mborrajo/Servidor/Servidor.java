package com.es.mborrajo.Servidor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Servidor {

	public int port = 6543;
	
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private JTextArea text = null;
	
	private boolean windowCreated = false;
	
	public boolean createLog = true;
	public String logFile = "log.txt";
	
	public Servidor(int port){
	
		this.port = port;		
		
	}
	
	public void createWindow(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(300, 400);
		frame.setTitle("Servidor");
		text = new JTextArea();
		text.setEditable(false);
		
		frame.add(text);
		
		frame.setVisible(true);
		windowCreated = true;
	}
	
	public boolean run(){
		
		serverSocket = null;
		
		appendText("Starting Server...");
		
		while(true){
			try {
			    serverSocket = new ServerSocket(port);
			} 
			catch (IOException e) {
				appendText("Could not listen on port: " + port);
				appendText(e.getMessage());
			    System.exit(-1);
			}
			appendText("Socket opened on port " + port + ".\nWaiting connection...");
			try {
			    clientSocket = serverSocket.accept();
			    recieveMessage();
			} 
			catch (IOException e) {
			    appendText("Accept failed: 4444");
			    System.exit(-1);
			}
		}
		
	}
	
	private void appendText(String s){
		if (windowCreated) text.setText(text.getText() + "\n" + s);
		System.out.println(s);
	}
	
	private void recieveMessage(){

		BufferedReader in = null;
		PrintWriter out = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
			        clientSocket.getInputStream()));
			String line = in.readLine();
			appendText(line.toString());
			parseMessage(line.toString());
		} catch (IOException e) {
			appendText("Failed to open incoming socket.");
		    return;
		}
		catch (Exception e){
			appendText("Exception: " + e.getMessage());
		    return;
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			appendText(e.getMessage());
			appendText(e.getStackTrace().toString());
			return;
		}
	}
	
	private void parseMessage(String s){
		
		String[] tokens = s.split(" ");
		for(int i = 0; i < tokens.length; i++){
			
			//System.out.println(tokens[i]);
			
		}
		if (tokens[0] != null && tokens[0].contains("GET")){
			PageServer ps = new PageServer();
			try {
				sendMessage(ps.getPage(tokens[1].substring(1)));
			}
			catch (FileNotFoundException e){
				sendMessage("File not found on server");
			}
			catch (IOException e) {
				appendText(e.getMessage());
				return;
			}
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
        	appendText(e.getMessage());
            return;
        }
		out.println("HTTP/1.1 200 OK");
		out.print(message);
		out.close();
		try{
			in.close();
			sendSocket.close();
		}catch(Exception e){
			appendText(e.getMessage());
			return;
		}
	}
	
}


