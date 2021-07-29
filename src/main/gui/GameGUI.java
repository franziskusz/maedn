package main.gui;

import main.Main;
import main.controller.BotThread;
import main.model.enums.PlayerColor;
import main.model.player.Piece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.print.attribute.standard.Media;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameGUI extends GUI {

	public static final String WUERFELN = "WUERFELN";
	public static final String OPTION_0 = "OPTION_0";
	public static final String OPTION_1 = "OPTION_1";
	public static final String OPTION_2 = "OPTION_2";
	public static final String OPTION_3 = "OPTION_3";
	public static final String ADMIN = "ADMIN";
	public static final String AGAIN = "AGAIN";

	private RoundButton btnDice = new RoundButton("", 20, false);
	private RoundButton btnOption0 = new RoundButton("", 20, false);
	private RoundButton btnOption1 = new RoundButton("", 20, false);
	private RoundButton btnOption2 = new RoundButton("", 20, false);
	private RoundButton btnOption3 = new RoundButton("", 20, false);
	private RoundButton rbtnBoardLayeredPaneShadow = new RoundButton("", 0, false);
	private RoundButton2 btnAdmin = new RoundButton2("", 20, false);
	private RoundButton2 btnPlayAgain = new RoundButton2("", 20, false);
	private RoundButton2 rbtnFirstPlace = new RoundButton2("", 20, false);
	private RoundButton2 rbtnSecondPlace = new RoundButton2("", 20, false);
	private RoundButton2 rbtnThirdPlace = new RoundButton2("", 20, false);
	private RoundButton2 rbtnFourthPlace = new RoundButton2("", 20, false);

	private JPanel panelTop = new JPanel();
	private JPanel panelBottomTop = new JPanel();
	private JPanel panelBottomCenter = new JPanel();
	private JPanel panelBottomBottom = new JPanel();
	private JPanel panelBottom = new JPanel();

	private JSlider sliderBotSpeed = new JSlider(10, 1010, 510);

	private JTextField tfInstruction = new RoundJTextField(20);
	private JTextField tfAdmin = new RoundJTextField(20);
	private JTextField tfSliderLeft = new JTextField("fast");
	private JTextField tfSliderRight = new JTextField("slow");

	private PanelGameBoard boardLayeredPane;

	private ImageIcon imageIconDiced1 = new ImageIcon("./images/1.png");
	private ImageIcon imageIconDiced2 = new ImageIcon("./images/2.png");
	private ImageIcon imageIconDiced3 = new ImageIcon("./images/3.png");
	private ImageIcon imageIconDiced4 = new ImageIcon("./images/4.png");
	private ImageIcon imageIconDiced5 = new ImageIcon("./images/5.png");
	private ImageIcon imageIconDiced6 = new ImageIcon("./images/6.png");
	private ImageIcon imageIconWreath = new ImageIcon("./images/wreath.png");
	private ImageIcon imageIconFirstPlace = new ImageIcon("./images/first.png");
	private ImageIcon imageIconSecondPlace = new ImageIcon("./images/second.png");
	private ImageIcon imageIconThirdPlace = new ImageIcon("./images/third.png");
	private ImageIcon imageIconKonfetti = new ImageIcon("./images/konfetti.gif");

	private JLabel labelDicedImage;
	private JLabel labelWreathImage = new JLabel(imageIconWreath);
	private JLabel labelFirstPlaceImage = new JLabel(imageIconFirstPlace);
	private JLabel labelSecondPlaceImage = new JLabel(imageIconSecondPlace);
	private JLabel labelThirdPlaceImage = new JLabel(imageIconThirdPlace);
	private JLabel labelFirstPlaceImage2 = new JLabel(imageIconFirstPlace);
	private JLabel labelThirdPlaceImage2 = new JLabel(imageIconThirdPlace);
	private JLabel labelSecondPlaceImage2 = new JLabel(imageIconSecondPlace);
	private JLabel labelKonfettiImage = new JLabel(imageIconKonfetti);


	public GameGUI(ArrayList<Piece> pieces, boolean admin, boolean allHuman) {

		this.setTitle("Mensch ärgere dich nicht!");

		boardLayeredPane = new PanelGameBoard(new ImageIcon("./images/background.png").getImage(), pieces);
		boardLayeredPane.setPreferredSize(new Dimension(580, 580));
		this.add(boardLayeredPane, BorderLayout.CENTER);

		panelTop.setPreferredSize(new Dimension(580, 50));
		panelTop.setLayout(new GridBagLayout());
		this.add(panelTop, BorderLayout.NORTH);

		panelBottomTop.setLayout(new GridLayout(1, 6, 10, 0));
		panelBottomTop.setPreferredSize(new Dimension(580, 80));
		panelBottomTop.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelBottomCenter.setLayout(new GridLayout(1, 6, 10, 0));
		panelBottomCenter.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelBottom.setLayout(new BorderLayout());
		panelBottom.add(panelBottomTop, BorderLayout.NORTH);
		panelBottom.add(panelBottomCenter, BorderLayout.CENTER);
		panelBottom.add(panelBottomBottom, BorderLayout.SOUTH);


		if(admin) {
			// Höhe des Panels anpassen, damit Buttons noch angezeigt werden
			panelBottomCenter.setPreferredSize(new Dimension(580, 50));
		} else {
			panelBottomCenter.setPreferredSize(new Dimension(580, 0));
		}
		this.add(panelBottom, BorderLayout.SOUTH);


		tfInstruction.setHorizontalAlignment(JTextField.CENTER);
		tfInstruction.setEditable(false);
		tfInstruction.setText("?");
		tfInstruction.setPreferredSize(new Dimension(200, 24));
		tfInstruction.setBackground(new Color(255, 255, 255, 255));
		tfInstruction.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		panelTop.add(tfInstruction);


		panelBottomBottom.setLayout(new GridLayout());
		//panel4.setBorder(new EmptyBorder(0,0,10,0));
		tfSliderLeft.setEditable(false);
		tfSliderLeft.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		tfSliderLeft.setHorizontalAlignment(JTextField.RIGHT);
		tfSliderLeft.setBackground(getForeground());
		tfSliderLeft.setBorder(new EmptyBorder(0, 0, 0, 0));


		tfSliderRight.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		tfSliderRight.setEditable(false);
		tfSliderRight.setBackground(getForeground());
		tfSliderRight.setBorder(new EmptyBorder(0, 0, 0, 0));

		sliderBotSpeed.setPaintTicks(true);
		sliderBotSpeed.setMinorTickSpacing(25);
		sliderBotSpeed.setPaintTrack(true);
		sliderBotSpeed.setMajorTickSpacing(100);
		//slider.setPaintLabels(true);
		sliderBotSpeed.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		sliderBotSpeed.setOrientation(SwingConstants.HORIZONTAL);
		sliderBotSpeed.addChangeListener(e -> BotThread.setPace(sliderBotSpeed.getValue()));


		if(!allHuman) {
			panelBottomBottom.setBorder(new EmptyBorder(0, 0, 10, 0));
			panelBottomBottom.add(tfSliderLeft);
			panelBottomBottom.add(sliderBotSpeed);
			panelBottomBottom.add(tfSliderRight);
		}


		btnDice.setText("Dice");
		//btnWuerfel.setBorder(new MyBorder());
		btnDice.setActionCommand(WUERFELN);
		btnDice.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnDice.setBorder(new EmptyBorder(0, 0, 0, 0)); //ganzer Button orange bei klick, nicht nur rahmen
		btnDice.setBackground(new Color(255, 255, 255, 255));
		//btnWuerfel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panelBottomTop.add(btnDice);

		labelDicedImage = new JLabel("", imageIconDiced6, JLabel.CENTER);
		// @Toni Hier mit der Größe Rumspielen
		labelDicedImage.setPreferredSize(new Dimension(50, 50));
		panelBottomTop.add(labelDicedImage);

		btnOption0.setText("0");
		//btnOption0.setBorder(new MyBorder());
		btnOption0.setActionCommand(OPTION_0);
		btnOption0.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption0.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption0.setBackground(new Color(255, 255, 255, 255));
		//btnOption0.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panelBottomTop.add(btnOption0);


		btnOption1.setText("1");
		//btnOption1.setBorder(new MyBorder());
		btnOption1.setActionCommand(OPTION_1);
		btnOption1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption1.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnOption1);
		//panel2.add( Box.createRigidArea( new Dimension( 1 , 0 ) )  ); für den Abstand zwischen den Buttons, hat nicht geklappt


		btnOption2.setText("2");
		//btnOption2.setBorder(new MyBorder());
		btnOption2.setActionCommand(OPTION_2);
		btnOption2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption2.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption2.setBackground(new Color(255, 255, 255, 255));
		//btnOption2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panelBottomTop.add(btnOption2);


		btnOption3.setText("3");
		//btnOption3.setBorder(new MyBorder());
		btnOption3.setActionCommand(OPTION_3);
		btnOption3.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption3.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption3.setBackground(new Color(255, 255, 255, 255));
		//btnOption3.setBorder(new EmptyBorder(0,10,0,0));
		//btnOption3.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		panelBottomTop.add(btnOption3);


		if(admin) {
			// Admin Elemente anzeigen
			tfAdmin.setPreferredSize(new Dimension(200, 30));
			tfAdmin.setText(" ");
			tfAdmin.setFont(new Font("Lexend Deca", Font.BOLD, 15));
			tfAdmin.setBorder(BorderFactory.createLineBorder(Color.black));
			tfAdmin.setHorizontalAlignment(RoundJTextField.CENTER);
			tfAdmin.setActionCommand(ADMIN);
			panelBottomCenter.add(tfAdmin);

			btnAdmin.setText("Admin");
			btnAdmin.setActionCommand(ADMIN);
			btnAdmin.setFont(new Font("Lexend Deca", Font.BOLD, 15));
			btnAdmin.setBorder(new EmptyBorder(0, 0, 0, 0));
			btnAdmin.setBackground(new Color(255, 255, 255, 255));
			panelBottomCenter.add(btnAdmin);
		}

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeGUI();
			}
		});
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}


	public JButton getBtnDice() {
		return btnDice;
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


	public JButton getBtnPlayAgain() {
		return btnPlayAgain;
	}


	public JTextField getTfInstruction() {
		return tfInstruction;
	}


	public JTextField getTfAdmin() {
		return tfAdmin;
	}


	public PanelGameBoard getBoardLayeredPane() {
		return boardLayeredPane;
	}


	public void setDicedImage(int diced) {
		switch(diced) {
			case 1:
				labelDicedImage.setIcon(imageIconDiced1);
				break;
			case 2:
				labelDicedImage.setIcon(imageIconDiced2);
				break;
			case 3:
				labelDicedImage.setIcon(imageIconDiced3);
				break;
			case 4:
				labelDicedImage.setIcon(imageIconDiced4);
				break;
			case 5:
				labelDicedImage.setIcon(imageIconDiced5);
				break;
			case 6:
				labelDicedImage.setIcon(imageIconDiced6);
				break;
		}
	}


	public void setBackgroundColor(PlayerColor playerColor) {
		
		
		switch(playerColor) {
			case RED:
				panelTop.setBackground(new Color(255, 0, 0, 255));
				break;
			case BLUE:
				panelTop.setBackground(new Color(30, 144, 255, 255));
				break;
			case GREEN:
				panelTop.setBackground(new Color(0, 255, 0, 225));
				break;
			case YELLOW:
				panelTop.setBackground(new Color(255, 255, 0, 225));
				break;
		}
	}


	public void stopGame() {
		this.setVisible(false);
		Main.startSetUp();
	}

	public void showEndScreen(PlayerColor[] winner) {

		switch(winner[0]) {
		
			case RED:
				panelTop.setBackground(new Color(255, 0, 0, 255));
				break;
		
			case BLUE:
				panelTop.setBackground(new Color(30, 144, 255, 255));
				break;
		
			case GREEN:
				panelTop.setBackground(new Color(0, 255, 0, 225));
				break;
		
			case YELLOW:
				panelTop.setBackground(new Color(255, 255, 0, 225));
				break;
		}
		
		
		
		int x = 205;
		int y = 88;
		int z = 115 + 5;
		
		labelKonfettiImage.setBounds(0, 0, 600, 600);
		boardLayeredPane.add(labelKonfettiImage);

		labelWreathImage.setBounds(265, 7, 50, 50);
		boardLayeredPane.add(labelWreathImage);

		labelFirstPlaceImage.setBounds(x, y, 50, 50);
		boardLayeredPane.add(labelFirstPlaceImage);

		labelSecondPlaceImage.setBounds(x, y + z, 50, 50);
		boardLayeredPane.add(labelSecondPlaceImage);

		labelThirdPlaceImage.setBounds(x, y + 2 * z, 50, 50);
		boardLayeredPane.add(labelThirdPlaceImage);

		//

		labelFirstPlaceImage2.setBounds(325, y, 50, 50);
		boardLayeredPane.add(labelFirstPlaceImage2);

		labelSecondPlaceImage2.setBounds(325, y + z, 50, 50);
		boardLayeredPane.add(labelSecondPlaceImage2);

		labelThirdPlaceImage2.setBounds(325, y + 2 * z, 50, 50);
		boardLayeredPane.add(labelThirdPlaceImage2);

		//


		


		rbtnFirstPlace.setVisible(true);
		rbtnFirstPlace.setText("Winner");
		//rbtnFirstPlace.setBorder(new MyBorder());
		rbtnFirstPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnFirstPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnFirstPlace.setBounds(0, 0, 200, 100);
		rbtnFirstPlace.setLayout(new GridLayout());
		rbtnFirstPlace.setLocation(190, 61);
		//rbtnFirstPlace.setEnabled(false);
		boardLayeredPane.add(rbtnFirstPlace);


		rbtnSecondPlace.setVisible(true);
		rbtnSecondPlace.setText("Second");
		//rbtnSecondPlace.setBorder(new MyBorder());
		rbtnSecondPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnSecondPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnSecondPlace.setBounds(0, 0, 200, 100);
		rbtnSecondPlace.setLayout(new GridLayout());
		rbtnSecondPlace.setLocation(190, 181);
		//rbtnSecondPlace.setEnabled(false);
		boardLayeredPane.add(rbtnSecondPlace);


		rbtnThirdPlace.setVisible(true);
		rbtnThirdPlace.setText("Third");
		//rbtnThirdPlace.setBorder(new MyBorder());
		rbtnThirdPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnThirdPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnThirdPlace.setBounds(0, 0, 200, 100);
		rbtnThirdPlace.setLayout(new GridLayout());
		rbtnThirdPlace.setLocation(190, 301);
		//rbtnThirdPlace.setEnabled(false);
		boardLayeredPane.add(rbtnThirdPlace);


		rbtnFourthPlace.setVisible(true);
		rbtnFourthPlace.setText("Fourth");

		rbtnFourthPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnFourthPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnFourthPlace.setBounds(0, 0, 200, 100);
		rbtnFourthPlace.setLayout(new GridLayout());
		rbtnFourthPlace.setLocation(190, 421);
		//rbtnFourthPlace.setEnabled(false);
		boardLayeredPane.add(rbtnFourthPlace);

		// Variable again wird ganz oben deklariert und initialisiert, weil sonst kein getter möglich.
		btnPlayAgain.setVisible(true);
		btnPlayAgain.setText("Play again?");
		btnPlayAgain.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnPlayAgain.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnPlayAgain.setBounds(0, 0, 100, 50);
		btnPlayAgain.setLayout(new GridLayout());
		btnPlayAgain.setLocation(470, 520);
		btnPlayAgain.setEnabled(true);
		btnPlayAgain.setActionCommand(AGAIN);


		boardLayeredPane.add(btnPlayAgain);

		rbtnBoardLayeredPaneShadow.setEnabled(false);
		rbtnBoardLayeredPaneShadow.setPreferredSize(new Dimension(700, 700));
		rbtnBoardLayeredPaneShadow.setBounds(0, 0, 700, 700);
		rbtnBoardLayeredPaneShadow.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnBoardLayeredPaneShadow.setBackground(new Color(0, 0, 0, 200));
		rbtnBoardLayeredPaneShadow.setVisible(true);
		boardLayeredPane.add(rbtnBoardLayeredPaneShadow);

			/*
			 -------------------------------
			|								|
			|								|
			 ------------       ------------
			 	-----	 |     | 	-----
			   |	 | 	 |     |   |     |
			   |  	 |	 |     |   |     |
	           |	 |	 |     |   |     |
			   | 	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	 |     |   |     |
			   |	 |	  -----    |     |          ___
			   |	 |	  		   |     |      ___|  |
   	   -------- 	  -------------       ------      |
      |			 		                            __|  
  	  | 		  	  							___|    
       ----------------------------------------- 
			*/

		RoundButton2[] placement = {rbtnFirstPlace, rbtnSecondPlace, rbtnThirdPlace, rbtnFourthPlace};
		
		
		for(int i = 0; i <= 3; ++i) {
			switch(winner[i]) {
				case RED:
					placement[i].setBackground(new Color(255, 0, 0, 255));
					break;
				case BLUE:
					placement[i].setBackground(new Color(30, 144, 255, 255));
					break;
				case GREEN:
					placement[i].setBackground(new Color(0, 255, 0, 225));
					break;
				case YELLOW:
					placement[i].setBackground(new Color(255, 255, 0, 225));
					break;
			}
		}
	}
}















