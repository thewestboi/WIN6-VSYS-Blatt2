package Auffgabe3_E;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter extends Thread {
	public static AtomicInteger counter = new AtomicInteger();
	private Object monitor = new Object();

	public void incrementCounter() {
		synchronized (monitor) {
			counter.getAndIncrement();
			monitor.notify();
		}
	}

	public void decrementCounter() {
		synchronized (monitor) {
			counter.getAndDecrement();
			monitor.notify();
		}
	}

	public void run() {
		while (true) {
			synchronized (monitor) {
				try {
					monitor.wait();
					System.out.println("Threads aktiv: " + counter.get());

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}