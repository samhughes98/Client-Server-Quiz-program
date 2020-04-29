package cmet.ac.sockets.servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import cmet.ac.sockets.clients.WriteToFile;


public class ClientManager extends Thread {

	// client socket
	private Socket 					clientSocket;
	
	// server
	private AbstractServerComponent	server;
	
	// stop connection tag
	private boolean					stopConnection;
	
	// I/O Streams
	private ObjectOutputStream 		out;
	private ObjectInputStream 		in;
	
	// client number upon messsage
	private int 					clientID;

	
    //clientmanager main thread
	public ClientManager(ThreadGroup threadgroup, Socket socket, int clientID, AbstractServerComponent server) {
		super(threadgroup, (Runnable) null);
		
		this.clientSocket = socket;
		this.server = server;
		this.stopConnection = false;
		this.clientID = clientID;
	
		System.out.println("[ClientManager: ] new client request received, poreeeeee "
				+ socket.getPort());
		
		try {
			this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.in = new ObjectInputStream(this.clientSocket.getInputStream());   
		}
		catch(IOException e) {
			System.err.println("[ClientManager: ] error when establishing IO streams on client socket.");
		
			
			try {
				closeAll();
			} catch (IOException e1) {
				System.err.println("[ClientManager: ] error when closing connections..." + e1.toString());

			}
		}
		
		start();	
	}
	

	// Message from server to client
	 
	public void sendMessageToClient(String msg) throws IOException {
		if (this.clientSocket == null || this.out == null)
			throw new SocketException("socket does not exist");
		
		this.out.writeObject(msg);
		

	}
	

	// close connections
	
	public void closeAll() throws IOException {
		try {
			
			if (this.clientSocket != null)
				this.clientSocket.close();

			
			if (this.out != null)
				this.out.close();

			
			if (this.in != null)
				this.in.close();
		} finally {
			

			this.in = null;
			this.in = null;
			this.clientSocket = null;
			
		}
	}

	/**
     handles message from client 
	 */
	@Override
	public void run() {
			
		// The message from the client
		
		String msg = "";
		int msg1 = WriteToFile.correctCount;
	
		try {
			while (!this.stopConnection) {
				//wait for message, send for handling to server
				
               
				
				msg = (String)this.in.readObject();
				this.server.handleMessagesFromClient(msg, this);
				
				if(msg.equals("done")) {
					 System.out.println(msg1);
				

				if(msg.equals("over")) {
					this.stopConnection = true;					
				}				
		
				}
			}
			//stop connection outcome
			System.out.println("[ClientManager: ] stopping the client connection ID: " + this.clientID);
		} catch (Exception e) {
			System.err.println("[ClientManager: ] error " + e.toString());
			
			if (!this.stopConnection) {
				try {
					closeAll();
				} 
				catch (Exception ex) 
				{
					System.err.println("[ClientManager: ] error when closing the connections.." + ex.toString());
				}
			}
		}
		finally {
			if(this.stopConnection) {
				try {
					closeAll();
				} catch (IOException e) {
					System.err.println("[ClientManager: ] error when closing the connections.." + e.toString());
				}				
			}
		}
		

					
	}
	
	
	 //Returns IP address
	
	public String toString() {
		return this.clientSocket == null ? null : this.clientSocket.getInetAddress().getHostName() + " ("
				+ this.clientSocket.getInetAddress().getHostAddress() + ")";
	}
	
	
	//////// GETTERS AND SETTERS ////////////
	public int getClientID() {
		return this.clientID;
	}
	
}
