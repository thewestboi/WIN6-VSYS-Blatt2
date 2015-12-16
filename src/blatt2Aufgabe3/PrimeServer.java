package blatt2Aufgabe3;

import java.io.IOException;
import java.util.logging.*;

import rm.requestResponse.*;

public class PrimeServer {
	private final static int PORT = 1234;
	private final static Logger LOGGER = Logger.getLogger(PrimeServer.class.getName());

	private Component communication;
	private int port = PORT;

	PrimeServer(int port) {
		communication = new Component();
		if (port > 0)
			this.port = port;

		// setLogLevel(Level.FINER);
		setLogLevel(Level.SEVERE);
	}

	private boolean primeService(long number) {
		for (long i = 2; i < Math.sqrt(number) + 1; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

	void setLogLevel(Level level) {
		for (Handler h : Logger.getLogger("").getHandlers())
			h.setLevel(level);
		LOGGER.setLevel(level);
		LOGGER.info("Log level set to " + level);
	}

	void listen() {
		LOGGER.info("Listening on port " + port);

		while (true) {
			Long request = null;

			LOGGER.finer("Receiving ...");
			try {
				request = (Long) communication.receive(port, true, false).getContent();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			LOGGER.fine(request.toString() + " received.");

			LOGGER.finer("Sending ...");
			try {
				communication.send(new Message("localhost", port, new Boolean(primeService(request.longValue()))),
						port, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOGGER.fine("message sent.");
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
