package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}
	
	// Read from stdin and write to the socket's output stream
	private static class InputThread extends Thread {
		Socket socket;
		
		public InputThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				InputStream sin = System.in;
				OutputStream streamOut = socket.getOutputStream();
				
				int nextByte;
				while ((nextByte = sin.read()) != -1) {
					streamOut.write(nextByte);
				}
				
				socket.shutdownOutput();
				
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
	}
	
	private static class OutputThread extends Thread{
		Socket socket;
		
		public OutputThread(Socket socket) {
			this.socket = socket;
		} 
		
		public void run() {
			try {
				InputStream streamIn = socket.getInputStream();
				OutputStream sout = System.out;
				
				int nextByte;
				while ((nextByte = streamIn.read()) != -1) {
					sout.write(nextByte);
				}
				
				System.out.flush();
				
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
	}
	
	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputThread inputThread = new InputThread(socket);
		OutputThread outputThread = new OutputThread(socket);
		
		inputThread.start();
		outputThread.start();
		
	}
}