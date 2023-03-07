package main.gui;

//Imports
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
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameGUI extends GUI {

	public static final String WUERFELN = "WUERFELN";
	public static final String OPTION_0 = "OPTION_0";
	public static final String OPTION_1 = "OPTION_1";
	public static final String OPTION_2 = "OPTION_2";
	public static final String OPTION_3 = "OPTION_3";
	public static final String ADMIN = "ADMIN";
	public static final String AGAIN = "AGAIN";

	//Button's
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

	//Panel's
	private JPanel panelTop = new JPanel();
	private JPanel panelBottomTop = new JPanel();
	private JPanel panelBottomCenter = new JPanel();
	private JPanel panelBottomBottom = new JPanel();
	private JPanel panelBottom = new JPanel();

	//Slider
	private JSlider sliderBotSpeed = new JSlider(10, 1010, 510);

	//Textfield's
	private JTextField tfInstruction = new RoundJTextField(20);
	private JTextField tfAdmin = new RoundJTextField(20);
	private JTextField tfSliderLeft = new JTextField("fast");
	private JTextField tfSliderRight = new JTextField("slow");

	//ImageIcon's
	private ImageIcon imageIconDiced1 = new ImageIcon(this.getClass().getResource("images/1.png"));
	private ImageIcon imageIconDiced2 = new ImageIcon(this.getClass().getResource("images/2.png"));
	private ImageIcon imageIconDiced3 = new ImageIcon(this.getClass().getResource("images/3.png"));
	private ImageIcon imageIconDiced4 = new ImageIcon(this.getClass().getResource("images/4.png"));
	private ImageIcon imageIconDiced5 = new ImageIcon(this.getClass().getResource("images/5.png"));
	private ImageIcon imageIconDiced6 = new ImageIcon(this.getClass().getResource("images/6.png"));
	private ImageIcon imageIconWreath = new ImageIcon(this.getClass().getResource("images/wreath.png"));
	private ImageIcon imageIconFirstPlace = new ImageIcon(this.getClass().getResource("images/first.png"));
	private ImageIcon imageIconSecondPlace = new ImageIcon(this.getClass().getResource("images/second.png"));
	private ImageIcon imageIconThirdPlace = new ImageIcon(this.getClass().getResource("images/third.png"));
	private ImageIcon imageIconKonfetti = new ImageIcon(this.getClass().getResource("images/konfetti.gif"));

	//Label's
	private JLabel labelDicedImage;
	private JLabel labelWreathImage = new JLabel(imageIconWreath);
	private JLabel labelFirstPlaceImage = new JLabel(imageIconFirstPlace);
	private JLabel labelSecondPlaceImage = new JLabel(imageIconSecondPlace);
	private JLabel labelThirdPlaceImage = new JLabel(imageIconThirdPlace);
	private JLabel labelFirstPlaceImage2 = new JLabel(imageIconFirstPlace);
	private JLabel labelThirdPlaceImage2 = new JLabel(imageIconThirdPlace);
	private JLabel labelSecondPlaceImage2 = new JLabel(imageIconSecondPlace);
	private JLabel labelKonfettiImage = new JLabel(imageIconKonfetti);
	
	//Sound Einstellungen
	private String DIR_SEPERATOR = java.io.File.separator;
	private int volumeSounds = 80;
	private static float MINIMAL_GAIN = -30f;
	
	//Spielfeld
	private PanelGameBoard boardLayeredPane;
	
	/**
	 * Konstruktor
	 * initialisiert Spieloberfläche mit individuellen Einstellungen
	 * 
	 * @param admin true wenn Admin-Checkbox ausgewählt
	 * @param allHuman true wenn keine Bots ausgewählt
	 */
	public GameGUI(ArrayList<Piece> pieces, boolean admin, boolean allHuman) {
	
		//Konstruktor
		this.setTitle("Mensch ärgere dich nicht!");

		boardLayeredPane = new PanelGameBoard(new ImageIcon(this.getClass().getResource("images/background.png")).getImage(), pieces);
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

		// wenn Admin ausgewählt, erweitert sich die Oberfläche für den Admin-Bereich
		if(admin) {
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
		sliderBotSpeed.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		sliderBotSpeed.setOrientation(SwingConstants.HORIZONTAL);
		sliderBotSpeed.addChangeListener(e -> BotThread.setPace(sliderBotSpeed.getValue()));
		sliderBotSpeed.setValue(BotThread.getPace());

		/*
		 *  Soll verhindern dass der Slider zur Steuerung der Botgeschwindigkeit 
		 * angezeigt wird wenn nur Menschen im Spiel sind
		 */ 
		if(!allHuman) {
			panelBottomBottom.setBorder(new EmptyBorder(0, 0, 10, 0));
			panelBottomBottom.add(tfSliderLeft);
			panelBottomBottom.add(sliderBotSpeed);
			panelBottomBottom.add(tfSliderRight);
		}


		btnDice.setText("Dice");
		btnDice.setActionCommand(WUERFELN);
		btnDice.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnDice.setBorder(new EmptyBorder(0, 0, 0, 0)); //ganzer Button orange bei klick, nicht nur rahmen
		btnDice.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnDice);

		labelDicedImage = new JLabel("", imageIconDiced6, JLabel.CENTER);
		labelDicedImage.setPreferredSize(new Dimension(50, 50));
		panelBottomTop.add(labelDicedImage);

		btnOption0.setText("0");
		btnOption0.setActionCommand(OPTION_0);
		btnOption0.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption0.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption0.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnOption0);


		btnOption1.setText("1");
		btnOption1.setActionCommand(OPTION_1);
		btnOption1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption1.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnOption1);


		btnOption2.setText("2");
		btnOption2.setActionCommand(OPTION_2);
		btnOption2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption2.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption2.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnOption2);


		btnOption3.setText("3");
		btnOption3.setActionCommand(OPTION_3);
		btnOption3.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnOption3.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnOption3.setBackground(new Color(255, 255, 255, 255));
		panelBottomTop.add(btnOption3);

		// wenn Admin Checkbox ausgewählt
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
		/* Sicherheitswarnung beim schließen
		*  statt DefaultCloseOperation eigene Methode und
		*  Do_Nothing_On_Close
		*/
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

	//Getter
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

	/**
	 * Setzt Bild der gewürfelten Zahl
	 * @param diced übergibt die gewürfelte Zahl
	 */
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

	/**
	 * Färbt den oberen Teil des Fensters genau in der Farbe des Spielers, 
	 * der an der Reihe ist
	 *
	 * @param playercolor
	 * übergibt die Farbe des Spielers, der an der Reihe ist
	 */
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

	
	/**
	 * Spielneustart durch Play again Button
	 */
	public void stopGame() {
		this.setVisible(false);
		Main.startSetUp();
	}

	
	/**
	 * Endbildschirm/Siegeranzeige
	 * 
	 * Holt sich das erste Element im Array - den Sieger- 
	 * und färbt den oberen Bereich des Fensters in der entsprechenden Farbe
	 * 
	 * platziert Bilder wie Pokal, Siegeskranz, Medaillien für Platz 2 und 3
	 *
	 * erstellt einen runden Button der zentral platziert wird für alle 4 Spieler
	 * erstellt eine Option um nochmal zu spielen
	 * 
	 * färbt die Buttons entsprechend der Reihenfolge des winner-Arrays ein
	 *
	 * @param winner
	 * übergibt einen Array an Farben, der nach Platzierung sortiert ist
	 */
	public void showEndScreen(PlayerColor[] winner) {
		playEndScreenCelebration();
		

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
		
		labelFirstPlaceImage2.setBounds(325, y, 50, 50);
		boardLayeredPane.add(labelFirstPlaceImage2);

		labelSecondPlaceImage2.setBounds(325, y + z, 50, 50);
		boardLayeredPane.add(labelSecondPlaceImage2);

		labelThirdPlaceImage2.setBounds(325, y + 2 * z, 50, 50);
		boardLayeredPane.add(labelThirdPlaceImage2);		


		rbtnFirstPlace.setVisible(true);
		rbtnFirstPlace.setText("Winner");
		rbtnFirstPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnFirstPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnFirstPlace.setBounds(0, 0, 200, 100);
		rbtnFirstPlace.setLayout(new GridLayout());
		rbtnFirstPlace.setLocation(190, 61);
		boardLayeredPane.add(rbtnFirstPlace);


		rbtnSecondPlace.setVisible(true);
		rbtnSecondPlace.setText("Second");
		rbtnSecondPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnSecondPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnSecondPlace.setBounds(0, 0, 200, 100);
		rbtnSecondPlace.setLayout(new GridLayout());
		rbtnSecondPlace.setLocation(190, 181);
		boardLayeredPane.add(rbtnSecondPlace);


		rbtnThirdPlace.setVisible(true);
		rbtnThirdPlace.setText("Third");
		rbtnThirdPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnThirdPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnThirdPlace.setBounds(0, 0, 200, 100);
		rbtnThirdPlace.setLayout(new GridLayout());
		rbtnThirdPlace.setLocation(190, 301);
		boardLayeredPane.add(rbtnThirdPlace);


		rbtnFourthPlace.setVisible(true);
		rbtnFourthPlace.setText("Fourth");
		rbtnFourthPlace.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		rbtnFourthPlace.setBorder(new EmptyBorder(0, 0, 0, 0));
		rbtnFourthPlace.setBounds(0, 0, 200, 100);
		rbtnFourthPlace.setLayout(new GridLayout());
		rbtnFourthPlace.setLocation(190, 421);
		boardLayeredPane.add(rbtnFourthPlace);


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
	
	/**
	 * Klasse zum asynchronen abspielen von Spielsounds
	 */
	public class SoundRunnable implements Runnable {
		
		/**
		 * erstellt den Pfad zum Sound
		 * 
		 * stellt die Lautstärke ein und gibt die Möglichkeit den Sound zu stoppen
		 * @Credits Softwareprojekt der letzten Jahre (Semster 4+5)
		 */
		final String soundUrl;
		
		public SoundRunnable(String soundUrl)
		{
			this.soundUrl = soundUrl;
		}
		
		@Override
		public void run() {
			Clip clip;
	
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("sounds/"+soundUrl)));
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float effectsGain = (100f - (float)volumeSounds) / 100f * MINIMAL_GAIN;
				gainControl.setValue(effectsGain);
				clip.start();
				Thread.sleep(clip.getMicrosecondLength()/1000);
				clip.stop();
				
				
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/* 
	 * erstellt einen neuen Thread sobald ein Sound aufgerufen wird, damit Sounds auch übereinander laufen können
	 */
	private void playSound(String soundUrl) {
			Thread soundThread = new Thread(new SoundRunnable(soundUrl));
			soundThread.start();
		}

	/* 
	 * Spielt den Jubelsound ab
	 */
	private void playEndScreenCelebration() {
		playSound("Celebration.wav");
	}
	
	/* 
	 * Kreiert eine zufällige Zahl zwischen 1 und 8
	 * 
	 * Wählt basierend auf der Zahl einen zufälligen Würfelsound aus damit es nicht jedes mal der gleiche ist
	 */
	public void playDiceSound()
	{
		Random randomSound = new Random();
		String [] sounds = new String [] {"DiceSound1.wav","DiceSound2.wav","DiceSound3.wav","DiceSound4.wav","DiceSound5.wav","DiceSound6.wav","DiceSound7.wav","DiceSound8.wav"};
		int n = randomSound.nextInt(8);
		playSound(sounds[n]);
	}
}

