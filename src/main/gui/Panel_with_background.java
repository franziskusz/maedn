package main.gui;

import javax.swing.*;
import java.awt.*;

public class Panel_with_background extends JLayeredPane {

	private Image img;

	public Panel_with_background(String img) {
		this(new ImageIcon(img).getImage());
	}

	public Panel_with_background(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
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