package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.*;



public class Circle extends JFrame {
	
	/*ImageIcon Image = new ImageIcon("circle.png");
	
	
	private Image img;

	public Circle(String img) {
		this(new ImageIcon(img).getImage());
	}

	public Circle(Image img) {
		this.img = img;
		Dimension size = new Dimension((100),(100));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
*/
	private ImageIcon image1;
	private JLabel label1;
	
	
	void ImageTutorial(){
		setLayout(new FlowLayout());
		
		image1= new ImageIcon(getClass().getResource("./circle.png"));
		label1 =new JLabel(image1);
		add(label1);
	}
	
		
		
		
		
	
	
	
	

}
