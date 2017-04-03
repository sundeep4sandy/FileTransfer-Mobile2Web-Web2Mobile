package networks;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 * sending and receiving msgs parallel 
 */
public class TCPServer extends Thread {
 
    public static final int SERVERPORT = 4444;
    private boolean running = false;
    private PrintWriter mOut;
    private OnMessageReceived messageListener;
 
    public static void main(String[] args) {
    	// this is the interface where we can check the msgs. remember it 
    	Interface frame = new Interface();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
 
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
 
	  public void sendMessage(String message){
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }
    }

		 public void writeUTF(String message){
			 
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            System.out.println(message+"utf");
            mOut.flush();
        }
    }
 
    @Override
    public void run() {
        super.run();
 
        running = true;
 
        try {
            System.out.println("server is connecting");
 
            //server socket creation;
            
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
 
            //client socket creation and accept() is used for the listening.
            Socket client = serverSocket.accept();
            System.out.println(" Receiving...");
 
            try {
 
                //sends the message to the client
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
 
                //read the message received from client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
 
                //this is used for listening of msgs and receiving msgs from client
              
                while (running) {
                    String message = in.readLine();
										
					     if (message != null && messageListener != null && message.compareTo("ListingFiles")==0) {
                        //call the method messageReceived from ServerBoard class
                        messageListener.messageReceived(message);
                    } 
					
					     else if (message != null && messageListener != null && message.compareTo("RequestingFile")==0) {
                        //call the method messageReceived from ServerBoard class
					    	 System.out.println(message+"  llkasl");
					    	 message = in.readLine();
					    	 System.out.println(message+" vyooo");
                        messageListener.sendFile(message);
                    } 
					
				
					
                }
 
            } catch (Exception e) {
                System.out.println("server is an error");
                e.printStackTrace();
            } finally {
                client.close();
                System.out.println("it is done");
            }
 
        } catch (Exception e) {
            System.out.println(" error");
            e.printStackTrace();
        }
 
    }
    //this is the interface. it will be implemented in interface.
   
    public interface OnMessageReceived {
			
        public void messageReceived(String message);
		public void sendFile(String message);
    }
 
}