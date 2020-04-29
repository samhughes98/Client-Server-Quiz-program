package cmet.ac.sockets.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class SimpleServer extends AbstractServerComponent implements Runnable {

	// server ref 
	private ServerSocket 			serverSocket;

	// clienthandler ref
	@SuppressWarnings("unused")
	private ClientManager 			clientHandler;

	// server stop ref 
	private boolean 				stopServer;

	private Thread 					serverListenerThread;

	// reference variable thread
	private ThreadGroup 			clientThreadGroup;

	// variable for port number
	int port;
	
	
	 
	public SimpleServer() {
		
		this.stopServer = false;
		
		//start threadgroup
		this.clientThreadGroup = new ThreadGroup("ClientManager threads");

	}
	
    //starts server listening
	public void initializeServer(int port) throws IOException {

		this.port = port;
		if (serverSocket == null) {
			serverSocket = new ServerSocket(port);
		}

		stopServer = false;
		serverListenerThread = new Thread(this);
		serverListenerThread.start();

	}
	
	//display messages from each client
	public synchronized void handleMessagesFromClient(String msg, ClientManager client) {
		
		
		// format the client message before displaying in server's terminal output. 
        String formattedMessage = String.format("[client %d] : %s", client.getClientID(), msg); 
       
        display(formattedMessage);
        
     //User send input 
     //output = [client x: hello world]
		
	}
	
	public void display(String message) {
		System.out.println(">> " + message);
		
	}
	
	
	
	//get server user input for server only
	public void handleUserInput(String msg) {
		
		if(msg.equals(new String("over"))) {
			this.stopServer = true;
			close();
			return;
		}
		
		Thread[] clientThreadList = getClientConnections();
		for (int i = 0; i < clientThreadList.length; i++) {
			try {
				((ClientManager)clientThreadList[i]).sendMessageToClient(msg);
			}
			// close all no matter exception
			catch (Exception ex) {
				
			}
		}
	}

	
	//sends message to client, all clients listen
	public synchronized void sendMessageToClient(String msg, ClientManager client) {
		try {
			client.sendMessageToClient(msg);
	
			
		} catch (IOException e) {
			System.err.println("[server: ] Server-to-client message sending failed...");
		}
		}
	
	public Thread[] getClientConnections() {
		
		Thread[] clientThreadList = new Thread[clientThreadGroup.activeCount()];
		clientThreadGroup.enumerate(clientThreadList);

		return clientThreadList;
	}
	
	
	
	//close server
	public void close() {
		
		if (this.serverSocket == null)
			return;

		try {
			this.stopServer = true;
			this.serverSocket.close();

		} catch (IOException e) {
			System.err.println("[server: ] Error in closing server connection...");
		} finally {

			// close sockets that are connected
			Thread[] clientThreadList = getClientConnections();
			for (int i = 0; i < clientThreadList.length; i++) {
				try {
					((ClientManager) clientThreadList[i]).closeAll();
				}
				// Ignore all exceptions when closing clients.
				catch (Exception ex) {
					
				}
			}
			this.serverSocket = null;
			
		}

	}
	
	//listens to port and creates connection
	@Override
	public void run() {
		System.out.println("[server: ] startit: " + port);
		System.out.println("[server: ] starting server: listening @ port: " + port);

		// adds a value per client connection
		int clientCount = 0;
 
		while (!this.stopServer) {

			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();	
			} catch (IOException e1) {
				System.err.println("[server: ] Error when handling client connections on port " + port);
			}

			@SuppressWarnings("unused")
			ClientManager cm = new ClientManager(this.clientThreadGroup, clientSocket, clientCount, this);
			
			
			if(clientCount > 3)
				try {
					clientSocket.close();
					System.out.println("Too many clients have connected");
				} catch (IOException e) {
					System.out.println("Too many clients have connected");
				}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("[server: ] server listner thread interruped..");
			}

			clientCount++;
			
			

		}		
	}

	//return - server status
	public boolean getServerStatus() {
		return this.stopServer;
	}

	//starts simpleserver
	public static void main(String[] args) throws IOException {

		SimpleServer server = new SimpleServer();
		// port number to listen to
		int port = Integer.parseInt(args[0]); //7778;

		try {
			server.initializeServer(port);

		} catch (IOException e) {
			System.err.println("[server: ] Error in initializing the server on port " + port);
		}
	
		  //main thread 
		new Thread(() -> {
			String line = "";
			BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
			
			
			try {
				while(true) {				
					line = consoleInput.readLine();
					server.handleUserInput(line);
					if(server.getServerStatus()) {					
						break;
					}								
				}			
			}
			catch(IOException e) {
				System.out.println("Error in System.in user input");
			}
			finally {
				try {
					consoleInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		
	}
}