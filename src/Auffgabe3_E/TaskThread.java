package Auffgabe3_E;


import java.io.IOException;
import java.util.logging.Logger;

import rm.requestResponse.Component;
import rm.requestResponse.Message;

public class TaskThread implements Runnable  {

	private Component communication;
	private Message msg;
	public static int counter;
	private Counter c;

	public TaskThread(Component communication,Message msg, Counter c) {
		this.c=c;
		this.communication = communication;
		this.msg=msg;
		c.incrementCounter();
	}

	private final static Logger LOGGER = Logger.getLogger(TaskThread.class
			.getName());

	public void run() {
		// TODO Auto-generated method stub
		
		LOGGER.finer("Sending ...");
		try {

			Long request = (Long) msg.getContent();
			//sende an Client zurück
			communication.send(new Message("localhost", 0, new Boolean(
					primeService(request.longValue()))), msg.getPort(), true);

			c.decrementCounter();
			
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fine("message sent.");

		}

		
	}

	private boolean primeService(long number) {
		for (long i = 2; i < Math.sqrt(number) + 1; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

}
