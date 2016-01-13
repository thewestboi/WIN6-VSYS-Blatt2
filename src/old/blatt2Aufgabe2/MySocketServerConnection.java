package blatt2Aufgabe2;

import java.io.*;
import java.net.*;

public class MySocketServerConnection extends Thread {
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private String path;
	private FileWriter fw;
	private String basePath;

	public MySocketServerConnection(Socket socket) throws IOException {
		this.socket = socket;
		
		basePath = new File("").getAbsolutePath()
				+ "\\src\\blatt2Aufgabe2\\";
		
		fw = new FileWriter(basePath + "temp.txt", true);
		
		
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		path = br.readLine();
		System.out.println("Request: " + path);
		if (path != null && path.length() > 14) {
			path = (String) path.subSequence(4, path.length() - 9);
		} else {
			path = "/index.html";
		}
		System.out.println("requested path: " + path);

		System.out.println("Server: incoming connection accepted.");
	}

	public void run() {
		try {

			if (path.equals("/") || path.equals("")) {
				path = "/index.html";
			}


			BufferedReader br = new BufferedReader(new FileReader(basePath
					+ path));
			StringBuilder stringBuilder = new StringBuilder();

			String line = "";

			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}

			br.close();

			objectOutputStream.writeObject("HTTP/1.1 200 OK \r\n\r\n "
					+ stringBuilder.toString());

		} catch (FileNotFoundException e) {
			try {
				System.out.println("Could not find " + path);
				fw.write("Could not find " + path + "\r\n");
				fw.flush();
				objectOutputStream
						.writeObject("HTTP/1.1 404 Not Found \r\n\r\n <html><body>Could not find "
								+ path + "</body></html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
