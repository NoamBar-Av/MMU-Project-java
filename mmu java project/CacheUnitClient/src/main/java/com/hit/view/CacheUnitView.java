package com.hit.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import com.hit.view.CacheUnitView.CachUnitPanel;

public class CacheUnitView 
{
	private static final String LOAD = "load";
	private static final String SHOW_STATISTICS = "show";
	private static String FILE_PATH;
	private static String PRESSED_BUTTON;
	private JTextArea jtxt;


	public class CachUnitPanel 	extends javax.swing.JPanel	implements java.awt.event.ActionListener
	{
		private JButton buttonLoad;
		private JButton buttonShow;
		
		public CachUnitPanel() 
		{	
			// 'Load' button 
			Icon loadIcon = new ImageIcon("src/main/resources/load.png");
			buttonLoad = new JButton("Load a Request");
			buttonLoad.setFont(new Font("Arial", Font.PLAIN, 22));
			buttonLoad.setIcon(loadIcon);
			buttonLoad.setPreferredSize(new Dimension(250, 70));
			buttonLoad.addActionListener(this);
			
			// 'Show Statistics' button
			Icon showIcon = new ImageIcon("src/main/resources/stats.png");
			buttonShow = new JButton("Show Statistics");
			buttonShow.setFont(new Font("Arial", Font.PLAIN, 22));
			buttonShow.setIcon(showIcon);
			buttonShow.setPreferredSize(new Dimension(250, 70));
			buttonShow.addActionListener(this);
		
			add(buttonLoad);
			add(buttonShow);
			jtxt = new JTextArea();  // That's the textArea - were the user can see the responses.
			jtxt.setEditable(false);
			jtxt.setText("");
			jtxt.setRows(10);
			jtxt.setColumns(36);
			jtxt.setFont(new Font("Arial", Font.PLAIN, 22));
			add(jtxt);
			
			JLabel background;
			ImageIcon img= new ImageIcon("src/main/resources/background.png");
			background= new JLabel ("",img,JLabel.CENTER);
			background.setBounds(0, 0, 1000, 500);
			add(background);
		}
		@Override
		
		public void actionPerformed(ActionEvent event) {
			Object button = event.getSource();
			if (button.equals(buttonLoad)) {
				PRESSED_BUTTON = LOAD;
			} else if (button.equals(buttonShow)) {
				System.out.println("sttts");

				PRESSED_BUTTON = SHOW_STATISTICS;
			}
			PressedButton();
		}
	}
	
	private PropertyChangeSupport support;
	
	public CacheUnitView()
	{
		support = new PropertyChangeSupport(this);
	}
	public void start()
	{
		
		JFrame frame = new JFrame("MMU Project NOAM+SAPIR");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setPreferredSize(new Dimension (700,500));
		frame.pack();
		CachUnitPanel CachUnitPanel = new CachUnitPanel();
		CachUnitPanel.setOpaque(true);
		frame.setContentPane(CachUnitPanel);
		frame.setVisible(true);
	}

	public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl)
	{
		this.support.addPropertyChangeListener(pcl);
	}
	public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl)
	{
		this.support.removePropertyChangeListener(pcl);
	}
	public <T> void updateUIData(T t)
	{
		jtxt.setText((String)t);  // Responsible for showing the message
	}
	public void PressedButton() {
		FILE_PATH = "";
		System.out.println("pressed");
		if(PRESSED_BUTTON.equals(LOAD)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSize(1000, 500);
			File dir = new File("src/main/resources"); 
			fileChooser.setCurrentDirectory(dir);
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				FILE_PATH = file.getAbsolutePath();
				support.firePropertyChange(PRESSED_BUTTON, null, FILE_PATH); // Method in the observer
			}
		}
		else if(PRESSED_BUTTON.equals(SHOW_STATISTICS)) {
			support.firePropertyChange(PRESSED_BUTTON, null, FILE_PATH);
		}
	}
}
