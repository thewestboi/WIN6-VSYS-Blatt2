package Auffgabe3_E;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import rm.requestResponse.Component;
import rm.requestResponse.Message;

public class ListenerThread extends Thread {
	private final static int PORT = 1234;
	private final static Logger LOGGER = Logger.getLogger(ListenerThread.class.getName());
	private Component communication;
	private int port = PORT;
	ExecutorService executor = null;
	Counter counter = new Counter();
	// private int counter = 0;

	public ListenerThread(int port) {
		if (port > 0) {
			this.port = port;
		}
		;
		communication = new Component();

		// setLogLevel(Level.FINER);
	}

	public void run() {
		// counterThread starten //Listener starten
		counter.start();
		listen();

	}

	void listen() {
		LOGGER.info("Listening on port " + port);

		String input = "N";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("FixedPool? 10 Threads [Y/N]");
		try {
			input = reader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (input.equals("Y")) {
			executor = Executors.newFixedThreadPool(10);
		} else {
			executor = Executors.newCachedThreadPool();
		}

		while (true) {
			

			LOGGER.finer("Receiving ...");
			try {
				Message mess = communication.receive(port, true, false);

				System.out.println("Message from Port: [" + mess.getPort() + "] received!");

				executor.execute(new TaskThread(communication, mess, counter));
				System.out.println(executor.toString());

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
//		executor.shutdown();
	}

}
