package main.gui;

import main.model.player.Piece;
import main.model.player.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



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
	
	public static final String WUERFELN = "WUERFELN";
	public static final String OPTION_1 = "OPTION_1";
	public static final String OPTION_2 = "OPTION_2";
	public static final String OPTION_3 = "OPTION_3";
	public static final String OPTION_4 = "OPTION_4";

	private JButton btnWuerfel = new JButton();
	private JButton btnOption1 = new JButton();
	private JButton btnOption2 = new JButton();
	private JButton btnOption3 = new JButton();
	private JButton btnOption4 = new JButton();

	private JTextField text = new JTextField();
	private JTextField wZahl = new JTextField();

	private Panel_with_background boardLayeredPane;
	 
	public GameGUI(ArrayList<Piece> pieces){
		
		this.setTitle("Mensch ärgere dich nicht!");


/*
		JPLayeredPane layeredpane = new JPanel();
		panel.setBackground(Color.black);
		panel.setPreferredSize(new Dimension(700, 700));
		this.add(panel, BorderLayout.CENTER);
*/
		
		/*
		setLayout(new FlowLayout());

		text.setEditable(false);
		text.setText("?");
		text.setPreferredSize( new Dimension( 200, 24 ) );
		wZahl.setSize(400, 10);
		add(text);

		wZahl.setEditable(false);
		wZahl.setText("0");
		wZahl.setSize(10, 10);
		add(wZahl);

		btnWuerfel.setText("Würfeln");
		btnWuerfel.setActionCommand(WUERFELN);
		add(btnWuerfel);

		btnOption1.setText("Option 1");
		btnOption1.setActionCommand(OPTION_1);
		add(btnOption1);

		btnOption2.setText("Option 2");
		btnOption2.setActionCommand(OPTION_2);
		add(btnOption2);

		btnOption3.setText("Option 3");
		btnOption3.setActionCommand(OPTION_3);
		add(btnOption3);

		btnOption4.setText("Option 3");
		btnOption4.setActionCommand(OPTION_3);
		add(btnOption4);


		pack();
		
		*/
		
		boardLayeredPane = new Panel_with_background(new ImageIcon("./images/background.png").getImage(), pieces);
		boardLayeredPane.setPreferredSize(new Dimension(580, 580));
		this.add(boardLayeredPane,BorderLayout.CENTER);
		
		
		
		JPanel panel1 = new JPanel();
		//panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(580, 50));
		this.add(panel1, BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel();
		//panel2.setBackground(Color.red);
		panel2.setPreferredSize(new Dimension(580, 50));
		this.add(panel2, BorderLayout.SOUTH);
		
		
		
		text.setEditable(false);
		text.setText("?");
		text.setPreferredSize( new Dimension( 200, 24 ) );
		panel1.add(text);

		btnWuerfel.setText("Würfeln");
		btnWuerfel.setActionCommand(WUERFELN);
		panel2.add(btnWuerfel);

		btnOption1.setText("Option 1");
		btnOption1.setActionCommand(OPTION_1);
		panel2.add(btnOption1);

		btnOption2.setText("Option 2");
		btnOption2.setActionCommand(OPTION_2);
		panel2.add(btnOption2);

		btnOption3.setText("Option 3");
		btnOption3.setActionCommand(OPTION_3);
		panel2.add(btnOption3);

		btnOption4.setText("Option 4");
		btnOption4.setActionCommand(OPTION_3);
		panel2.add(btnOption4);
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public JButton getBtnWuerfel() {
		return btnWuerfel;
	}

	public JButton getBtnOption1() {
		return btnOption1;
	}

	public JButton getBtnOption2() {
		return btnOption2;
	}

	public JButton getBtnOption3() {
		return btnOption3;
	}

	public JButton getBtnOption4() {
		return btnOption4;
	}

	public JTextField getText() {
		return text;
	}

	//
	// NEU
	//
	public Panel_with_background getBoardLayeredPane() {
		return boardLayeredPane;
	}
	//
	// NEU ENDE
	//

}















