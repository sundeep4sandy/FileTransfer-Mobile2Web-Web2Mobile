package networks;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Client {

	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message;

	public static void main(String[] args) throws IOException {
		try {
			serverSocket = new ServerSocket(4444); // Server socket

		} catch (IOException e) {
			System.out.println("Could not listen on port: 4444");
		}

		System.out.println("Server started. Listening to the port 4444");
		
		String name="";
		while (true) {
			try {

				clientSocket = serverSocket.accept(); // accept the  connection
				inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader); // get the  message
				message = bufferedReader.readLine();
				
				
				if(message.contains("fileList")){
					System.out.println("List of Files");
					Scanner sc= new Scanner(System.in); 
					System.out.println(message);
					
					clientSocket.close();
					name=sc.nextLine();
					PrintWriter writer = new PrintWriter(name, "UTF-8");
					clientSocket = serverSocket.accept();
					PrintWriter printwriter1 = new PrintWriter(clientSocket.getOutputStream(), true);
					printwriter1.write(name); // write the message to output stream

					printwriter1.flush();
					printwriter1.close();
					
				}
				else{
					System.out.println("Contents of file");
					PrintWriter writer = new PrintWriter(name, "UTF-8");
					writer.write(message);
					writer.close();
				System.out.println(message);
				}
				
				inputStreamReader.close();
				

			} catch (IOException ex) {
				System.out.println("Problem in message reading");
			}
		}

	}
	

}
