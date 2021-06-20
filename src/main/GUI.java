package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GUI extends JFrame{
	
	private Controller controller;

	private JLabel myLabel;
	private JButton startButton;
	private Panel_with_background content;
	private Circle circle;
	
	public GUI(){
		controller= new Controller(this);
		
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setResizable(false);
		 setLayout(null);
		 setSize(new Dimension(700,700));
		 setTitle("Mensch ärgere dich nicht");
	    
		
		JLabel label1= new JLabel();
		   label1.setOpaque(true);
		   label1.setBackground(Color.RED);
		   label1.setBounds(50,50,200,200);
		   
		  JLabel label2= new JLabel();
		   label2.setOpaque(true);
		   label2.setBackground(Color.GREEN);
		   label2.setBounds(100,100,200,200);
		   
		  JLabel label3= new JLabel(new ImageIcon("./circle.png"));
		  label3.setOpaque(true);
		  label3.setVisible(true);
		  label3.setBounds(150,150,200,200);
		  label3.setSize(500,500);
		  
		   
		 /* JButton button = new JButton();
		   button.setOpaque(true);
		   button.setBackground(Color.GREEN);
		   button.setBounds(100,100,200,200);
		   button.setText("klappt");
		   */
		   
		   JLayeredPane layeredPane = new Panel_with_background(new ImageIcon("./background.png").getImage());
		
		   layeredPane.setBounds(0,0,700,700);
		   layeredPane.add(label1, Integer.valueOf(1));
		   layeredPane.add(label2, Integer.valueOf(2));
		   layeredPane.add(label3, Integer.valueOf(3));
		   //layeredPane.add(button, Integer.valueOf(15001));
		   
		    
		     
		   	  setContentPane(layeredPane);
		      pack();
		      setVisible(true);

	
		/*
		// setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		JLabel background;
		setSize(700,700);
		ImageIcon image = new ImageIcon ("./background.png");
		
		background = new JLabel ("",image, JLabel.CENTER);
		background.setBounds(0,0,700,700);
		add(background);
		
		setVisible(true);
		
		*/
		
		
		
		
		/*
		JLabel circle;
		setSize(100,100);
		ImageIcon circles = new ImageIcon ("./circle.png");
		
		circle = new JLabel ("", JLabel.NORTH);
		add(circle);
		
		*/
		
	
		/*content = new Panel_with_background(new ImageIcon("./background.png").getImage());
		circle = new Circle(new ImageIcon("./circle.png").getImage());
		
		setLayout(new FlowLayout());
		
		setTitle("Mensch ärgere dich nicht");
		setSize(700,700);
		setResizable(false);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		//setContentPane(content);
		getContentPane().add(content);
		setContentPane(circle);
		pack();
		
		setVisible(true);
		*/
		
		
		
		
	}
	
	
	


}
















