package com.zahir.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import com.zahir.panel.PhoneBookUIPanel;

public class PhoneBookApp {

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("log4j.properties");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 
				JFrame jFrame = new JFrame("Phone Book App");
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				PhoneBookUIPanel phoneBookUIPanel = new PhoneBookUIPanel();
				jFrame.add(phoneBookUIPanel);

				jFrame.setSize(500, 600);
				jFrame.setVisible(true);
			}
		});

	}
}
