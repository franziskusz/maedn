package main.gui;


import main.SleepThread;
import main.model.enums.PlayerColor;
import main.model.player.Piece;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.management.timer.Timer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;






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
	
	private BufferedImage image;

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
	
	
	
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JPanel panelbox = new JPanel();
	
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15;
	
	private JSlider slider = new JSlider(10,1010,510);
	

	private JTextField text = new RoundJTextField(20);
	private JLabel labelDiced;
	private JTextField tfAdmin = new RoundJTextField(20);
	private JTextField left = new JTextField("fast");
	private JTextField right = new JTextField("slow");
	
	private Panel_with_background boardLayeredPane;

	ImageIcon imageIcon1 = new ImageIcon ("./images/1.png");
	ImageIcon imageIcon2 = new ImageIcon ("./images/2.png");
	ImageIcon imageIcon3 = new ImageIcon ("./images/3.png");
	ImageIcon imageIcon4 = new ImageIcon ("./images/4.png");
	ImageIcon imageIcon5 = new ImageIcon ("./images/5.png");
	ImageIcon imageIcon6 = new ImageIcon ("./images/6.png");
	ImageIcon imageIconWinner = new ImageIcon("./images/success.png");
	

	public GameGUI(ArrayList<Piece> pieces, boolean admin, boolean allhuman) throws IOException{

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

		/*
		s1.setEnabled(false);
		s1.setBackground(new Color (225,221,176,200));
		s1.setBorder(BorderFactory.createLoweredBevelBorder());
		s1.setBounds(270, 270, 40, 40);
		 */
		
		
		
		
		
		boardLayeredPane = new Panel_with_background(new ImageIcon("./images/background.png").getImage(), pieces);
		boardLayeredPane.setPreferredSize(new Dimension(580, 580));
		
		
		
	
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
		panelbox.add(panel3, BorderLayout.CENTER);
		panelbox.add(panel4, BorderLayout.SOUTH);
		
		
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
		
		
		/*
		 * 
		 * 

DropShadow shadow = new DropShadow();
Button button = new Button();
button.setEffect(shadow);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		panel4.add(slider);
		*/
		
		panel4.setLayout(new GridLayout());
		//panel4.setBorder(new EmptyBorder(0,0,10,0));
		left.setEditable(false);
		left.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		left.setHorizontalAlignment(JTextField.RIGHT);
		left.setBackground(getForeground());
		left.setBorder(new EmptyBorder(0,0,0,0));
		
		
		
		right.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		right.setEditable(false);
		right.setBackground(getForeground());
		right.setBorder(new EmptyBorder(0,0,0,0));
		
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(25);
		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(100);
		//slider.setPaintLabels(true);
		slider.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		slider.setOrientation(SwingConstants.HORIZONTAL);
		slider.addChangeListener(e -> setbotpace());

		
		int i=500;
		SleepThread.setPace(i);
		
		
		if (allhuman==false) {
		panel4.setBorder(new EmptyBorder(0,0,10,0));
		panel4.add(left);
		panel4.add(slider);
		panel4.add(right);
		}
		
		

		
		
		btnWuerfel.setText("Dice");
		//btnWuerfel.setBorder(new MyBorder());
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
		//btnOption0.setBorder(new MyBorder());
		btnOption0.setActionCommand(OPTION_0);
		btnOption0.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption0.setBorder(new EmptyBorder(0,0,0,0));
		btnOption0.setBackground(new Color(255,255,255,255));
		//btnOption0.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnOption0);


		btnOption1.setText("1");
		//btnOption1.setBorder(new MyBorder());
		btnOption1.setActionCommand(OPTION_1);
		btnOption1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption1.setBorder(new EmptyBorder(0,0,0,0));
		btnOption1.setBackground(new Color(255,255,255,255));
		panel2.add(btnOption1);
		//panel2.add( Box.createRigidArea( new Dimension( 1 , 0 ) )  ); für den Abstand zwischen den Buttons, hat nicht geklappt

		btnOption2.setText("2");
		//btnOption2.setBorder(new MyBorder());
		btnOption2.setActionCommand(OPTION_2);
		btnOption2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption2.setBorder(new EmptyBorder(0,0,0,0));
		btnOption2.setBackground(new Color(255,255,255,255));

		//btnOption2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panel2.add(btnOption2);
	

		btnOption3.setText("3");
		//btnOption3.setBorder(new MyBorder());
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
		
	/*
		if (btnOption0.isEnabled()==true) {
			btnOption0.setBackground(new Color(136,136,136,200));
		} else if (btnOption0.isEnabled()==false) {
			btnOption0.setBackground(new Color(255,255,255,255));
		}
		
		if (btnOption1.isEnabled()== true) {
			btnOption1.setBackground(new Color(136,136,136,200));
		} else if (btnOption1.isEnabled()== false) {
			btnOption1.setBackground(new Color(255,255,255,255));
		}
		
		if (btnOption2.isEnabled()==true) {
			btnOption2.setBackground(new Color(136,136,136,200));
		} else if  (btnOption2.isEnabled()==false){
			btnOption2.setBackground(new Color(255,255,255,255));
		}
		
		if (btnOption3.isEnabled()==true) {
			btnOption3.setBackground(new Color(136,136,136,200));
		} else if (btnOption3.isEnabled()==false){
			btnOption3.setBackground(new Color(255,255,255,255));
		}
 */
		
		
		
		done(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

		
	
	private void setbotpace() {
		SleepThread.setPace(slider.getValue());
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
	
	public void done(PlayerColor[] winner) throws IOException {
		
		int x=205;
		int y=88;
		int z=115+5;
		
		BufferedImage myPicture = ImageIO.read(new File("./images/success_2.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setBounds(265,7,50,50);
		boardLayeredPane.add(picLabel);
		

		BufferedImage myPicture2 = ImageIO.read(new File("./images/first.png"));
		JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
		picLabel2.setBounds(x,y,50,50);
		boardLayeredPane.add(picLabel2);
		
		BufferedImage myPicture3 = ImageIO.read(new File("./images/second.png"));
		JLabel picLabel3 = new JLabel(new ImageIcon(myPicture3));
		picLabel3.setBounds(x,y+z,50,50);
		boardLayeredPane.add(picLabel3);
		
		BufferedImage myPicture4 = ImageIO.read(new File("./images/third.png"));
		JLabel picLabel4 = new JLabel(new ImageIcon(myPicture4));
		picLabel4.setBounds(x,y+2*z,50,50);
		boardLayeredPane.add(picLabel4);
		
		
		//
		
		BufferedImage myPicture6 = ImageIO.read(new File("./images/first.png"));
		JLabel picLabel6 = new JLabel(new ImageIcon(myPicture6));
		picLabel6.setBounds(325,y,50,50);
		boardLayeredPane.add(picLabel6);
		
		BufferedImage myPicture7 = ImageIO.read(new File("./images/second.png"));
		JLabel picLabel7 = new JLabel(new ImageIcon(myPicture7));
		picLabel7.setBounds(325,y+z,50,50);
		boardLayeredPane.add(picLabel7);
		
		BufferedImage myPicture8 = ImageIO.read(new File("./images/third.png"));
		JLabel picLabel8 = new JLabel(new ImageIcon(myPicture8));
		picLabel8.setBounds(325,y+2*z,50,50);
		boardLayeredPane.add(picLabel8);
		//
		
		
		
		RoundButton first = new RoundButton ("",20, false);
		first.setVisible(true);
		first.setText("Winner");
		//first.setBorder(new MyBorder());
		first.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		first.setBorder(new EmptyBorder(0,0,0,0));
		first.setBackground(new Color(255,255,255,255));
		first.setBounds(0,0,200,100);
		first.setLayout(new GridLayout());
		first.setLocation(190,61);
		first.setEnabled(false);
		boardLayeredPane.add(first);
		

		RoundButton second = new RoundButton ("",20, false);
		second.setVisible(true);
		second.setText("Second");
		//second.setBorder(new MyBorder());
		second.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		second.setBorder(new EmptyBorder(0,0,0,0));
		second.setBackground(new Color(255,255,255,255));
		second.setBounds(0,0,200,100);
		second.setLayout(new GridLayout());
		second.setLocation(190,181);
		second.setEnabled(false);

		boardLayeredPane.add(second);
		
		RoundButton third = new RoundButton ("",20, false);
		third.setVisible(true);
		third.setText("Third");
		//third.setBorder(new MyBorder());
		third.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		third.setBorder(new EmptyBorder(0,0,0,0));
		third.setBackground(new Color(255,255,255,255));
		third.setBounds(0,0,200,100);
		third.setLayout(new GridLayout());
		third.setLocation(190,301);
		third.setEnabled(false);
		boardLayeredPane.add(third);
		
	
		RoundButton fourth = new RoundButton ("",20, false);
		fourth.setVisible(true);
		fourth.setText("Fourth");
		//fourth.setBorder(new MyBorder());
		fourth.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		fourth.setBorder(new EmptyBorder(0,0,0,0));
		fourth.setBackground(new Color(255,255,255,255));
		fourth.setBounds(0,0,200,100);
		fourth.setLayout(new GridLayout());
		fourth.setLocation(190,421);
		fourth.setEnabled(false);

		boardLayeredPane.add(fourth);
		
		RoundButton again = new RoundButton ("",20, false);
		again.setVisible(true);
		again.setText("Play again?");
		//again.setBorder(new MyBorder());
		again.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		again.setBorder(new EmptyBorder(0,0,0,0));
		again.setBackground(new Color(255,255,255,255));
		again.setBounds(0,0,100,50);
		again.setLayout(new GridLayout());
		again.setLocation(470,520);
		again.setEnabled(true);
		
		
		boardLayeredPane.add(again);
		
		
		RoundButton black = new RoundButton("", 0, false);
		black.setEnabled(false);
		black.setPreferredSize(new Dimension(700,700));
		black.setBounds(0,0,700,700);
		black.setBorder(new EmptyBorder(0,0,0,0));
		black.setBackground(new Color(0,0,0,200));
		black.setVisible(true);
		boardLayeredPane.add(black);
		
		/*
		try {
	          image = ImageIO.read(new File("./images/success.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
		boardLayeredPane.drawImage(image, 0, 0, this); 
	*/
		
		
		
		/*
		 * Probleme zu beheben:
		 * 
		 * Bilder einbinden funktioniert nicht
		 * Button erneut spielen ruft SetUpGUI nicht auf
		 * Farbe Buttons Winner werden nicht eingebunden
		 * done Funktion wird immer ausgeführt, soll aber nur wenn beendet
		 * 
		 * 
		 */
		
		
			/*	
		int first= 1;
		int second=2;
		int third=3;
		int fourth=4;
		*/
		
		
		
	}
	
	
	

	
}















