package com.hit.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CahceUnitClient 
{
	
    private final Integer port=12345;
    private final String ip="127.0.0.1";
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
	public CahceUnitClient() {
		try {
			socket = new Socket(ip, port);
			
		} catch (IOException e) {
			System.out.println("Server is not running");
			System.exit(1);
		} 
	}
	public java.lang.String send(java.lang.String request) { // this function sends request to the server
		
		String messageFromServer = null;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(request); // so the server could "push" it
			output.flush();
			input = new ObjectInputStream(socket.getInputStream()); // Here we are waiting to answer from server
			messageFromServer = (String)input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("message from server: "+messageFromServer);
	
		return messageFromServer; // answer returns to the observer
	}
}
