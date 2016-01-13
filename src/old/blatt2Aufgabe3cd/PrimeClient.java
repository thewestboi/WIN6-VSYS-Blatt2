package blatt2Aufgabe3cd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import rm.requestResponse.*;

public class PrimeClient extends Thread {
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 1234;
	private static final long INITIAL_VALUE = (long) 1e17;
	private static final long COUNT = 5; // default 20
	private static final String CLIENT_NAME = PrimeClient.class.getName();
	private static final String REQUESTmODE = "a";
	private static Object lock = new Object();

	private Component communication;
	String hostname;
	int port;
	long initialValue, count;
	String requestMode;

	public PrimeClient(String hostname, int port, long initialValue, long count, String requestMode) {
		this.hostname = hostname;
		this.port = port;
		this.initialValue = initialValue;
		this.count = count;
		this.requestMode = requestMode;
	}

	public void run() {
		communication = new Component();
		for (long i = initialValue; i < initialValue + count; i++)
			try {
				processNumber(i);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
	}

	public void processNumber(long value) throws IOException, ClassNotFoundException {
		Boolean isPrime;
		switch (requestMode) {
		default:
		case "a":
			communication.send(new Message(hostname, port, new Long(value)), port, false);
			isPrime = (Boolean) communication.receive(port, true, true).getContent();

			System.out.println(value + ": " + (isPrime.booleanValue() ? "prime" : "not prime"));
			break;

		case "c":
			System.out.print(value + ": ");

			communication.send(new Message(hostname, port, new Long(value)), port, false);
			Message answer = null;
			do {
				try {
					answer = communication.receive(port, false, true);
					System.out.print(".");
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (answer == null);

			isPrime = (Boolean) answer.getContent();

			System.out.println((isPrime.booleanValue() ? " prime" : " not prime"));
			break;

		case "d":
			Component communication = new Component();
			System.out.print(value + ": ");
			processNumberThread myThread = new processNumberThread();
			myThread.start();
			synchronized (lock) {
				try {
					communication.send(new Message(hostname, port, new Long(value)), port, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					isPrime = (Boolean) communication.receive(port, true, true).getContent();
					System.out.println((isPrime ? " prime" : " not prime"));
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
			myThread.setStop(true);
			break;
		}
	}


	private class processNumberThread extends Thread {
		private boolean stop = false;
		
		protected void setStop(boolean stop) {
			this.stop = stop;
		}

		@Override
		public void run() {
			while (!stop) {
				System.out.print(".");
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) throws IOException, ClassNotFoundException {
		System.out.println("Client");
		String hostname = HOSTNAME;
		int port = PORT;
		long initialValue = INITIAL_VALUE;
		long count = COUNT;
		String requestMode = REQUESTmODE;

		boolean doExit = false;

		String input;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to " + CLIENT_NAME + "\n");

		while (!doExit) {
			System.out.print("Server hostname [" + hostname + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				hostname = input;

			System.out.print("Server port [" + port + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				port = Integer.parseInt(input);

			System.out.print("Request mode Synchronized (a|c|d) [" + requestMode + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				requestMode = (String) input;

			System.out.print("Prime search initial value [" + initialValue + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				initialValue = Integer.parseInt(input);

			System.out.print("Prime search count [" + count + "] > ");
			input = reader.readLine();
			if (!input.equals(""))
				count = Integer.parseInt(input);

			new PrimeClient(hostname, port, initialValue, count, requestMode).run();

			System.out.println("Exit [n]> ");
			input = reader.readLine();
			if (input.equals("y") || input.equals("j"))
				doExit = true;
		}
	}
}
