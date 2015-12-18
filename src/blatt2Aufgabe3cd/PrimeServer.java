package blatt2Aufgabe3cd;

import java.io.IOException;
import java.util.logging.*;

import rm.requestResponse.*;

public class PrimeServer {
	private final static int PORT = 1234;
	private final static Logger LOGGER = Logger.getLogger(PrimeServer.class
			.getName());

	private Component communication;
	private int port = PORT;
	public int threads = 0;
	
//	MyThreadPool myThreadPool = new MyThreadPool(8);

	PrimeServer(int port) {
		communication = new Component();
		if (port > 0)
			this.port = port;

		// setLogLevel(Level.FINER);
		setLogLevel(Level.SEVERE);
	}

		void setLogLevel(Level level) {
		for (Handler h : Logger.getLogger("").getHandlers())
			h.setLevel(level);
		LOGGER.setLevel(level);
		LOGGER.info("Log level set to " + level);
	}

	void listen() {
		LOGGER.info("Listening on port " + port);
		MyThread[] myThread = new MyThread[1000];
		
		while (true) {
			Long request = null;

			LOGGER.finer("Receiving ...");
			try {
				request = (Long) communication.receive(port, true, false)
						.getContent();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
			if (request != null) { //if message received
				//do stuff in new thread				
				myThread[threads] = new MyThread(LOGGER, communication, request, port);
				myThread[threads++].run();
				System.out.println("Threads: " + threads); //todo
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Server");
		int port = 0;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-port":
				try {
					port = Integer.parseInt(args[++i]);
				} catch (NumberFormatException e) {
					LOGGER.severe("port must be an integer, not " + args[i]);
					System.exit(1);
				}
				break;
			default:
				LOGGER.warning("Wrong parameter passed ... '" + args[i] + "'");
			}
		}

		new PrimeServer(port).listen();
	}
}
