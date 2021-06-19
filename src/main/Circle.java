package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;



public class Circle extends JPanel {
	
	
	private Image img;

	public Circle(String img) {
		this(new ImageIcon(img).getImage());
	}

	public Circle(Image img) {
		this.img = img;
		Dimension size = new Dimension((10),(10));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
