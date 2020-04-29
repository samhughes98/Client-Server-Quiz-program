package cmet.ac.sockets.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


public class SimpleClient implements Runnable {

	// socket ref
	private Socket 					clientSocket;

	// I/O ref
	private ObjectOutputStream 		output;
	private ObjectInputStream 		input;

	// stop client ref
	private boolean 				stopClient;

	private Thread 					clientReader;

	// Ip/Host number ref
	private String 					host;
	private int 					port;
	
    //begins client - opens connection
	public SimpleClient(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		openConnection();
	}
	


    //starts connections and I/O
	public void openConnection() throws IOException {

		// sockets and streams
		try {

			this.clientSocket = new Socket(this.host, this.port);
			this.output = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.input = new ObjectInputStream(this.clientSocket.getInputStream());
        //code runs incase an error occours
		} catch (IOException ex) {
			try {
				closeAll();
			} catch (Exception exc) {
				System.err.println("[client: ] error in opening a connection to: " + this.host + " on port: " + this.port);
			}

			throw ex; 
		}
		
		this.clientReader = new Thread(this);
		this.stopClient = false;
		this.clientReader.start();
	}

    //sends string to server
	public void sendMessageToServer(String msg) throws IOException {
		if (this.clientSocket == null || this.output == null)
			throw new SocketException("socket does not exist");
		this.output.writeObject(msg);
	}


    //handles message from server
	public void handleMessageFromServer(String msg) {
		display(msg);

	}
	
	//display string in cmd
	public void display(String message) {
		System.out.println("> " + message);
	}

	//close all connections
	public void closeAll() throws IOException {
		try {
			//close socket stream
			if (this.clientSocket != null)
				this.clientSocket.close();
		    //close output stream
			if (this.output != null)
				this.output.close();
            //close input stream
			if (this.input != null)
				this.input.close();
			
		} finally {
			this.output = null;
			this.input = null;
			this.clientSocket = null;
		}
	}
	

	
	//user input to terminal
	//runs writetofile - asks for users names
	//writetofile includes xmlparser - runs game script
	public void runClient() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message = null;
			WriteToFile WriteToFile = new WriteToFile();
			WriteToFile.userinput();
			
			while (true) {
				message = fromConsole.readLine();
				handleUserInput(message);
				if(message.equals("over"))
					break;
			}
			
			
			//message begins if client is stopped via the over keyword
			System.out.println("[client: ] stopping client...");
			this.stopClient = true;
			fromConsole.close();
			//closeAll();
		} catch (Exception ex) {
			System.out.println("[client: ] unexpected error while reading from console!");
		}

	}
	//handle users input from client
	public void handleUserInput(String userResponse) {

		if (!this.stopClient) {
			try {
				sendMessageToServer(userResponse);
			} catch (IOException e) {
				System.err.println("[client: ] error when sending message to server: " + e.toString());

				try {
					closeAll();
				} catch (IOException ex) {
					System.err.println("[client: ] error closing the client connections: " + ex.toString());
				}
			}
		}
	}
	
	//thread handles communication with server
	@Override
	public void run() {

		String msg;
	

		// wait for data

		try {
			while (!this.stopClient) {
				msg = (String) input.readObject();

				handleMessageFromServer(msg);
			}
			
			System.out.println("[client: ] client stopped..");
		} catch (Exception exception) {
			if (!this.stopClient) {
				try {
					closeAll();
				} catch (Exception ex) {
					System.err.println("[client: ] error in closing the client connection...");
				}
			}
		} finally {
			clientReader = null;
		}
	
		
		System.out.println("[client: ] exiting thread...");
	}

	//begins client
	public static void main(String[] args) {
		
		// hardcoded server IP and port number. 
		String ip; // = "127.0.0.1";
		int port; // = 7777;
		
		ip = args[0];
		port = Integer.parseInt(args[1]);
		

		SimpleClient chatclient = null;
		
		// server communicate thread
		try {
			chatclient = new SimpleClient(ip, port);
		} catch (IOException e) {
			System.err.println("[client: ] error in openning the client connection to " + ip + " on port: " + port);
		}

		// handle user inputs on main thread
		chatclient.runClient();
		

	}

}

