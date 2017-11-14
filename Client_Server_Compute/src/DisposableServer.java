/********************************************************************************************
 * 
 * File Name : DisposableServer.java
 * 
 * Authors : Sreeram Pulavarthi
 * 
 * Date: 11-10-2017
 * 
 * Compiler Used: Java 1.8
 * 
 * Description of File: Sends message to client and also handles the compute operator received from client and replies back
 *
 *********************************************************************************************
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class DisposableServer extends Thread {

	DataInput in; 
	
	DataOutput out; 
	
	Socket socket;
	
	private Calculate_Server_Multi CSM;	
	
	String cmessage,skadr;
		
	public DisposableServer(Socket socket, Calculate_Server_Multi CSM) {
		// TODO Auto-generated constructor stub
		
		this.CSM = CSM;
		
		try { 
					
			this.socket = socket;
			
			in = new DataInputStream(socket.getInputStream()); 
			
			out = new DataOutputStream(socket.getOutputStream()); 
			
		} 
		
		catch (IOException ioe) { } 
		
	}
	
	public void run() { 
		
		boolean val = true;
		
		skadr = socket.getRemoteSocketAddress().toString();
		
		try { 
			
			out.writeUTF(skadr);
			
			System.out.println("Server thread Port: " + skadr+'\n'); 
			
			out.writeUTF("OK"); 
			
			CSM.JTS.insert( skadr+ " connected\n",0);	
					
			CSM.JTS.insert( "Sending OK Response...\n",0 ); 			
			
			while (val){
			cmessage = in.readUTF();
			
			if (cmessage.equals("DISCONN"))
			
			{
				
				/*
				 * Closes the connection when client gets disconnected 
				 * 
				 * */
				
				CSM.JTS.insert( skadr+ " disconnected\n",0);	
				
				val = false;
				
				socket.close();
			}
			else if (cmessage.equals("COMPUTE"))
			{
				int oprtr;
				Float num1,num2,out_res;
				String cnrt_res="";
				Double decimal_part;
				
				String[] CompOpt = { "+", "/", "*", "-"};
				
				/*
				 * Handles the compute instructions here 
				 * 
				 * */
				
				num1 = in.readFloat();
				
				oprtr = in.readInt();
				
				num2 = in.readFloat();
				
				if (CompOpt[oprtr].equals("+"))
				{
					
					CSM.JTS.insert("Calculate Problem :"+ num1+" " +CompOpt[oprtr]+ " " + num2 +" = ?\n",0);	
					
					out_res = num1+num2;
															
					System.out.println("Out side Decimal_point "+String.format("%5.2f",out_res));
					out.writeUTF(String.format("%5.2f",out_res));
					
				}
				else if (CompOpt[oprtr].equals("*"))
				{
					
					CSM.JTS.insert("Calculate Problem :"+ num1+" " +CompOpt[oprtr]+ " " + num2 +" = ?\n",0);	
					
					out_res = num1*num2;
					
					System.out.println("Out side Decimal_point "+String.format("%5.2f",out_res));
					out.writeUTF(String.format("%5.2f",out_res));
				}
				
				else if (CompOpt[oprtr].equals("-"))
				{
					
					CSM.JTS.insert("Calculate Problem :"+ num1+" " +CompOpt[oprtr]+ " " + num2 +" = ?\n",0);	
					

					out_res = num1-num2;
					
					System.out.println("Out side Decimal_point "+String.format("%5.2f",out_res));
					out.writeUTF(String.format("%5.2f",out_res));
				}
				
				else if (CompOpt[oprtr].equals("/"))
				{
					
					CSM.JTS.insert("Calculate Problem :"+ num1+" " +CompOpt[oprtr]+ " " + num2 +" = ?\n",0);	
					

					out_res = num1/num2;
					
					System.out.println("Out side Decimal_point "+String.format("%5.2f",out_res));
					out.writeUTF(String.format("%5.2f",out_res));
					}
				
				}
			}
		} 
		
		catch (IOException ioe) { ioe.printStackTrace(); } 
		
	}
		
}
