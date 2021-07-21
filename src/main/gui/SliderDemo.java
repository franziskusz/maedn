package main.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


public class SliderDemo implements ChangeListener{

	JFrame frame;
	JPanel panel;
	JLabel label;
	JSlider slider;
	
	
	
	SliderDemo(){
		
		frame= new JFrame("SliderDemo");
		panel = new JPanel();
		label = new JLabel();
		slider= new JSlider(0,100,0);
		
		
		slider.setSize(new Dimension(400,200));
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(5);
		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(10);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		slider.setOrientation(SwingConstants.HORIZONTAL);
		label.setText("Bot ist jetzt" + slider.getValue()+ "mal" + "schneller");
		
		slider.addChangeListener(this);
		
		panel.add(slider);
		panel.add(label);
		frame.add(panel);
		frame.setSize(400,400);
		frame.setVisible(true);
		
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		
		label.setText("Bot ist jetzt" + slider.getValue()+ "mal" + "schneller");
		
	}

}
