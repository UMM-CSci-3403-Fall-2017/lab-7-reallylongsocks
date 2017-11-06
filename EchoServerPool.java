package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EchoServerPool  {
	private static final int PORT_NUMBER = 6013;
	private  static ExecutorService pool;
    private ServerSocket serverSocket;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServerPool server = new EchoServerPool();
		server.start();
	}

	private void start() {
		try {
			 serverSocket = new ServerSocket(PORT_NUMBER);
			 pool = Executors.newFixedThreadPool(50);
			 
			 while (true) {
				 pool.execute(new Handler(serverSocket.accept()));
			 }
		} catch (IOException e) {
			pool.shutdown();
			e.printStackTrace();
		}
		
	}
	
	private class Handler implements Runnable {
		private final Socket clientSocket;
		
		public Handler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			InputStream inputStream;
			try {
				inputStream = clientSocket.getInputStream();
				OutputStream outputStream = clientSocket.getOutputStream();
				
				int nextByte;
				while ((nextByte = inputStream.read()) != -1) {
					outputStream.write(nextByte);
				}
				
				clientSocket.close();
				System.out.println("Client disconnected");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
	
