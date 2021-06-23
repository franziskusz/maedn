package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



/*Probleme
 * 
 * 	Hintergrund+Spielfeld nicht möglich Warum?
 *
 */



public class GameGUI extends JFrame{

	
 	private static final Graphics Graphics = null;
	private JLabel myLabel;
	private JButton option1;
	private JButton option2;
	private JButton option3;
	private JButton option4;
	private JButton würfeln;
	private JPanel mainPanel;
	private JPanel rightPanel;
	private Panel_with_background content;
	private Circle circle;
	 
	public GameGUI(){
		
		this.setTitle("Mensch ärgere dich nicht!");


/*
		JPLayeredPane layeredpane = new JPanel();
		panel.setBackground(Color.black);
		panel.setPreferredSize(new Dimension(700, 700));
		this.add(panel, BorderLayout.CENTER);
*/
		JLayeredPane layeredPane = new Panel_with_background(new ImageIcon("./images/background.png").getImage());
		layeredPane.setPreferredSize(new Dimension(700, 800));
		this.add(layeredPane,BorderLayout.CENTER);
		
		
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(700, 50));
		this.add(panel1, BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.red);
		panel2.setPreferredSize(new Dimension(700, 50));
		this.add(panel2, BorderLayout.SOUTH);



		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);


	}	
	

}















