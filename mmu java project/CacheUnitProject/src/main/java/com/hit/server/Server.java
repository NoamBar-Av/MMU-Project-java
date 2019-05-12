package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.hit.services.CacheUnitController;
import com.hit.util.CLI;

public class Server extends java.lang.Object implements java.beans.PropertyChangeListener, java.lang.Runnable {

	public int port = 12345;
	public Socket socket;
	private String command;
	private static boolean on = false;
	private ServerSocket serverSocket;
	private Thread thread;
	private CacheUnitController<String> controller = new CacheUnitController<String>();

	public Server() {
		on = false;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Port already used");
			System.exit(1);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		command = evt.getNewValue().toString();
		if (command.equalsIgnoreCase("start")) {
			if (!on) {
				System.out.println("starting server...");
				on = true;
				new Thread(this).start();
			} else {
				System.out.println("already running");

			}
		} else if (command.equalsIgnoreCase("shutdown")) {
			System.out.println("shutdown server...");
			if (!serverSocket.isClosed()) {
				try {
					serverSocket.close();
					System.exit(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("not valid");
		}

	}

	
	@Override
	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept(); //The server is waiting for the Client to send the request
				HandleRequest<String> handler = new HandleRequest<String>(socket,controller );
				thread = new Thread(handler); // New thread for each request
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
