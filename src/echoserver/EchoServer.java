package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private static class ClientThread extends Thread {
		Socket clientSocket;
		
		public ClientThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			try {
				InputStream inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream();
				
				int nextByte;
				while ((nextByte = inputStream.read()) != -1) {
					outputStream.write(nextByte);
				}
				
				clientSocket.close();
				System.out.println("Client disconnected");
				
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
			
		}
	}
	
	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Client connected");
			ClientThread currentThread = new ClientThread(socket);
			currentThread.run();
		}
	}
}