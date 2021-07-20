package main.gui;

import main.model.enums.PlayerColor;
import main.model.player.Piece;
import main.model.player.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;



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
	public static final String OPTION_0 = "OPTION_0";
	public static final String OPTION_1 = "OPTION_1";
	public static final String OPTION_2 = "OPTION_2";
	public static final String OPTION_3 = "OPTION_3";
	public static final String ADMIN = "ADMIN";

	JPanel panel1 = new JPanel();
	
	private JButton btnWuerfel = new RoundButton("", 20, false); 
	private JButton btnOption0 = new RoundButton("", 20, false);
	private JButton btnOption1 = new RoundButton("", 20, false);
	private JButton btnOption2 = new RoundButton("", 20, false);
	private JButton btnOption3 = new RoundButton("", 20, false);
	private JButton btnAdmin = new RoundButton("", 20, false);
	private RoundButton s1 = new RoundButton("",40,false);
	private RoundButton s2 = new RoundButton("",40,false);
	 
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panelbox = new JPanel();

	private JTextField text = new RoundJTextField(20);
	private JLabel labelDiced;
	private JTextField tfAdmin = new RoundJTextField(20);
	
	private Panel_with_background boardLayeredPane;

	ImageIcon imageIcon1 = new ImageIcon ("./images/1.png");
	ImageIcon imageIcon2 = new ImageIcon ("./images/2.png");
	ImageIcon imageIcon3 = new ImageIcon ("./images/3.png");
	ImageIcon imageIcon4 = new ImageIcon ("./images/4.png");
	ImageIcon imageIcon5 = new ImageIcon ("./images/5.png");
	ImageIcon imageIcon6 = new ImageIcon ("./images/6.png");

	public GameGUI(ArrayList<Piece> pieces, boolean admin){

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

		
		
		s1.setEnabled(false);
		s1.setBounds(290, 290, 40, 40);
		
		s2.setEnabled(false);
		s2.setBounds(270, 270, 40, 40);

		
		boardLayeredPane = new Panel_with_background(new ImageIcon("./images/background.png").getImage(), pieces);
		boardLayeredPane.setPreferredSize(new Dimension(580, 580));
		boardLayeredPane.add(s1);
		boardLayeredPane.add(s2);
		this.add(boardLayeredPane,BorderLayout.CENTER);

		
		
		panel1.setPreferredSize(new Dimension(580, 50));
		this.add(panel1, BorderLayout.NORTH);
		panel1.setLayout(new GridBagLayout());
		panel2.setLayout(new GridLayout(1,6,10,0));
		panel2.setPreferredSize(new Dimension(580,80));
		panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel3.setLayout(new GridLayout(1,6,10,0));
		panel3.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panelbox.setLayout(new BorderLayout());
		panelbox.add(panel2, BorderLayout.NORTH);
		panelbox.add(panel3, BorderLayout.SOUTH);
		
		
		if(admin) {
			// Höhe des Panels anpassen, damit Buttons noch angezeigt werden
			panel3.setPreferredSize(new Dimension(580, 50));
			
		} else {
			panel3.setPreferredSize(new Dimension(580, 0));
		}
		this.add(panelbox, BorderLayout.SOUTH);

		
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setEditable(false);
		text.setText("?");
		text.setPreferredSize( new Dimension( 200, 24 ));
		text.setBackground(new Color(255,255,255,255));
		text.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		panel1.add(text);
		

		
		
		
		
		btnWuerfel.setText("Dice");
		btnWuerfel.setActionCommand(WUERFELN);
		btnWuerfel.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnWuerfel.setBorder(new EmptyBorder(0,0,0,0)); //ganzer Button orange bei klick, nicht nur rahmen
		btnWuerfel.setBackground(new Color(255,255,255,255));
		//btnWuerfel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnWuerfel);
		
		labelDiced = new JLabel("", imageIcon6,JLabel.CENTER);
		// @Toni Hier mit der Größe Rumspielen
		labelDiced.setPreferredSize(new Dimension(40, 40));
		panel2.add(labelDiced);

		btnOption0.setText("0");
		btnOption0.setActionCommand(OPTION_0);
		btnOption0.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption0.setBorder(new EmptyBorder(0,0,0,0));
		btnOption0.setBackground(new Color(255,255,255,255));
		//btnOption0.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnOption0);
		
		

		btnOption1.setText("1");
		btnOption1.setActionCommand(OPTION_1);
		btnOption1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption1.setBorder(new EmptyBorder(0,0,0,0));
		btnOption1.setBackground(new Color(255,255,255,255));
		panel2.add(btnOption1);
		//panel2.add( Box.createRigidArea( new Dimension( 1 , 0 ) )  ); für den Abstand zwischen den Buttons, hat nicht geklappt

		btnOption2.setText("2");
		btnOption2.setActionCommand(OPTION_2);
		btnOption2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption2.setBorder(new EmptyBorder(0,0,0,0));
		btnOption2.setBackground(new Color(255,255,255,255));

		//btnOption2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnOption2);
	

		btnOption3.setText("3");
		btnOption3.setActionCommand(OPTION_3);
		btnOption3.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption3.setBorder(new EmptyBorder(0,0,0,0));
		btnOption3.setBackground(new Color(255,255,255,255));

		//btnOption3.setBorder(new EmptyBorder(0,10,0,0));
		//btnOption3.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnOption3);
		
		

		if(admin) {
			// Admin Elemente anzeigen
			tfAdmin.setPreferredSize(new Dimension(200, 30));
			tfAdmin.setText(" ");
			tfAdmin.setFont(new Font("Lexend Deca", Font.BOLD, 15));
			tfAdmin.setBorder(BorderFactory.createLineBorder(Color.black));
			tfAdmin.setHorizontalAlignment(RoundJTextField.CENTER);
			panel3.add(tfAdmin);

			btnAdmin.setText("Admin");
			btnAdmin.setActionCommand(ADMIN);
			btnAdmin.setFont(new Font("Lexend Deca", Font.BOLD, 15));
			btnAdmin.setBorder(new EmptyBorder(0,0,0,0));
			btnAdmin.setBackground(new Color(255,255,255,255));
			panel3.add(btnAdmin);
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	public JButton getBtnWuerfel() {
		return btnWuerfel;
	}

	public JButton getBtnOption0() {
		return btnOption0;
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

	public JButton getBtnAdmin() {
		return btnAdmin;
	}

	public JTextField getText() {
		return text;
	}

	public JTextField getTfAdmin() {
		return tfAdmin;
	}

	public Panel_with_background getBoardLayeredPane() {
		return boardLayeredPane;
	}


	public void setDicedImage(int diced) {
		switch(diced) {
		case 1:
			labelDiced.setIcon(imageIcon1);
			break;
		case 2:
			labelDiced.setIcon(imageIcon2);
			break;
		case 3:
			labelDiced.setIcon(imageIcon3);
			break;
		case 4:
			labelDiced.setIcon(imageIcon4);
			break;
		case 5:
			labelDiced.setIcon(imageIcon5);
			break;
		case 6:
			labelDiced.setIcon(imageIcon6);
			break;
		}
	}

	public void setBackgroundColor(PlayerColor playerColor) {
		switch(playerColor) {
			case RED:
				
				panel1.setBackground(new Color (255,0,0,255));
				//panel2.setBackground(new Color(255, 61, 61,255));
				break;
			case BLUE:
				panel1.setBackground(new Color (30,144,255,255));
				//panel2.setBackground(new Color(61, 139, 255,255));
				break;
			case GREEN:
				panel1.setBackground(new Color (0,255,0,225));
				//panel2.setBackground(new Color(138, 243, 67,255));
				break;
			case YELLOW:
				panel1.setBackground(new Color (255,255,0,225));
				//panel2.setBackground(new Color(222, 201, 21,255));
				break;
		}
	}
}















