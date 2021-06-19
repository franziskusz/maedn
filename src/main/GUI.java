package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame{
	
	private Controller controller;

	private ImageIcon background;
	private JLabel myLabel;
	private JButton startButton;
	private JPanel content;
	
	public GUI(){
		controller= new Controller(this);	
		
		Panel_with_background content = new Panel_with_background(new ImageIcon("/pruefung_aop/background.png").getImage());
		
		setTitle("Mensch ärgere dich nicht");
		add(myLabel);
		setSize(700,700); 
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		setContentPane(content);
		pack();
		setVisible(true);
		
		
	}
	
	
	
	
	
	

}
















