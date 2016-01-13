package blatt2Aufgabe1b;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientMain {
	private static final int port=1234;
	private static final String hostname="localhost";
	private static BufferedReader reader;

	public static void main(String args[]) {
		try {
			System.out.print("Client: Enter name> ");
			reader=new BufferedReader(new InputStreamReader(System.in ));
			String clientName=reader.readLine();
			MyThread myThread = new MyThread(hostname, port, clientName);
			myThread.start();
			reader.readLine();
			myThread.setStop(true);
			System.out.println("Stopped");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
