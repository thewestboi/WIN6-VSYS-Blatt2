package Aufgabe3_BCD;

import java.io.IOException;
import java.util.logging.Logger;

import rm.requestResponse.Component;
import rm.requestResponse.Message;

public class TaskThread extends Thread {

	private Component communication;
	private Message msg;
	public static int counter;

	public TaskThread(Component communication,Message msg) {
		this.communication = communication;
		this.msg=msg;
	}

	private final static Logger LOGGER = Logger.getLogger(TaskThread.class
			.getName());

	public void run() {
		// TODO Auto-generated method stub
		
		LOGGER.finer("Sending ...");
		try {
			System.out.println("Anzahl Threads: " + ++counter);
			
			Long request = (Long) msg.getContent();
			//sende an Client zurück
			communication.send(new Message("localhost", 0, new Boolean(
					primeService(request.longValue()))), msg.getPort(), true);
			
			//sende an Listener zurück, dass ich fertig bin
			//communication.send(new Message("localhost", 0, "fertig"),1234, true);
			System.out.println("Anzahl Threads: " + --counter);
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
