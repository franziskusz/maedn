package main;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI extends JFrame{
	
	private Controller controller;

	private JLabel myLabel;
	private JButton startButton;
	private Panel_with_background content;
	private Circle circle;
	
	public GUI(){
		controller= new Controller(this);
		
		content = new Panel_with_background(new ImageIcon("./background.png").getImage());
		circle = new Circle();
		
		setTitle("Mensch ärgere dich nicht");
		setSize(700,700);
		setResizable(false);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setContentPane(content);
		getContentPane().add(circle);
		pack();
		setVisible(true);
		
		
		
		
		
	}
	
	
	


}
















