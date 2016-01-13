package Aufgabe3_BCD;

import java.io.IOException;
import java.util.logging.Logger;

import rm.requestResponse.Component;
import rm.requestResponse.Message;

public class ListenerThread extends Thread {
	private final static int PORT = 1234;
	private final static Logger LOGGER = Logger.getLogger(ListenerThread.class.getName());
	private Component communication;
	private int port = PORT;
	//private int counter = 0;

	public ListenerThread(int port) {
    	if(port>0) {
    		this.port=port;
    	};
    	communication=new Component();

//    	setLogLevel(Level.FINER);
    }

	public void run() {
		listen();
	}

	void listen() {
		LOGGER.info("Listening on port " + port);

	//	ExecutorService executor = Executors.newCachedThreadPool();

		while (true) {

			LOGGER.finer("Receiving ...");
			try {
				Message mess = communication.receive(port, true, false);
				
				System.out.println("Message from Port: ["+ mess.getPort() + "] received!");
				
				//if (!mess.getContent().equals("fertig")){
					new TaskThread(communication, mess).start();
				//	counter++;
				//}
				
				//if (mess.getContent().equals("fertig")){
					//counter--;
				//}
				//System.out.println("Anzahl Threads: " + counter);
				

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			// LOGGER.fine(request.toString()+" received.");

		}
	}

}
