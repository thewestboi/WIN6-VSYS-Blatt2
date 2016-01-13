package Aufgabe3_BCD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import rm.requestResponse.*;

public class PrimeClient {
	private static final String HOSTNAME="localhost";
	private static final int PORT=1234;
	private static final long INITIAL_VALUE=(long)1e17;
	private static final long COUNT=20;
	private static final String CLIENT_NAME=PrimeClient.class.getName();
	private static final String SYNCHRONIZED="synchronized";
	private static final String POLLING ="polling";
	private static final String ASYNCHRONIOUS="asynch";

	static Random randomGen = new Random();

	private Component communication;
	String hostname;
	int port;
	long initialValue,count;
	static String requestMode;
	int portClient;
	

	
	public PrimeClient(String hostname,int port,long initialValue,long count) {
		this.hostname=hostname;
		this.port=port;
		this.initialValue=initialValue;
		this.count=count;
		
    	 portClient= (randomGen.nextInt(1599)+4000);

	}
	
	public void run() throws ClassNotFoundException, IOException {
		communication=new Component();
		if(requestMode==POLLING){
			for (long i=initialValue;i<initialValue+count;i++) processNumberPolling(i);
		}
		else{
			if(requestMode==ASYNCHRONIOUS){
				for (long i=initialValue;i<initialValue+count;i++) processNumberAsynch(i);
			}else{
				for (long i=initialValue;i<initialValue+count;i++) processNumber(i);
			}

		}
    }
	    
    public void processNumber(long value) throws IOException, ClassNotFoundException {
    	System.out.println("Client-Port: " + portClient);
		communication.send(new Message(hostname,portClient,new Long(value)),port,false);
		Boolean	isPrime = (Boolean) communication.receive(portClient,true,true).getContent();
	
		System.out.println(value + ": "+(isPrime.booleanValue() ? "prime" : "not prime"));
	}
    public void processNumberPolling(long value) throws IOException, ClassNotFoundException {
  		communication.send(new Message(hostname,port,new Long(value)),port,false);
  		
  		boolean finished=false;
  		System.out.print(value + ": ... ");

  		while(!finished){
  			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			Message recievedMessage = communication.receive(port,false,true);
  			if( recievedMessage!= null){
  	  	  		Boolean	isPrime = (Boolean) recievedMessage.getContent();
  	  	  		finished=true;
  	  	  		System.out.println(isPrime);
  			}
  			else{
  				System.out.print(".");
  			}
  			
  		}
  	
  	}
    public void processNumberAsynch(long value) throws IOException, ClassNotFoundException {
    	//TODO: Start a thread and let him send the message, add a callback for recieve???
    	
  		communication.send(new Message(hostname,port,new Long(value)),port,false);
  		
 
  			
  		
  	
  	}
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		String hostname=HOSTNAME;
		int port=PORT;
		long initialValue=INITIAL_VALUE;
		long count=COUNT;		

		
		boolean doExit=false;
				
		String input;
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in )); 
		
		System.out.println("Welcome to "+CLIENT_NAME+"\n");
		
		while(!doExit) {
			System.out.print("Server hostname ["+hostname+"] > ");
			input=reader.readLine();
			if(!input.equals("")) hostname=input;
			
			System.out.print("Server port ["+port+"] > ");
			input=reader.readLine();
			if(!input.equals("")) port=Integer.parseInt(input);
			
			//Request Mode added to menu
			System.out.print("Request mode [ 1- " +SYNCHRONIZED+" | 2- "+POLLING+ " | 3- "+ASYNCHRONIOUS+"] > ");
			input=reader.readLine();
			if(Integer.parseInt(input)==1){
				requestMode=SYNCHRONIZED;
			}else{
				if(Integer.parseInt(input)==2){
					requestMode=POLLING;
				}
				else{
					requestMode=ASYNCHRONIOUS;
				}
			}
			
			System.out.print("Prime search initial value ["+initialValue+"] > ");
			input=reader.readLine();
			if(!input.equals("")) initialValue=Integer.parseInt(input);
			
			System.out.print("Prime search count ["+count+"] > ");
			input=reader.readLine();
			if(!input.equals("")) count=Integer.parseInt(input);
			
			new PrimeClient(hostname,port,initialValue,count).run();
			
			System.out.println("Exit [n]> ");
			input=reader.readLine();
			if(input.equals("y") || input.equals("j")) doExit=true;
		}
	}
}
	
