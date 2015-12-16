package blatt2Aufgabe2;

import java.io.*;

//Startet Server unter http://localhost:1234/

public class ServerMain {
	private static final int port=1234;
	private static MySocketServer server;
	
	public static void main(String args[]) {
		try {
			server=new MySocketServer(port);
			server.listen();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}