package main.gui;

//Import's
import main.controller.GameController;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.player.Bot;
import main.model.player.Gamer;
import main.model.player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class SetupGUI extends GUI {

	private final String[] playerOptions = {"Mensch", "Bot"};

	private JTextField labelWhatToDo = new JTextField();
	private JTextField labelWhatToDo2 = new JTextField();
	private JTextField labelWhatToDo3 = new JTextField();

	private JTextField tfRed = new JTextField();
	private JTextField tfBlue = new JTextField();
	private JTextField tfGreen = new JTextField();
	private JTextField tfYellow = new JTextField();
	

	private JPanel panelText = new JPanel();
	private JPanel panelButtonsRed = new JPanel();
	private JPanel panelButtonsBlue = new JPanel();
	private JPanel panelButtonsGreen = new JPanel();
	private JPanel panelButtonsYellow = new JPanel();
	private JPanel panelFooter = new JPanel();

	
	private RoundButton2 buttonContinue = new RoundButton2("", 20, false);
	
	private RoundButton2 btnRedHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnRedBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnBlueHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnBlueBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnGreenHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnGreenBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnYellowHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnYellowBot = new RoundButton2("", 20, false);
	
	boolean clickedRedHuman = false;
	boolean clickedBlueHuman = false;
	boolean clickedGreenHuman = false;
	boolean clickedYellowHuman = false;
	
	boolean clickedRedBot = false;
	boolean clickedBlueBot = false;
	boolean clickedGreenBot = false;
	boolean clickedYellowBot = false;
	
	private int counterRedHuman=0;
	private int counterRedBot=0;
	private int counterBlueHuman=0;
	private int counterBlueBot=0;
	private int counterGreenHuman=0;
	private int counterGreenBot=0;
	private int counterYellowHuman=0;
	private int counterYellowBot=0;


	private JCheckBox cbAdmin = new JCheckBox("Admin", false);

	/**
	 * Konstruktor
	 * initialisiert die Einstellungs GUI mit möglichen Optionen
	 * um Bot, Mensch und Admin zu wählen
	 */
	public SetupGUI() {
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./images/logo.png"));
		this.setTitle("Mensch ärgere dich nicht!");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(500, 200);
		this.setSize(520, 520);
		

		
		labelWhatToDo.setText(" Please select 'Person' if you want to play by yourself or ");
		labelWhatToDo2.setText(" select 'Bot' if you want the computer to play. ");
		labelWhatToDo3.setText(" 'Go!' will start the game! ");

		labelWhatToDo.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		labelWhatToDo2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		labelWhatToDo3.setFont(new Font("Lexend Deca", Font.BOLD, 15));

		labelWhatToDo.setEditable(false);
		labelWhatToDo2.setEditable(false);
		labelWhatToDo3.setEditable(false);

		labelWhatToDo.setBorder(null);
		labelWhatToDo2.setBorder(null);
		labelWhatToDo3.setBorder(null);

		labelWhatToDo.setBackground(getForeground());
		labelWhatToDo2.setBackground(getForeground());
		labelWhatToDo3.setBackground(getForeground());

		
		
		panelText.setBorder(new EmptyBorder(5,5,5,5));
		panelText.add(labelWhatToDo, BorderLayout.CENTER);
		panelText.add(labelWhatToDo2, BorderLayout.CENTER);
		panelText.add(labelWhatToDo3, BorderLayout.CENTER);
		
		this.add(panelText, BorderLayout.NORTH);
		
		
		tfRed.setText("Red");
		tfBlue.setText("Blue");
		tfGreen.setText("Green");
		tfYellow.setText("Yellow");
		
		tfRed.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		tfBlue.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		tfGreen.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		tfYellow.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		tfRed.setEditable(false);
		tfBlue.setEditable(false);
		tfGreen.setEditable(false);
		tfYellow.setEditable(false);
		
		tfRed.setBorder(null);
		tfBlue.setBorder(null);
		tfGreen.setBorder(null);
		tfYellow.setBorder(null);
		
		tfRed.setBackground(getForeground());
		tfBlue.setBackground(getForeground());
		tfGreen.setBackground(getForeground());
		tfYellow.setBackground(getForeground());
		
		
		
		
		//<Red>
		btnRedHuman.setText("Person");
		btnRedBot.setText("Bot");
		
		btnRedHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnRedBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnRedHuman.setBackground(new Color(255,255,255,255));
		btnRedBot.setBackground(new Color(255,255,255,255));
		
		btnRedHuman.addActionListener(e -> {
			btnRedHuman.setBackground(new Color(255,0,0,255));
			clickedRedHuman=true;
			btnRedBot.setEnabled(false);
			counterRedHuman +=1;
			
			if (counterRedHuman%2==0) {
				btnRedHuman.setBackground(new Color(255,255,255,255));
				btnRedHuman.setEnabled(true);
				btnRedBot.setEnabled(true);
				clickedRedHuman=false;
			}
			checkStartGame();
		});
		
		
		btnRedBot.addActionListener(e -> {
			btnRedBot.setBackground(new Color(255,0,0,255));
			clickedRedBot= true;
			btnRedHuman.setEnabled(false);
			counterRedBot +=1;
			
			if (counterRedBot%2==0) {
				btnRedBot.setBackground(new Color(255,255,255,255));
				btnRedHuman.setEnabled(true);
				btnRedBot.setEnabled(true);	
				clickedRedBot= false;
			}
			checkStartGame();
		});
		
		
		btnRedHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnRedBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsRed.setLayout(new GridLayout());
		panelButtonsRed.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsRed.add(btnRedHuman);
		
		tfRed.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsRed.add(tfRed);
		
		panelButtonsRed.add(btnRedBot);
		this.add(panelButtonsRed);
		//</Red>
		
		
		
		
		
		//<Blue>
		btnBlueHuman.setText("Person");
		btnBlueBot.setText("Bot");
		
		btnBlueHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnBlueBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnBlueHuman.setBackground(new Color(255,255,255,255));
		btnBlueBot.setBackground(new Color(255,255,255,255));
		
		btnBlueHuman.addActionListener(e -> {
			btnBlueHuman.setBackground(new Color(0,0,255,200));
			clickedBlueHuman = true;
			btnBlueBot.setEnabled(false);
			counterBlueHuman +=1;
			
			if (counterBlueHuman%2==0) {
				btnBlueHuman.setBackground(new Color(255,255,255,255));
				btnBlueHuman.setEnabled(true);
				btnBlueBot.setEnabled(true);
				clickedBlueHuman=false;
			}
			checkStartGame();

		});
		btnBlueBot.addActionListener(e -> {
			btnBlueBot.setBackground(new Color(0,0,255,200));
			clickedBlueBot= true;
			btnBlueHuman.setEnabled(false);
			counterBlueBot +=1;
			
			if (counterBlueBot%2==0) {
				btnBlueBot.setBackground(new Color(255,255,255,255));
				btnBlueHuman.setEnabled(true);
				btnBlueBot.setEnabled(true);	
				clickedBlueBot= false;
			}
			checkStartGame();

		});
		btnBlueHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBlueBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsBlue.setLayout(new GridLayout());
		panelButtonsBlue.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsBlue.add(btnBlueHuman);
		
		tfBlue.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsBlue.add(tfBlue);
	
		panelButtonsBlue.add(btnBlueBot);
		this.add(panelButtonsBlue);
		//</Blue>
		
		
		
		

		//<Green>
		btnGreenHuman.setText("Person");
		btnGreenBot.setText("Bot");
		
		btnGreenHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnGreenBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnGreenHuman.setBackground(new Color(255,255,255,255));
		btnGreenBot.setBackground(new Color(255,255,255,255));
		
		btnGreenHuman.addActionListener(e -> {
			btnGreenHuman.setBackground(new Color(0,255,0,200));
			clickedGreenHuman = true;
			btnGreenBot.setEnabled(false);
			counterGreenHuman +=1;
			
			if (counterGreenHuman%2==0) {
				btnGreenHuman.setBackground(new Color(255,255,255,255));
				btnGreenHuman.setEnabled(true);
				btnGreenBot.setEnabled(true);
				clickedGreenHuman=false;
			}
			checkStartGame();
		
		});
		btnGreenBot.addActionListener(e -> {
			btnGreenBot.setBackground(new Color(0,255,0,200));
			clickedGreenBot= true;
			btnGreenHuman.setEnabled(false);
			counterGreenBot +=1;
			
			if (counterGreenBot%2==0) {
				btnGreenBot.setBackground(new Color(255,255,255,255));
				btnGreenHuman.setEnabled(true);
				btnGreenBot.setEnabled(true);	
				clickedGreenBot= false;
			}
			checkStartGame();
			
		});
		btnGreenHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnGreenBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsGreen.setLayout(new GridLayout());
		panelButtonsGreen.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsGreen.add(btnGreenHuman);
		
		tfGreen.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsGreen.add(tfGreen);

		panelButtonsGreen.add(btnGreenBot);
		this.add(panelButtonsGreen);
		//</Green>
		
		
		
		
		
		//<Yellow>				
		btnYellowHuman.setText("Person");
		btnYellowBot.setText("Bot");
				
		btnYellowHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnYellowBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnYellowHuman.setBackground(new Color(255,255,255,255));
		btnYellowBot.setBackground(new Color(255,255,255,255));
		
		btnYellowHuman.addActionListener(e -> {
			btnYellowHuman.setBackground(new Color(255,255,0,200));
			clickedYellowHuman = true;
			btnYellowBot.setEnabled(false);
			counterYellowHuman +=1;
			
			if (counterYellowHuman%2==0) {
				btnYellowHuman.setBackground(new Color(255,255,255,255));
				btnYellowHuman.setEnabled(true);
				btnYellowBot.setEnabled(true);
				clickedYellowHuman=false;
			}
			checkStartGame();
		});
		btnYellowBot.addActionListener(e -> {
			btnYellowBot.setBackground(new Color(255,255,0,200));
			clickedYellowBot= true;
			btnYellowHuman.setEnabled(false);
			counterYellowBot +=1;
			
			if (counterYellowBot%2==0) {
				btnYellowBot.setBackground(new Color(255,255,255,255));
				btnYellowHuman.setEnabled(true);
				btnYellowBot.setEnabled(true);	
				clickedYellowBot= false;
			}
			checkStartGame();
		});
		btnYellowHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnYellowBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsYellow.setLayout(new GridLayout());
		panelButtonsYellow.setBorder(new EmptyBorder(20,50,20,50));
		panelButtonsYellow.add(btnYellowHuman);
		
		tfYellow.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsYellow.add(tfYellow);
		
	
		panelButtonsYellow.add(btnYellowBot);
		this.add(panelButtonsYellow);
		//</Yellow>
		
		
		
		buttonContinue.setBackground(new Color(255,255,255,255));
		buttonContinue.setText("Go!");
		buttonContinue.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		buttonContinue.setBorder(new EmptyBorder(0,0,0,0));
		buttonContinue.setEnabled(false);
		
		
		
		
		buttonContinue.addActionListener(e -> {
			startGame();
		});
		buttonContinue.setPreferredSize(new Dimension(100,50));
		
		
		
		
		
		
		
		panelFooter.add(buttonContinue);

		panelFooter.add(cbAdmin);

		panelFooter.setBorder(new EmptyBorder(5, 5, -30, 245));
		this.add(panelFooter);


		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeGUI();
			}
		});
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
	}

	/**
	 * Startet das Spiel und erkennt ob Mensch oder Bot ausgewählt
	 * Erkennt ob nur Menschen ausgewählt wurden
	 * 
	 * Initialisiert das Spiel mit den eingegebenen Einstllungen und übergibt die Daten
	 * an Modeler, Controller, und GUI
	 */
	private void startGame() {

		boolean allHuman = true;

		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();

		
		if(clickedRedHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.RED));
			allHuman = false;
		}

		if(clickedBlueHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.BLUE));
			allHuman = false;
		}

		if(clickedGreenHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.GREEN));
			allHuman = false;
		}

		if(clickedYellowHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.YELLOW));

		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.YELLOW));
			allHuman = false;
		}

		GameModel gameModel = new GameModel(INITIAL_PLAYERS);
		GameGUI gameGui = new GameGUI(gameModel.getPieces(), cbAdmin.isSelected(), allHuman);
		GameController gameController = new GameController(gameModel, gameGui);

		this.setVisible(false);
	}

	/**
	 *  zeigt die Setup GUI
	 */
	public void showGUI() {
		this.setVisible(true);
	}
	
	/**
	 * Deaktiviert den Go Button solange bis auf jeder Ebene eine Einstellung ausgewählt wurde
	 */
	private void checkStartGame() {
		
		if ((clickedRedHuman || clickedRedBot) &&
			(clickedBlueHuman || clickedBlueBot) && 
			(clickedGreenHuman || clickedGreenBot) && 
			(clickedYellowHuman || clickedYellowBot)) {
				buttonContinue.setEnabled(true);
			} 
		else {
			buttonContinue.setEnabled(false);
		}	
	}
}
