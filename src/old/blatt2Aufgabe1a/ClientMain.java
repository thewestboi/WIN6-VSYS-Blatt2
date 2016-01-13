package blatt2Aufgabe1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientMain {
	private static final int port=1234;
	private static final String hostname="localhost";
	private static MySocketClient client;
	private static BufferedReader reader;

	public static void main(String args[]) {
		try {
			client=new MySocketClient(hostname,port);
			System.out.print("Client: Enter name> ");
			reader=new BufferedReader(new InputStreamReader(System.in ));
			String clientName=reader.readLine();
			System.out.println(client.sendAndReceive(clientName));
			client.disconnect();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
