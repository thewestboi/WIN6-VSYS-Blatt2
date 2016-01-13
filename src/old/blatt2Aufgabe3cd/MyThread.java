package blatt2Aufgabe3cd;

import java.io.IOException;
import java.util.logging.Logger;

import rm.requestResponse.Component;
import rm.requestResponse.Message;

public class MyThread extends Thread {
	private Logger LOGGER;
	private Component communication;
	private Long request;
	private int port;
	
	public MyThread(Logger lOGGER, Component communication,Long request, int port) {
		super();
		LOGGER = lOGGER;
		this.communication = communication;
		this.request = request;
		this.port = port;
	}

	private boolean primeService(long number) {
		for (long i = 2; i < Math.sqrt(number) + 1; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("hi");
		
		
		LOGGER.fine(request.toString() + " received.");
		LOGGER.finer("Sending ...");
		try {
			communication.send(new Message("localhost", port, new Boolean(
					primeService(request.longValue()))), port, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.fine("message sent.");
		
	}
}