package com.es.mborrajo.Servidor;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Servidor server = new Servidor(6543);
		if (args.length == 1 && !args[0].equals("-nogui"))	server.createWindow();
		server.run();
		System.exit(0);
	}

}
