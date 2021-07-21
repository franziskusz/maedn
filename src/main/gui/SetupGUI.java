package main.gui;

import main.controller.GameController;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.player.Bot;
import main.model.player.Gamer;
import main.model.player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
	
	private JTextField jtf1 = new JTextField("Rot");
	private JTextField jtf2 = new JTextField("Blau");
	private JTextField jtf3 = new JTextField("Grün");
	private JTextField jtf4 = new JTextField("Gelb");
	private JTextField jtf5 = new JTextField("Admin");

	private JComboBox comboBox1 = new JComboBox(playerOptions);
	private JComboBox comboBox2 = new JComboBox(playerOptions);
	private JComboBox comboBox3 = new JComboBox(playerOptions);
	private JComboBox comboBox4 = new JComboBox(playerOptions);

	/*
	private JLabel jLabel1 = new JLabel("Rot");
	private JLabel jLabel2 = new JLabel("Blau");
	private JLabel jLabel3 = new JLabel("Grün");
	private JLabel jLabel4 = new JLabel("Gelb");
	*/
	
	
	
	

	private JCheckBox cbAdmin = new JCheckBox("Admin", false);

	//ToggleSwitch ts = new ToggleSwitch();
	
	private JButton buttonContinue = new JButton("Los !");
	

	public SetupGUI() {
	
		buttonContinue.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		jtf1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		jtf2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		jtf3.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		jtf4.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		comboBox1.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBox2.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBox3.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		comboBox4.setFont(new Font("Lexend Deca", Font.BOLD, 15));
		
		this.setTitle("Mensch ärgere dich nicht!");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(300,300);
		this.setSize(500, 500);

		//labelWhatToDo.setLayout(new GridLayout());
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
		
/*
		panel1.setLayout(new GridLayout());
		panel1.add(jtf1);
		jtf1.setHorizontalAlignment(JTextField.CENTER);
		jtf1.setEditable(false);
		jtf1.setBackground(new Color (255,0,0,155));
		jtf1.setBorder(BorderFactory.createLoweredBevelBorder());
		comboBox1.setBorder(BorderFactory.createLoweredBevelBorder());
		panel1.add(comboBox1);
		panel1.setBorder(new EmptyBorder(5,5,5,5));
		this.add(panel1);

		panel2.setLayout(new GridLayout());
		panel2.add(jtf2);
		jtf2.setHorizontalAlignment(JTextField.CENTER);
		jtf2.setEditable(false);
		jtf2.setBackground(new Color (30,144,255,155));
		jtf2.setBorder(BorderFactory.createLoweredBevelBorder());
		comboBox2.setBorder(BorderFactory.createLoweredBevelBorder());
		panel2.add(comboBox2);
		panel2.setBorder(new EmptyBorder(5,5,5,5));
		this.add(panel2);

		panel3.setLayout(new GridLayout());
		panel3.add(jtf3);
		jtf3.setHorizontalAlignment(JTextField.CENTER);
		jtf3.setEditable(false);
		jtf3.setBackground(new Color (0,255,0,225));
		jtf3.setBorder(BorderFactory.createLoweredBevelBorder());
		comboBox3.setBorder(BorderFactory.createLoweredBevelBorder());
		panel3.add(comboBox3);
		panel3.setBorder(new EmptyBorder(5,5,5,5));
		this.add(panel3);

		panel4.setLayout(new GridLayout());
		panel4.add(jtf4);
		jtf4.setHorizontalAlignment(JTextField.CENTER);
		jtf4.setEditable(false);
		jtf4.setBackground(new Color (255,255,0,225));
		jtf4.setBorder(BorderFactory.createLoweredBevelBorder());
		comboBox4.setBorder(BorderFactory.createLoweredBevelBorder());
		panel4.add(comboBox4);
		panel4.setBorder(new EmptyBorder(5,5,5,5));
		this.add(panel4);
*/
		
	/*
		ts.setPreferredSize(new Dimension(100,30));
		ts.setLayout(new BorderLayout());
		ts.setFont(new Font("Lexend Deca", Font.BOLD, 15));
	*/	
		
		
		panel1.setLayout(new GridLayout());
		panel1.setBackground(new Color(255,0,0,155));
		panel1.add(comboBox1);
		comboBox1.setBorder(BorderFactory.createTitledBorder("Rot"));
		//comboBox1.setBackground(new Color(255,0,0,155));
		panel1.setBorder(new EmptyBorder(2,5,5,5));
		this.add(panel1);
		
		panel2.setLayout(new GridLayout());
		panel2.setBackground(new Color (30,144,255,155));
		panel2.add(comboBox2);
		comboBox2.setBorder(BorderFactory.createTitledBorder("Blau"));
		//comboBox2.setBackground(new Color(20,44,255,155));
		panel2.setBorder(new EmptyBorder(2,5,5,5));
		this.add(panel2);
		
		panel3.setLayout(new GridLayout());
		panel3.setBackground(new Color (0,225,0,225));
		panel3.add(comboBox3);
		comboBox3.setBorder(BorderFactory.createTitledBorder("Grün"));
		//comboBox3.setBackground(new Color(0,125,0,225));
		panel3.setBorder(new EmptyBorder(2,5,5,5));
		this.add(panel3);
		
		panel4.setLayout(new GridLayout());
		panel4.setBackground(new Color (255,225,0,225));
		panel4.add(comboBox4);
		comboBox4.setBorder(BorderFactory.createTitledBorder("Gelb"));
		//comboBox4.setBackground(new Color(125,125,0,225));
		panel4.setBorder(new EmptyBorder(2,5,5,5));
		this.add(panel4);
		

		panel5.setLayout(new GridLayout());
		buttonContinue.addActionListener(e -> startGame());
		panel5.add(buttonContinue);
		jtf5.setBackground(getForeground());
		jtf5.setBorder(new EmptyBorder(5,5,5,5));
		jtf5.setEditable(false);
		panel5.add(cbAdmin);
		//panel5.add(jtf5);
		panel5.setBorder(new EmptyBorder(5,5,5,5));
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
