package blatt2Aufgabe3cd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
	private static int MAX_THREADS;
	private static boolean done;

	public MyThreadPool(int mAX_THREADS) {
		super();
		MAX_THREADS = mAX_THREADS;
	}

	public static void setDone(boolean done) {
		MyThreadPool.done = done;
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
		while (!done) {
			executor.execute(new Task());
		}
		executor.shutdown();
	}
}