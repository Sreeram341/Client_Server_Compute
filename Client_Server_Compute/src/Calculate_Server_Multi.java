/********************************************************************************************
 * 
 * File Name : Calculate_Server_Multi.java
 * 
 * Authors : Sreeram Pulavarthi
 * 
 * Date: 11-10-2017
 * 
 * Compiler Used: Java 1.8
 * 
 * Description of File: Creates a server and creates the thread for every client connected 
 *
 *********************************************************************************************
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.io.*; 
import java.net.*;
import javax.swing.*;
import javax.swing.border.Border;


public class Calculate_Server_Multi {
	
	public JFrame MainFrame = new JFrame();
	
	Border border = BorderFactory.createLineBorder(Color.BLACK);

	public JTextArea JTS = new JTextArea(5,25); 
	
	public Calculate_Server_Multi() {
		// TODO Auto-generated constructor stub
		
		MainFrame.getContentPane().setLayout(null);
		
		MainFrame.setSize(600, 900);
		
		MainFrame.setVisible(true);
		
		MainFrame.setTitle("Calculate Server Multi");
		
				
		JTS.setForeground(Color.GREEN);
		
		JTS.setLineWrap(true);
		
			
		JTS.setFont(JTS.getFont().deriveFont(35.f));
		
		/*
		 * Adds the horizontal and vertical scroll to the textarea
		 * 
		 * */
		
		
		JScrollPane scrollPane = new JScrollPane (JTS,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);		
				
		scrollPane.setBounds(10, 10, 560, 830);
		
		MainFrame.add(scrollPane);
				
	}
	
	public void runServer() {
		
		ServerSocket server; 
		
		Socket connection; 
		
				
		try { 
			
			/*
			 * Creates a server socket and starts listening to port 5000 
			 * 
			 * */
			
			server = new ServerSocket( 5000, 100 ); 
						
			JTS.insert("Server Running!!", 0);
		
			while(true) { 
				
				System.out.println("ready to accept"); 
								
				/*
				 * Accepts any request if any client is ready to listen on the connected local port 
				 * 
				 * */
				
				connection = server.accept(); 
				
				System.out.println("accept succeeded"); 
				
				/*
				 * Creates a new object for each client which gets connected to the server 
				 * 
				 * */
				
				DisposableServer localServer = new DisposableServer(connection,this); 
				
				localServer.start(); 
				
			} 
			
		} // try
		
		catch ( IOException e ) { e.printStackTrace(); } 
		
	}
	
																																																																																						// catch
	public static void main(String[] args) {

				Calculate_Server_Multi csm = new Calculate_Server_Multi();
				
				csm.runServer();
			
	}
	
}
