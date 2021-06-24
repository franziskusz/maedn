package main.model;

import main.model.enums.GameState;
import main.model.enums.PlayerState;
import main.model.graph.Graph;
import main.model.player.Piece;
import main.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

public class GameModel extends Observable {

	private Random random = new Random();

	private Graph board = new Graph();
	private GameState gameState = GameState.NOT;
	private Player playerTurn;
	private int playerTurnDicedCount = 0;

	private ArrayList<Player> INITIAL_PLAYERS; // Für initalen Aufruf
	private ArrayList<Player> players; // geordnet nach Uhrzeigersinn und an Stelle 0 ist Beginner
	private ArrayList<Piece> pieces;



	public GameModel(ArrayList<Player> INITIAL_PLAYERS) {
		this.INITIAL_PLAYERS = INITIAL_PLAYERS;
		players = new ArrayList<>(INITIAL_PLAYERS);

		pieces = new ArrayList<Piece>();
		for(Player player : players) {
			for(int i = 0; i < 4; i++) {
				pieces.add(player.getPieces()[i]);
			}
		}

		playerTurn = players.get(0);
		changeGameState(GameState.DETERMINE_ORDER);
	}


	//
	// Aus Controller Aufrufbare Methoden
	//


	public void diceRoll() {
		int diced = random.nextInt(6)+1;
		playerTurnDicedCount += 1;

		if(playerTurn.getPlayerState() == PlayerState.DICE_AGAIN) {
			playerTurn.setPlayerState(PlayerState.NORMAL);
		}

		playerTurn.setLastDiced(diced);

		//TODO entfernen
		System.out.println(playerTurn.getPlayerColor().toString() + " hat " + diced + " gewürfelt");

		if(gameState == GameState.DETERMINE_ORDER) {
			if(playerTurn == players.get(players.size() - 1)) { // Ermittlung Beginner, jeder hat 1x gewürfelt
				if(determineBeginner()) {
					setPlayerOrder();
					clearPlayerLastDice();
					changeGameState(GameState.IN_GAME);

					//TODO entfernen
					System.out.println(players.get(0).getPlayerColor().toString() + " beginnt!\n");

					// Spiel beginnt mit Beginner
					firstPlayer();
					updateGUI();
					return;
				}
			}
			nextPlayer();
			updateGUI();
			return;
		}

		//TODO über den Spielbrett-Graph die Möglichkeiten für den Spieler, mit Würfelzahl ermitteln und irgendwie im Model
		// speichern, damit beim GUI Update die OPTION Buttons aktivieert werden können
		// !! Wenn es keine Möglichkeiten gibt (Figuren vor dem Haus und keine passende Zahl oder PlayerState THREE TIMES)
		// dann muss irgendwie geschaut werden, ob der



		// TODO !!! Wenn man mit ner 6 im Ziel mit Figuren restlos aurück, dann darf man theoretisch wieder 3x

		if(board.getOptions(playerTurn, diced)) { // würde True zurückgeben, wenn Möglich. vorhanden und diese in der Klasse gesetzt werden
			playerTurn.setPlayerState(PlayerState.NORMAL);

			//TODO evt braucht man DICE_AGAIN gar nicht und es geht auch nur mit dice == 6, dann nicht nextPlayer();
			if((diced == 6) && (gameState != GameState.DETERMINE_ORDER)) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
				// Hier könnte auch irgendeine Variable gesetzt werden, die in der GUI geprüft und dann "Nochmal würfeln" ausgibt
			} else {
				nextPlayer();
			}
		} else {
			if((diced == 6) && (gameState != GameState.DETERMINE_ORDER)) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
				// Hier könnte auch irgendeine Variable gesetzt werden, die in der GUI geprüft und dann "Nochmal würfeln" ausgibt
			}

			// 3x hintereinander ohne Erfolg gewürfelt
			// oder Normaler Status aber keine Möglichkeiten (z.B. alle vor dem Ziel, keiner mehr im Haus)
			if(((playerTurn.getPlayerState() == PlayerState.DICE_THREE_TIMES) && (playerTurnDicedCount >= 3))
					|| (playerTurn.getPlayerState() == PlayerState.NORMAL)){
				nextPlayer();
			}
		}

		updateGUI();
	}

	public void performOption(int option) {
		//TODO
		// Getroffene Auswahl bearbeiten
		// Beim Schlagen eines anderen Spielers überprüfen, ob dieser Spiler dann nur noch Figuren im Haus
		//   oder restlos aufgerück im Ziel hat. Wenn ja, muss dem anderen Spieler PlayerState THREE TIMES gegeben
		//   werden.
		// Auch der Spieler der dran ist muss gegf. THREE TIMES Status bekommen, wenn er alle Figuren restlos
		//   aufgerückt im Ziel hat.
		// Überorüfung, ob gewonnen

		//TODO Auswahloptionen zurücksetzen, sodass auf GUI OPTION bUTTONS deaktiviert werden
		//
		//


		// Nächster Spieler wenn PlayerState == NORMAL
		if((playerTurn.getPlayerState() == PlayerState.NORMAL)) {
			nextPlayer();
		}

		updateGUI();
	}

	public boolean hasOption() {
		//TODO Wenn Options vatiable exisiert schauen, ob Optionen vorhanden sind
		return false;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	public GameState getGameState() {
		return gameState;
	}

	//
	// Methoden fürs Auswüfeln des Beginners
	//

	/**
	 * Überprüft, wie viele und welche Spieler die höchste Punktzahl gewürfelt haben.
	 * Wird nur bei deim Beginner auswürfeln aufgerufen.
	 * alle Spieler mit zu kleinen Augenzahlen werden aus data.players gelöscht (setPlayerOder() wieder hinzugefügt)
	 *
	 * @return ob es einen Spieler gibt, der die höchste Zahl gewüfelt hat
	 */
	private boolean determineBeginner() {
		int hightDice = 0;
		for(Player player : players) {
			if(hightDice < player.getLastDiced()) {
				hightDice = player.getLastDiced();
			}
		}

		Iterator<Player> iterator = players.iterator();
		while(iterator.hasNext()) {
			Player player = iterator.next();
			if(player.getLastDiced() < hightDice) {
				iterator.remove();
			}
		}

		return players.size() <= 1;
	}

	/**
	 * Füllt data.player mit Spielern im Uhrzeigersinn
	 * Spieler mit der höchsten gewüfelten Zahl steht noch in data.player,
	 * der Rest wird aus data.INITALPLAYER geholt
	 */
	private void setPlayerOrder() {
		switch(INITIAL_PLAYERS.indexOf(players.get(0))) {
			case 0:
				players.add(INITIAL_PLAYERS.get(1));
				players.add(INITIAL_PLAYERS.get(2));
				players.add(INITIAL_PLAYERS.get(3));
				break;
			case 1:
				players.add(INITIAL_PLAYERS.get(2));
				players.add(INITIAL_PLAYERS.get(3));
				players.add(INITIAL_PLAYERS.get(0));
				break;
			case 2:
				players.add(INITIAL_PLAYERS.get(3));
				players.add(INITIAL_PLAYERS.get(0));
				players.add(INITIAL_PLAYERS.get(1));
				break;
			case 3:
				players.add(INITIAL_PLAYERS.get(0));
				players.add(INITIAL_PLAYERS.get(1));
				players.add(INITIAL_PLAYERS.get(2));
				break;
		}
	}

	/**
	 * Setzt alle Letzte Würfel 0
	 */
	private void clearPlayerLastDice() {
		for(Player player : players) {
			player.setLastDiced(0);
		}
	}

	private void firstPlayer() {
		playerTurn = players.get(0);
		playerTurnDicedCount = 0;
	}


	//
	// Methoden während des Spiels
	//

	/**
	 * Setzt Spieler der dran ist im Urzeigersinn weiter
	 * Setzt den letzen Wüfel beim Spieler, der jetzt nicht mehr dran ist auf 0
	 * Setzt playerTurnDicedCount 0
	 * ruft die GUI Würfeln für neuen Spieler auf
	 */
	private void nextPlayer() {
		if(gameState == GameState.IN_GAME) {
			playerTurn.setLastDiced(0);
		}

		int indexOfPlayerTurn;

		if(players.contains(playerTurn)) {
			indexOfPlayerTurn = players.indexOf(playerTurn);

			try {
				playerTurn = players.get(indexOfPlayerTurn + 1);
			} catch(Exception ex) {
				playerTurn = players.get(0);
			}
		} else {
			playerTurn = players.get(0);
		}


		playerTurnDicedCount = 0;
	}

	private void changeGameState(GameState newGameState) {
		gameState = newGameState;
	}



	//
	// Methode zum updaten des GUI
	//

	private void updateGUI() {
		setChanged();
		notifyObservers();
	}
}
