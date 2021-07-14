package main.gui;

import main.controller.GameController;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.player.Bot;
import main.model.player.Gamer;
import main.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SetupGUI extends JFrame {

	private final String[] playerOptions = {"Mensch", "Bot"};

	private JLabel labelWhatToDo = new JLabel();

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();

	private JComboBox comboBox1 = new JComboBox(playerOptions);
	private JComboBox comboBox2 = new JComboBox(playerOptions);
	private JComboBox comboBox3 = new JComboBox(playerOptions);
	private JComboBox comboBox4 = new JComboBox(playerOptions);

	private JLabel jLabel1 = new JLabel("Rot");
	private JLabel jLabel2 = new JLabel("Blau");
	private JLabel jLabel3 = new JLabel("Grün");
	private JLabel jLabel4 = new JLabel("Gelb");

	private JCheckBox cbAdmin = new JCheckBox("Admin", false);

	private JButton buttonContinue = new JButton("Los!");

	public SetupGUI() {
		
		
		this.setTitle("Mensch ärgere dich nicht!");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(300, 300);
		this.setSize(500, 500);

		labelWhatToDo.setText("Hier steht eine Erklärung von dem ganzen Scheiß!");
		this.add(labelWhatToDo);

		panel1.setLayout(new FlowLayout());
		panel1.add(comboBox1);
		panel1.add(jLabel1);
		this.add(panel1);

		panel2.setLayout(new FlowLayout());
		panel2.add(comboBox2);
		panel2.add(jLabel2);
		this.add(panel2);

		panel3.setLayout(new FlowLayout());
		panel3.add(comboBox3);
		panel3.add(jLabel3);
		this.add(panel3);

		panel4.setLayout(new FlowLayout());
		panel4.add(comboBox4);
		panel4.add(jLabel4);
		this.add(panel4);

		panel5.setLayout(new FlowLayout());
		buttonContinue.addActionListener(e -> startGame());
		panel5.add(buttonContinue);
		panel5.add(cbAdmin);
		this.add(panel5);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	private void startGame() {
		
		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();
		if(comboBox1.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.RED));
		}

		if(comboBox2.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.BLUE));
		}

		if(comboBox3.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.GREEN));
		}

		if(comboBox4.getSelectedIndex() == 0) {
			INITIAL_PLAYERS.add(new Gamer(PlayerColor.YELLOW));
		} else {
			INITIAL_PLAYERS.add(new Bot(PlayerColor.YELLOW));
		}

		GameModel gameModel = new GameModel(INITIAL_PLAYERS);
		GameGUI gameGui = new GameGUI(gameModel.getPieces(), cbAdmin.isSelected());
		GameController gameController = new GameController(gameModel, gameGui);

		this.setVisible(false);
	}
}
