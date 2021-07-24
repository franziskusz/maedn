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
import java.util.ArrayList;

public class SetupGUI extends JFrame {

	private final String[] playerOptions = {"Mensch", "Bot"};

	private JTextField labelWhatToDo = new JTextField();
	private JTextField labelWhatToDo2 = new JTextField();
	private JTextField labelWhatToDo3 = new JTextField();

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();

	private JComboBox comboBoxRed = new JComboBox(playerOptions);
	private JComboBox comboBoxBlue = new JComboBox(playerOptions);
	private JComboBox comboBoxGreen = new JComboBox(playerOptions);
	private JComboBox comboBoxYellow = new JComboBox(playerOptions);


	private RoundButton2 buttonContinue = new RoundButton2("Los !", 20, false);
	;

	private JCheckBox cbAdmin = new JCheckBox("Admin", false);


	public SetupGUI() {
		this.setTitle("Mensch ärgere dich nicht!");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(300, 300);
		this.setSize(500, 500);

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
	}

	private void startGame() {

		boolean allHuman = true;

		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();

		if(comboBoxRed.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.RED));
			allHuman = false;
		}

		if(comboBoxBlue.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.BLUE));
			allHuman = false;
		}

		if(comboBoxGreen.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.GREEN));
			allHuman = false;
		}

		if(comboBoxYellow.getSelectedIndex() == 0) {
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
