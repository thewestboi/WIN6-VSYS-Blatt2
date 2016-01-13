package Auffgabe3_E;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import rm.requestResponse.Component;
//import rm.requestResponse.Message;

public class PrimeServer {
	//private final static int PORT=1234;
	private final static Logger LOGGER=Logger.getLogger(PrimeServer.class.getName());
	
	

 
    
	void setLogLevel(Level level) {
    	for(Handler h : LOGGER.getLogger("").getHandlers())	h.setLevel(level); 
    	LOGGER.setLevel(level);
    	LOGGER.info("Log level set to "+level);
    }

    

/*	private boolean primeService(long number) {
		for (long i = 2; i < Math.sqrt(number) + 1; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}*/
	
    public static void main(String[] args) {
    	
    	int port=0;
    	
    	for (int i = 0; i<args.length; i++)  {
			switch(args[i]) {
				case "-port": 
					try {
				        port = Integer.parseInt(args[++i]);
				    } catch (NumberFormatException e) {
				    	LOGGER.severe("port must be an integer, not "+args[i]);
				        System.exit(1);
				    }
					break;
				default:
					LOGGER.warning("Wrong parameter passed ... '"+args[i]+"'");
			}           
        }
    	
    	new ListenerThread(port).start();
    }
}
