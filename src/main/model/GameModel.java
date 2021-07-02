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

	private Graph board;
	private GameState gameState = GameState.NOT;
	private Player playerTurn;
	private int playerTurnDicedCount = 0;
	private int diced = 6;

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

		board = new Graph(players);

		playerTurn = players.get(0);
		changeGameState(GameState.DETERMINE_ORDER);
	}


	//
	// Aus Controller Aufrufbare Methoden
	//

	/**
	 * Wird durch Buttonclick auf Würfeln aufgerufen
	 */
	public void diceRoll() {
		diced = random.nextInt(6)+1;
		playerTurnDicedCount += 1;

		if(playerTurn.getPlayerState() == PlayerState.DICE_AGAIN) {
			playerTurn.setPlayerState(PlayerState.NORMAL);
		}

		playerTurn.setLastDiced(diced);

		//TODO entfernen
		System.out.println(playerTurn.getPlayerColor().toString() + " hat " + diced + " gewürfelt");

		// Wird gerade der Beginner ausgewürelt?
		if(gameState == GameState.DETERMINE_ORDER) {
			// Ist Spieler 4? -> (Alle haben gewürfelt)
			if(playerTurn == players.get(players.size() - 1)) {
				// Kann eindeutiger Beginner ermittelt werden?
				if(determineBeginner()) {
					setPlayerOrder();
					clearPlayerLastDice();
					changeGameState(GameState.IN_GAME);

					//TODO entfernen
					System.out.println(players.get(0).getPlayerColor().toString() + " beginnt!\n");

					// Spiel beginnt mit Spieler, der die höchste Zahl gewürfelt hat
					firstPlayer();
					updateGUI();
					return;
				}
			}
			// Nächster Spieler mit Würfeln dran
			nextPlayer();
			updateGUI();
			return;
		}


		// @Franziskus
		//TODO über den Spielbrett-Graph die Möglichkeiten für den Spieler, mit Würfelzahl ermitteln und irgendwie im Model
		// speichern, damit beim GUI Update die OPTION Buttons aktivieert werden können
		// board.getOptions(playerTurn, diced);
		// !! Option: "Keine Möglichkeiten" beachten z.B. wenn Figuren vor dem Haus und keine passende Zahl



		// Gibt es eine oder mehrere Möglichkeiten, unter der der Spieler auswählen kann
		if(hasOption()) {
			playerTurn.setPlayerState(PlayerState.NORMAL);

			if((diced == 6) && (gameState != GameState.DETERMINE_ORDER)) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
				//TODO Hier könnte auch irgendeine Variable gesetzt werden, die in der GUI geprüft und dann "Nochmal würfeln" ausgibt
			}
			// TODO Wahrscheinlich falsch an dieser Stelle! (Sollte erst in performOption aufgerufen werden, da Möglichkeit zum auswählen)
			// else {
			//	nextPlayer();
			// }
		} else {
			// Der Spieler hat keine Möglichkeiten

			// Hat der Spieler eine 6 gewürfelt? -> nochmal würfeln
			if((diced == 6) && (gameState != GameState.DETERMINE_ORDER)) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
				//TODO Hier könnte auch irgendeine Variable gesetzt werden, die in der GUI geprüft und dann "Nochmal würfeln" ausgibt
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


	/**
	 * Wird durch Buttonclick auf Option Buttons aufgerufen
	 * Wählt eine der Optionen aus, die aus dem Graph zur Verfügung gestellt wurden
	 *
	 * @param option 0-3 entspricht der Option und somit auch PieceID
	 */
	public void performOption(int option) {

		boolean isSuperSpecialCase = false;

		// @Franziskus
		// TODO isSuperSpecialCase = board.perfromODERSO(int option);
		// - in performOption() eine Methode im Graph aufrufen, die den gewünschten Spielstein bewegt
		//    und daraufhin bestimmte Sachen überprüft
		//    - Wurde Geschlagen?
		//        - Muss geschlagener Spieler THREE TIMES bekommen (gar keine Spielfiguren mehr
		//            aufm Spielfeld oder restlos aufgerückt)
		//    - Ist der Spieler restlos aufgerückt und hast sonst keine Spielsteine auf dem Feld?
		//        - Dann bekommt er THREE TIMES (return true; (THREE TIMES nicht setzen!) sonst immer return false;)
		//    - Ob eigene Spielfigur übersprungen wurde muss NICHT überprüft werden, da es durch "keine Möglichkeit" auisgeschlossen

		// @Franziskus
		// TODO board.hatGewonnenODERSO(Player playerTrun)
		// - in performOption() eine Methode im Graph aufrufen, die überprüft, ob der
		//    mitgegebene Spieler (playerTrun) gewonnen hat
		//    - Falls ja -> GameState END setzen
		//
		// if(board.hatGewonnen(playerTurn)) {
		//   changeGameState(GameState.END);
		// }


		//TODO Auswahloptionen zurücksetzen, sodass auf GUI OPTION bUTTONS deaktiviert werden


		if(gameState != GameState.END) {
			if(playerTurn.getPlayerState() == PlayerState.NORMAL) {
				nextPlayer();
			}

			// TODO Wenn Spieler durch diesen Zug THREE TIMES bekommt und keine 6 gewürfelt hat
			//  darf er erst nächste Runde wieder würfeln.
			//  Wenn er allerdings durch diesen Zug THREE TIMES bekommt und eine 6 gewürfelt hat,
			//  darf er direkt weiter 3x würfeln!
			//  !!!!Wird durch folgendes gelöst (wahrscheinlich)!!!

			if(isSuperSpecialCase) {
				if(playerTurn.getPlayerState() == PlayerState.DICE_AGAIN) {
					playerTurn.setPlayerState(PlayerState.DICE_THREE_TIMES);
				} else {
					playerTurn.setPlayerState(PlayerState.DICE_THREE_TIMES);
					nextPlayer();
				}
			}
		}

		updateGUI();
	}

	/**
	 * @return ob playerTurn Möglichkeiten hat zu handeln
	 */
	public boolean hasOption() {
		//TODO Wenn Mögöichkeiten vatiable eingebaut schauen, ob Möglichkeiten vorhanden sind
		// NIHCT NOCHMAL board.getOptions(playerTurn, 0);
		// auf lokale Möglichkeiten Variable zugreifen
		return false;
	}

	public int getDiced() {
		return diced;
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
	 * alle Spieler mit zu kleinen Augenzahlen werden aus players gelöscht (in setPlayerOder() wieder hinzugefügt)
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
	 * Füllt players mit Spielern im Uhrzeigersinn
	 * Spieler mit der höchsten gewüfelten Zahl steht noch in players,
	 * der Rest wird aus INITAL_PLAYERS geholt
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
	 * Setzt bei allen Spielern Letzter Würfel 0
	 */
	private void clearPlayerLastDice() {
		for(Player player : players) {
			player.setLastDiced(0);
		}
	}

	/**
	 * Setzt playerTurn auf den Beginner
	 */
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

	/**
	 * Ändert Game Status
	 * @param newGameState neuer Game Status
	 */
	private void changeGameState(GameState newGameState) {
		gameState = newGameState;
	}



	//
	// Methode zum updaten des GUI
	//

	/**
	 * benachrichtigt Controller, dass Änderungen auf GUI aktualisiert werden müssen
	 */
	private void updateGUI() {
		setChanged();
		notifyObservers();
	}
}
