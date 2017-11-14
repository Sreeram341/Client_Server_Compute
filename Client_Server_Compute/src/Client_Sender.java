/********************************************************************************************
 * 
 * File Name : Client_Sender.java
 * 
 * Authors : Sreeram Pulavarthi
 * 
 * Date: 11-10-2017
 * 
 * Compiler Used: Java 1.8
 * 
 * Description of File: Creates client and sends request to Server and disconnectes from server when not required
 *
 *********************************************************************************************
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Client_Sender {

	public JFrame MainFrame = new JFrame();

	Socket client;

	InputStream input;

	OutputStream output;

	String[] CompOpt = { "+", "/", "*", "-" };

	public JComboBox CompList = new JComboBox(CompOpt);

	public JButton C2S, COMP, CDISC;

	public JTextArea JT = new JTextArea(5, 25);

	public JTextField Num1, Num2;

	private String message;

	Float num1, num2;

	public Client_Sender() {
		// TODO Auto-generated constructor stub

		/*
		 * Creates a new JFrame and sets the layout to be null 
		 * 
		 * */
		
		MainFrame.getContentPane().setLayout(null);

		MainFrame.setSize(800, 600);

		MainFrame.setLocationRelativeTo(null);

		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainFrame.setVisible(true);

	}

	public void CreateTA() {
		
		/*
		 * Creates Textarea field and adds to ScrollPane, and adds to the frame 
		 * 
		 * */

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JT.setForeground(Color.BLUE);

		JT.setBorder(border);

		JT.setEnabled(true);

		JT.setFont(JT.getFont().deriveFont(35.f));

		JT.setText("Client Running!!");

		JScrollPane scrollPane = new JScrollPane(JT);

		scrollPane.setBounds(10, 10, 750, 300);

		MainFrame.add(scrollPane);

		MainFrame.setTitle("NOT Connected");

		MainFrame.setVisible(true);

		CreateRest();

	}

	private void CreateRest() {
		// TODO Auto-generated method stub
		
		/*
		 * Adds rest of the Text entries, Button, Option button entries onto the frame  
		 * 
		 * */
		

		Num1 = new JTextField();

		Num1.setBounds(40, 400, 150, 40);

		MainFrame.add(Num1);

		Num2 = new JTextField();

		Num2.setBounds(210, 400, 150, 40);

		MainFrame.add(Num2);

		CompList.setBounds(380, 400, 70, 40);

		CompList.setFont(CompList.getFont().deriveFont(30.f));

		MainFrame.add(CompList);

		COMP = new JButton("COMPUTE");

		COMP.setBounds(480, 400, 150, 40);

		COMP.setFont(COMP.getFont().deriveFont(20.f));

		COMP.setEnabled(false);

		MainFrame.add(COMP);

		CDISC = new JButton("CONNECT");

		/*
		 * Add the Listener to the click button, enables the compute button, creates a new connection 
		 * 
		 * */
		
		
		CDISC.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ee) {

				StartConnect(ee);

			}
		});

		CDISC.setBounds(40, 475, 200, 40);

		CDISC.setFont(CDISC.getFont().deriveFont(20.f));

		MainFrame.add(CDISC);

	}

	public void StartConnect(MouseEvent e) {

		JButton click = (JButton) e.getSource();

		/*
		 * Toggles the method to handle the connect and disconnect and send the values to Server 
		 * 
		 * */
		
		
		if (click.getText().equals("CONNECT")) {
			CDISC.setText("DISCONNECT");

			COMP.setEnabled(true);

			COMP.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent cmee) {

					/*
					 * Calls the Start compute method and passes the Mouse event option 
					 * 
					 * */
					
					try {
						StartCompute(cmee);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			runClient();

		}

		else {
			CDISC.setText("CONNECT");

			COMP.setEnabled(false);

			try {
				
				/*
				 * Sends option to server that it is above to disconnect and closes the socket 
				 * 
				 * */

				output = client.getOutputStream();

				DataOutputStream out = new DataOutputStream(output);

				out.writeUTF("DISCONN");

				client.close();

			}

			catch (IOException ee) {

				ee.printStackTrace();

			}

		}

	}

	protected void StartCompute(MouseEvent cmee) throws IOException {
		// TODO Auto-generated method stub
		
		
		/*
		 * Raises the error if textbox is left empty 
		 * 
		 * */

		if (Num1.getText().isEmpty() || Num2.getText().isEmpty()) {
			JOptionPane.showMessageDialog(MainFrame, "Operands can't be null", "Error on Input: Null Value ",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				
				/*
				 * Parse into Float value if it is not empty and not a text value 
				 * 
				 * */

				num1 = Float.parseFloat(Num1.getText());
				num2 = Float.parseFloat(Num2.getText());

				int oprtr = CompList.getSelectedIndex();

				String outpt_txt = "";

				try {
					output = client.getOutputStream();
					input = client.getInputStream();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				/*
				 * If data is good send the values to the Server 
				 * 
				 * */
				

				DataInputStream inn = new DataInputStream(input);
				DataOutputStream outt = new DataOutputStream(output);

				outt.writeUTF("COMPUTE");
				outt.writeFloat(num1);
				outt.writeInt(oprtr);
				outt.writeFloat(num2);

				outpt_txt = inn.readUTF();

				JT.append(num1 + " " + CompOpt[oprtr] + " " + num2 + " = " + outpt_txt + "\n");

			} catch (NumberFormatException e) {
				// Not an integer
				JOptionPane.showMessageDialog(MainFrame, "Operands Input Error ", "Error on Input:",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void runClient() {

		try {
			
			
			/*
			 * Creates a socket and assigns to client 
			 * 
			 * */

			client = new Socket(InetAddress.getLocalHost(), 5000);

			input = client.getInputStream();
			output = client.getOutputStream();

			DataInputStream in = new DataInputStream(input);
			DataOutputStream out = new DataOutputStream(output);

			/*
			 * Reads the data from input 
			 * 
			 * */
			
			
			message = in.readUTF();

			MainFrame.setTitle("CONNECTED: " + message);

			JT.append("Created Socket\n");

			JT.append("Created input stream\n");

			message = "";

			message = in.readUTF();

			JT.append("The text from the server is: " + message + '\n');

			JT.append("Connection Successful\n");
			
			MainFrame.addWindowListener(new WindowAdapter() {
				
				/*
				 * Handles the event when window is above to close and sends a disconnect signal to server 
				 * 
				 * */
				
				
				public void windowClosing(WindowEvent we) {
					
						try {
							out.writeUTF("DISCONN");
							client.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
			});

		}

		catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				Client_Sender cs = new Client_Sender();

				cs.CreateTA();

			}
		});

	}

}
