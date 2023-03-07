package main.controller;

import main.gui.GameGUI;
import main.gui.RoundButton;
import main.model.enums.GameState;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.player.Bot;
import main.model.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer, ActionListener {

	private GameModel gameModel;
	private GameGUI gameGUI;


	/**
	 * Konstruktor
	 * Setzt Observer
	 * Setzt ButtonListener auf this
	 * Aktiviert und Deaktiviert Buttons initial
	 * setzt Hintergrundfarbe und Textfeld für ersten Spieler
	 * macht GameGUI sichtbar
	 *
	 * @param gameModel Model für Spiel
	 * @param gameGUI   Spiel JFrame
	 */
	public GameController(GameModel gameModel, GameGUI gameGUI) {
		this.gameModel = gameModel;
		this.gameGUI = gameGUI;

		// ControllerGUI soll Model beobachten
		gameModel.addObserver(this);

		// set Listener für Buttons
		gameGUI.getBtnDice().addActionListener(this);
		gameGUI.getBtnOption0().addActionListener(this);
		gameGUI.getBtnOption1().addActionListener(this);
		gameGUI.getBtnOption2().addActionListener(this);
		gameGUI.getBtnOption3().addActionListener(this);
		gameGUI.getBtnAdmin().addActionListener(this);
		gameGUI.getBtnPlayAgain().addActionListener(this);

		// set Listender für TfAdmin (Enter druecken)
		gameGUI.getTfAdmin().addActionListener(this);

		// Buttons disable
		gameGUI.getBtnOption0().setEnabled(false);
		gameGUI.getBtnOption1().setEnabled(false);
		gameGUI.getBtnOption2().setEnabled(false);
		gameGUI.getBtnOption3().setEnabled(false);

		// mach Fenster sichtbar
		gameGUI.setVisible(true);

		initGUI();
	}


	/**
	 * Setze GUI für ersten Spieler
	 */
	private void initGUI() {
		gameGUI.setBackgroundColor(gameModel.getPlayerTurn().getPlayerColor());

		if(gameModel.getPlayerTurn() instanceof Bot) {
			gameGUI.getBtnDice().setEnabled(false);
			gameGUI.getBtnAdmin().setEnabled(false);
			gameGUI.getTfAdmin().setEnabled(false);
			gameGUI.getTfInstruction().setText("Bot's turn");
		} else {
			gameGUI.getBtnDice().setEnabled(true);
			gameGUI.getBtnAdmin().setEnabled(true);
			gameGUI.getTfAdmin().setEnabled(true);
			gameGUI.getTfInstruction().setText("Please roll the dice: " + gameModel.getPlayerTurn().getPlayerColor().toString());
		}
	}


	/**
	 * Updated GUI
	 * Wird aufgerufen, wenn im Model Änderungen gemacht wurden
	 *
	 * @param o   Observer
	 * @param arg arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o == gameModel) {
			if(gameModel.getGameState() == GameState.END) {
				ArrayList<Player> winner = new ArrayList<>(gameModel.getINITIAL_PLAYERS());
				winner.sort(Player.sortByPlace);

				gameGUI.getTfInstruction().setText(winner.get(0).getPlayerColor().toString() + " wins");

				gameGUI.showEndScreen(new PlayerColor[]{winner.get(0).getPlayerColor(), winner.get(1).getPlayerColor(),
						winner.get(2).getPlayerColor(), winner.get(3).getPlayerColor()});

			} else {
				gameGUI.setBackgroundColor(gameModel.getPlayerTurn().getPlayerColor());
				RoundButton.setColor(gameModel.getPlayerTurn().getPlayerColor());
				if(gameModel.getPlayerTurn() instanceof Bot) {
					gameGUI.getBtnDice().setEnabled(false);
					gameGUI.getBtnOption0().setEnabled(false);
					gameGUI.getBtnOption1().setEnabled(false);
					gameGUI.getBtnOption2().setEnabled(false);
					gameGUI.getBtnOption3().setEnabled(false);
					gameGUI.getBtnAdmin().setEnabled(false);
					gameGUI.getTfAdmin().setEnabled(false);

					gameGUI.getTfInstruction().setText("Bot's turn");
				} else {
					if(gameModel.hasOption()) {
						gameGUI.getBtnDice().setEnabled(false);
						gameGUI.getBtnAdmin().setEnabled(false);
						gameGUI.getTfAdmin().setEnabled(false);
						if(gameModel.getOptions().contains(0)) {
							gameGUI.getBtnOption0().setEnabled(true);
						}
						if(gameModel.getOptions().contains(1)) {
							gameGUI.getBtnOption1().setEnabled(true);
						}
						if(gameModel.getOptions().contains(2)) {
							gameGUI.getBtnOption2().setEnabled(true);
						}
						if(gameModel.getOptions().contains(3)) {
							gameGUI.getBtnOption3().setEnabled(true);
						}

						gameGUI.getTfInstruction().setText("Please choose an option: " + gameModel.getPlayerTurn().getPlayerColor().toString());
					} else {
						gameGUI.getBtnDice().setEnabled(true);
						gameGUI.getBtnAdmin().setEnabled(true);
						gameGUI.getTfAdmin().setEnabled(true);
						gameGUI.getBtnOption0().setEnabled(false);
						gameGUI.getBtnOption1().setEnabled(false);
						gameGUI.getBtnOption2().setEnabled(false);
						gameGUI.getBtnOption3().setEnabled(false);

						if(gameModel.getPlayerTurnDicedCount() >= 1) {
							gameGUI.getTfInstruction().setText("Please roll the dice again: " + gameModel.getPlayerTurn().getPlayerColor().toString());
						} else {
							gameGUI.getTfInstruction().setText("Please roll the dice: " + gameModel.getPlayerTurn().getPlayerColor().toString());
						}
					}
				}
			}

			gameGUI.setDicedImage(gameModel.getDiced());
			gameGUI.getBoardLayeredPane().setPieces(gameModel.getPieces());
			gameGUI.getBoardLayeredPane().repaint();
		}
	}


	/**
	 * ActionListener
	 * Wird beim Buttonclick aufgerufen
	 * switcht die Buttons und führt Methoden im GameModel aus
	 *
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case GameGUI.WUERFELN:
				gameModel.diceRoll();
				if(!(gameModel.getPlayerTurn() instanceof Bot)) {
					gameGUI.playDiceSound();
				}
				break;
			case GameGUI.OPTION_0:
				gameModel.performOption(0);
				break;
			case GameGUI.OPTION_1:
				gameModel.performOption(1);
				break;
			case GameGUI.OPTION_2:
				gameModel.performOption(2);
				break;
			case GameGUI.OPTION_3:
				gameModel.performOption(3);
				break;
			case GameGUI.ADMIN:
				gameModel.performAdminCommand(gameGUI, gameGUI.getTfAdmin().getText());
				gameGUI.getTfAdmin().setText("");
				break;
			case GameGUI.AGAIN:
				gameGUI.stopGame();
				break;

			default:
				System.out.println("Unknown Action...");
				break;
		}
	}
}
