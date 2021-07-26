package main.gui;

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
	
	
	

	private JComboBox comboBoxRed = new JComboBox(playerOptions);
	private JComboBox comboBoxBlue = new JComboBox(playerOptions);
	private JComboBox comboBoxGreen = new JComboBox(playerOptions);
	private JComboBox comboBoxYellow = new JComboBox(playerOptions);


	private RoundButton2 buttonContinue = new RoundButton2("", 20, false);
	
	private RoundButton2 btnRedHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnRedBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnBlueHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnBlueBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnGreenHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnGreenBot = new RoundButton2("", 20, false);
	
	private RoundButton2 btnYellowHuman = new RoundButton2("", 20, false);
	private RoundButton2 btnYellowBot = new RoundButton2("", 20, false);
	
	boolean RedHuman = false;
	boolean BlueHuman = false;
	boolean GreenHuman = false;
	boolean YellowHuman = false;
	
	boolean RedBot = false;
	boolean BlueBot = false;
	boolean GreenBot = false;
	boolean YellowBot = false;
	


	private JCheckBox cbAdmin = new JCheckBox("Admin", false);


	public SetupGUI() {
		
		this.setTitle("Mensch ärgere dich nicht!");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(500, 200);
		this.setSize(520, 520);

		
		labelWhatToDo.setText(" Bitte wähle hier 'Mensch', wenn du selbst spielen möchtest und ");
		labelWhatToDo2.setText(" wähle 'Bot' wenn du möchtest, dass der Computer spielen soll. ");
		labelWhatToDo3.setText(" Auf 'Los!' startet das Spiel.");

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
		
		
		tfRed.setText("Rot");
		tfBlue.setText("Blau");
		tfGreen.setText("Grün");
		tfYellow.setText("Gelb");
		
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
		btnRedHuman.setText("Mensch");
		btnRedBot.setText("Bot");
		
		btnRedHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnRedBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnRedHuman.setBackground(new Color(255,255,255,255));
		btnRedBot.setBackground(new Color(255,255,255,255));
		
		/*
		btnRedHuman.setBackground(new Color(255,0,0,50));
		btnRedBot.setBackground(new Color(255,0,0,50));
		*/
		
		btnRedHuman.addActionListener(e -> {
			btnRedHuman.setBackground(new Color(255,0,0,255));
			RedHuman=true;
			btnRedBot.setEnabled(false);
		
		});
		btnRedBot.addActionListener(e -> {
			btnRedBot.setBackground(new Color(255,0,0,255));
			RedBot= true;
			btnRedHuman.setEnabled(false);

		});
		
		/*
		btnRedHuman.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btnRedHuman.setBackground(new Color(255,0,0,255));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btnRedHuman.setBackground(UIManager.getColor("control"));
		    }
		});
		
		btnRedBot.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btnRedBot.setBackground(new Color(255,0,0,255));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btnRedBot.setBackground(UIManager.getColor("control"));
		    }
		});
		
		*/
		
		btnRedHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnRedBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsRed.setLayout(new GridLayout());
		//panel3.setPreferredSize(new Dimension(200,50));
		panelButtonsRed.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsRed.add(btnRedHuman);
		
		tfRed.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsRed.add(tfRed);
		
		//panelButtonsRed.add( Box.createRigidArea( new Dimension( 10 , 0 ) )  );
		panelButtonsRed.add(btnRedBot);
		this.add(panelButtonsRed);
		//</Red>
		
		
		
		
		
		//<Blue>
		btnBlueHuman.setText("Mensch");
		btnBlueBot.setText("Bot");
		
		btnBlueHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnBlueBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnBlueHuman.setBackground(new Color(255,255,255,255));
		btnBlueBot.setBackground(new Color(255,255,255,255));
		/*
		btnBlueHuman.setBackground(new Color(0,0,255,50));
		btnBlueBot.setBackground(new Color(0,0,255,50));
		*/
		
		btnBlueHuman.addActionListener(e -> {
			btnBlueHuman.setBackground(new Color(0,0,255,200));
			BlueHuman = true;
			btnBlueBot.setEnabled(false);

		});
		btnBlueBot.addActionListener(e -> {
			btnBlueBot.setBackground(new Color(0,0,255,200));
			BlueBot= true;
			btnBlueHuman.setEnabled(false);

		});
		btnBlueHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBlueBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsBlue.setLayout(new GridLayout());
		panelButtonsBlue.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsBlue.add(btnBlueHuman);
		
		tfBlue.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsBlue.add(tfBlue);
		
		//panelButtonsBlue.add( Box.createRigidArea( new Dimension( 10 , 0 ) )  );
		panelButtonsBlue.add(btnBlueBot);
		this.add(panelButtonsBlue);
		//</Blue>
		
		
		
		

		//<Green>
		btnGreenHuman.setText("Mensch");
		btnGreenBot.setText("Bot");
		
		btnGreenHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnGreenBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnGreenHuman.setBackground(new Color(255,255,255,255));
		btnGreenBot.setBackground(new Color(255,255,255,255));
		/*
		btnGreenHuman.setBackground(new Color(0,255,0,50));
		btnGreenBot.setBackground(new Color(0,255,0,50));
		*/
		btnGreenHuman.addActionListener(e -> {
			btnGreenHuman.setBackground(new Color(0,255,0,200));
			GreenHuman = true;
			btnGreenBot.setEnabled(false);
		
		});
		btnGreenBot.addActionListener(e -> {
			btnGreenBot.setBackground(new Color(0,255,0,200));
			GreenBot= true;
			btnGreenHuman.setEnabled(false);
			
		});
		btnGreenHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnGreenBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsGreen.setLayout(new GridLayout());
		panelButtonsGreen.setBorder(new EmptyBorder(20,50,0,50));
		panelButtonsGreen.add(btnGreenHuman);
		
		tfGreen.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsGreen.add(tfGreen);
		
		//panelButtonsGreen.add( Box.createRigidArea( new Dimension( 10 , 0 ) )  );
		panelButtonsGreen.add(btnGreenBot);
		this.add(panelButtonsGreen);
		//</Green>
		
		
		
		
		
		//<Yellow>				
		btnYellowHuman.setText("Mensch");
		btnYellowBot.setText("Bot");
				
		btnYellowHuman.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		btnYellowBot.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		btnYellowHuman.setBackground(new Color(255,255,255,255));
		btnYellowBot.setBackground(new Color(255,255,255,255));
		/*
		btnYellowHuman.setBackground(new Color(255,255,0,50));
		btnYellowBot.setBackground(new Color(255,255,0,50));
		*/	
		btnYellowHuman.addActionListener(e -> {
			btnYellowHuman.setBackground(new Color(255,255,0,200));
			YellowHuman = true;
			btnYellowBot.setEnabled(false);
		});
		btnYellowBot.addActionListener(e -> {
			btnYellowBot.setBackground(new Color(255,255,0,200));
			YellowBot= true;
			btnYellowHuman.setEnabled(false);
		});
		btnYellowHuman.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnYellowBot.setBorder(new EmptyBorder(0, 0, 0, 0));

		panelButtonsYellow.setLayout(new GridLayout());
		panelButtonsYellow.setBorder(new EmptyBorder(20,50,20,50));
		panelButtonsYellow.add(btnYellowHuman);
		
		tfYellow.setHorizontalAlignment(JTextField.CENTER);
		panelButtonsYellow.add(tfYellow);
		
		//panelButtonsYellow.add( Box.createRigidArea( new Dimension( 10 , 0 ) )  );
		panelButtonsYellow.add(btnYellowBot);
		this.add(panelButtonsYellow);
		//</Yellow>
		
		
		
		buttonContinue.setBackground(new Color(255,255,255,255));
		buttonContinue.setText("Los!");
		buttonContinue.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		buttonContinue.setBorder(new EmptyBorder(0,0,0,0));
		
		
		
		buttonContinue.addActionListener(e -> {
			startGame();
		});
		buttonContinue.setPreferredSize(new Dimension(100,50));
		
		
		/*
		if (RedHuman || RedBot &&
			BlueHuman || BlueBot && 
			GreenHuman || GreenBot && 
			YellowHuman || YellowBot) {
			
			buttonContinue.setEnabled(true);
		} else {
			buttonContinue.setEnabled(false);
		}
		*/
		
		
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
		
		/*
		comboBoxRed.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBoxBlue.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBoxGreen.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBoxYellow.setFont(new Font("Lexend Deca", Font.BOLD, 15));

		buttonContinue.setFont(new Font("Lexend Deca", Font.BOLD, 15));

		labelWhatToDo.setText(" Bitte wähle hier 'Mensch', wenn du selbst spielen möchtest und ");
		labelWhatToDo2.setText(" wähle 'Bot' wenn du möchtest, dass der Computer spielen soll. ");
		labelWhatToDo3.setText(" Auf 'Los!' startet das Spiel.");

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

		this.add(labelWhatToDo, BorderLayout.CENTER);
		this.add(labelWhatToDo2, BorderLayout.CENTER);
		this.add(labelWhatToDo3, BorderLayout.CENTER);


		panel1.setLayout(new GridLayout());
		panel1.setBackground(new Color(255, 0, 0, 155));
		panel1.setBorder(new EmptyBorder(2, 5, 5, 5));

		comboBoxRed.setBorder(BorderFactory.createTitledBorder("Rot"));
		panel1.add(comboBoxRed);
		this.add(panel1);


		panel2.setLayout(new GridLayout());
		panel2.setBackground(new Color(30, 144, 255, 155));
		panel2.setBorder(new EmptyBorder(2, 5, 5, 5));

		comboBoxBlue.setBorder(BorderFactory.createTitledBorder("Blau"));
		panel2.add(comboBoxBlue);
		this.add(panel2);


		panel3.setLayout(new GridLayout());
		panel3.setBackground(new Color(0, 225, 0, 225));
		panel3.setBorder(new EmptyBorder(2, 5, 5, 5));

		comboBoxGreen.setBorder(BorderFactory.createTitledBorder("Grün"));
		panel3.add(comboBoxGreen);
		this.add(panel3);


		panel4.setLayout(new GridLayout());
		panel4.setBackground(new Color(255, 225, 0, 225));
		panel4.setBorder(new EmptyBorder(2, 5, 5, 5));

		comboBoxYellow.setBorder(BorderFactory.createTitledBorder("Gelb"));
		panel4.add(comboBoxYellow);
		this.add(panel4);


		panel5.setLayout(new GridLayout());
		buttonContinue.addActionListener(e -> {
			startGame();
		});
		panel5.add(buttonContinue);

		panel5.add(cbAdmin);

		panel5.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(panel5);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		*/
	}

	private void startGame() {

		boolean allHuman = true;

		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();

		if(RedHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.RED));
			allHuman = false;
		}

		if(BlueHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.BLUE));
			allHuman = false;
		}

		if(GreenHuman) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.GREEN));
			allHuman = false;
		}

		if(YellowHuman) {
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

	public void showGUI() {
		this.setVisible(true);
	}
}
