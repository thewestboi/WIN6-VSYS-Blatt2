package blatt2Aufgabe1b;

import java.lang.management.ManagementFactory;

public class MyThread extends Thread {
	private static MySocketClient client;
	private static String hostname;
	private static int port;
	private static String clientName;
	private boolean stop = false;
	private static int i = 0;

	public MyThread(String hostname, int port, String clientName) {
		MyThread.hostname = hostname;
		MyThread.port = port;
		MyThread.clientName = clientName;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void run() {
		while (!stop) {
			try {
				client = new MySocketClient(hostname, port);
				System.out.println(client
						.sendAndReceive(clientName + " " + i++ + " ProcessId@Machine: " + ManagementFactory.getRuntimeMXBean().getName() ));
				client.disconnect();
				Thread.sleep((long) (Math.random() * 1000));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
