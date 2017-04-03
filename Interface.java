package networks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
 
public class Interface extends JFrame {
   private JTextArea messagesArea;
    private JButton sendButton;
    private JTextField message;
    private JButton startServer;
    private TCPServer mServer;
 
    public Interface() {
 
        super("Interface");
 
        JPanel panelFields = new JPanel();
        panelFields.setLayout(new BoxLayout(panelFields,BoxLayout.X_AXIS));
 
        JPanel panelFields2 = new JPanel();
        panelFields2.setLayout(new BoxLayout(panelFields2,BoxLayout.X_AXIS));
 
        //here we will have the text messages screen
        messagesArea = new JTextArea();
        messagesArea.setColumns(80);
        messagesArea.setRows(40);
        messagesArea.setEditable(false);
 
        sendButton = new JButton("text");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get msg from the text view
                String messageText = message.getText();
                // add msg to the msg area
                messagesArea.append("\n" + messageText);
                // send msg to the client
              
                message.setText("");
            }
        });
 
        startServer = new JButton("Start");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // disable the start button
                startServer.setEnabled(false);
 
                //creates the object OnMessageReceived asked by the TCPServer constructor
                mServer = new TCPServer(new TCPServer.OnMessageReceived() {
                    @Override
                   
                    public void messageReceived(String message) {
							messagesArea.append("\n "+message);
						String path = "C:/Users/Rishi/Desktop/sandy/test";
						String files;
						File folder = new File(path);
						File[] listOfFiles = folder.listFiles(); 
 
						for (int i = 0; i < listOfFiles.length; i++) 
						{
 
						if (listOfFiles[i].isFile()) 
						{
						mServer.sendMessage(listOfFiles[i].getName());
						}	
						//mServer.flush();
					} 
					}
					
					public void sendFile(String message)
					{
						messagesArea.append("\n "+message);
					final File folder = new File("C:/Users/Rishi/Desktop/sandy/test");
					final File file = new File(folder,message);	
					
					
					try
						{
						FileInputStream fin= new FileInputStream(file);
						int ch;
						ch=fin.read();
						while(ch!=-1){
							ch=fin.read();
							mServer.writeUTF(String.valueOf(ch));
							System.out.println(ch);
						}
						fin.close();
						
						
						
						}
						catch (IOException e)
						{
							
						}
						finally 
						{
							
						}
						System.out.println("File transfer complete");
					
					}	
                        
                });
				
                mServer.start();
 
            }
        });
 
        //the box where the user enters the text (EditText is called in Android)
        message = new JTextField();
        message.setSize(200, 20);
 
        //add the buttons and the text fields to the panel
        panelFields.add(messagesArea);
        panelFields.add(startServer);
 
        panelFields2.add(message);
        panelFields2.add(sendButton);
 
        getContentPane().add(panelFields);
        getContentPane().add(panelFields2);
 
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
 
        setSize(300, 170);
        setVisible(true);
    }
}