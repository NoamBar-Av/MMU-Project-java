package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import com.hit.server.Server;

public class CLI extends Observable implements java.lang.Runnable {
	
	public static final String START = "start";
	public static final String SHUTDOWN = "shutdown";
	public static final String NOT_VALID = "error";
	public static String SERVER_STATUS= "null";
	private OutputStreamWriter outputWriter;
	private PropertyChangeSupport Change = new PropertyChangeSupport (this);
	private ArrayList<PropertyChangeListener> listener;
	private Server server;
	private InputStream in;
	private OutputStream out;
	private Scanner reader;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		reader = new Scanner (new InputStreamReader(in));
		this.listener = new ArrayList<PropertyChangeListener>();
		this.in = in;
		this.out = out;	
	}

	public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl)
	{
		listener.add(pcl);
		this.Change.addPropertyChangeListener(pcl);
	}
	public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl)
	{
		this.Change.removePropertyChangeListener(pcl);
		listener.add(pcl);
	}
	public void write(java.lang.String string)
	{
		try {
			outputWriter.write(string + System.lineSeparator());
			outputWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	@Override
	public void run()
	{
		String command;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your command. For begin please enter 'start', for exit please enter 'shutdown'");
		while(sc.hasNext()) {
			command = sc.next();
			System.out.println(command);
            Change.firePropertyChange("command", "null", command);      
		}
		sc.close();
	}

}
	
