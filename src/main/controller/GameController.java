package main.controller;

import main.gui.GameGUI;
import main.gui.RoundButton;
import main.model.enums.GameState;
import main.model.GameModel;
import main.model.player.Bot;
import main.model.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		gameGUI.getBtnWuerfel().addActionListener(this);
		gameGUI.getBtnOption0().addActionListener(this);
		gameGUI.getBtnOption1().addActionListener(this);
		gameGUI.getBtnOption2().addActionListener(this);
		gameGUI.getBtnOption3().addActionListener(this);
		gameGUI.getBtnAdmin().addActionListener(this);

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
			gameGUI.getBtnWuerfel().setEnabled(false);
			gameGUI.getBtnAdmin().setEnabled(false);
			gameGUI.getText().setText("Bot ist dran");
		} else {
			gameGUI.getBtnWuerfel().setEnabled(true);
			gameGUI.getBtnAdmin().setEnabled(true);
			gameGUI.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
		}

		System.out.println("Init GUI");
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
				StringBuilder stringBuilder = new StringBuilder();
				for(Player player : gameModel.getINITIAL_PLAYERS()) {
					stringBuilder.append(player.getPlayerColor().toString() + " " + player.getPlace() + ". ");
				}
				// TODO evt anders azeigen (Im Moment "BLAU 1. ROT 3. GREEN 4. YELLOW 2.")
				gameGUI.getText().setText(stringBuilder.toString());

				//TODO evt Button sichtbar machen, der Wieder das Initiale JFrame aufruft für neue Runde

			} else {
				gameGUI.setBackgroundColor(gameModel.getPlayerTurn().getPlayerColor());
				RoundButton.hierhastewas(gameModel.getPlayerTurn().getPlayerColor());
				if(gameModel.getPlayerTurn() instanceof Bot) {
					gameGUI.getBtnWuerfel().setEnabled(false);
					gameGUI.getBtnOption0().setEnabled(false);
					gameGUI.getBtnOption1().setEnabled(false);
					gameGUI.getBtnOption2().setEnabled(false);
					gameGUI.getBtnOption3().setEnabled(false);
					gameGUI.getBtnAdmin().setEnabled(false);

					gameGUI.getText().setText("Bot ist dran");
				} else {
					if(gameModel.hasOption()) {
						gameGUI.getBtnWuerfel().setEnabled(false);
						gameGUI.getBtnAdmin().setEnabled(false);
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

						gameGUI.getText().setText("Wähle Option");
					} else {
						gameGUI.getBtnWuerfel().setEnabled(true);
						gameGUI.getBtnAdmin().setEnabled(true);
						gameGUI.getBtnOption0().setEnabled(false);
						gameGUI.getBtnOption1().setEnabled(false);
						gameGUI.getBtnOption2().setEnabled(false);
						gameGUI.getBtnOption3().setEnabled(false);

						if(gameModel.getPlayerTurnDicedCount() >= 1) {
							gameGUI.getText().setText("Bitte nochmal Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
						} else {
							gameGUI.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
						}
					}
				}
			}
			gameGUI.setDicedImage(gameModel.getDiced());

			gameGUI.getBoardLayeredPane().setPieces(gameModel.getPieces());
			gameGUI.getBoardLayeredPane().repaint();

			//TODO entfernen
			System.out.println("Updated GUI");
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
				gameModel.perfromAdminCommand(gameGUI, gameGUI.getTfAdmin().getText());
				gameGUI.getTfAdmin().setText("");
				break;

			default:
				System.out.println("Unknown Action...");
				break;
		}
	}
}
