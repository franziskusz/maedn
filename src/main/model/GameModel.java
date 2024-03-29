package main.model;

import main.controller.BotThread;
import main.controller.AdminCommand;
import main.model.enums.BotAction;
import main.model.enums.GameState;
import main.model.enums.PlayerState;
import main.model.graph.Graph;
import main.model.player.Bot;
import main.model.player.Piece;
import main.model.player.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

public class GameModel extends Observable {

	private Random random = new Random();

	private Graph board;
	private GameState gameState = GameState.NOT;
	private Player playerTurn;
	private Integer indexOfRemovedPlayer = null;
	private int playerTurnDicedCount = 0;
	private int diced = 6;

	private ArrayList<Player> INITIAL_PLAYERS; // Für initalen Aufruf
	private ArrayList<Player> players; // geordnet nach Uhrzeigersinn und an Stelle 0 ist Beginner
	private ArrayList<Piece> pieces;

	private ArrayList<Integer> options = new ArrayList<>();


	/**
	 * Konstruktor
	 * Initialisiert player, pieces, board, playerTurn, Gamestate
	 * Falls erster Spieler Bot, wird dieser zum Würfeln gezwungen
	 *
	 * @param INITIAL_PLAYERS Initialordnung der Spieler
	 */
	public GameModel(ArrayList<Player> INITIAL_PLAYERS) {
		this.INITIAL_PLAYERS = INITIAL_PLAYERS;
		setPlayerOrderINITIAL();

		pieces = new ArrayList<Piece>();
		for(Player player : players) {
			for(int i = 0; i < 4; i++) {
				pieces.add(player.getPieces()[i]);
			}
		}

		board = new Graph(INITIAL_PLAYERS);

		playerTurn = players.get(0);
		changeGameState(GameState.DETERMINE_ORDER);
		isBot_DoAction(BotAction.DICE);
	}


	//
	// Aus Controller Aufrufbare Methoden
	//

	/**
	 * Wird durch Buttonclick auf Würfeln aufgerufen oder durch Bot oder durch AdminButton mit Würfelzahl
	 */
	public void diceRoll(int... setDiced) {
		if(setDiced.length == 0) {
			diced = random.nextInt(6) + 1;
		} else {
			diced = setDiced[0];
		}
		playerTurnDicedCount += 1;

		if(playerTurn.getPlayerState() == PlayerState.DICE_AGAIN) {
			playerTurn.setPlayerState(PlayerState.NORMAL);
		}

		playerTurn.setLastDiced(diced);

		// Wird gerade der Beginner ausgewürelt?
		if(gameState == GameState.DETERMINE_ORDER) {
			// Ist Spieler 4? -> (Alle haben gewürfelt)
			if(playerTurn == players.get(players.size() - 1)) {
				// Kann eindeutiger Beginner ermittelt werden?
				if(determineBeginner()) {
					setPlayerOrder();
					clearPlayerLastDice();
					changeGameState(GameState.IN_GAME);

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

		options = board.getOptions(playerTurn, diced, INITIAL_PLAYERS);
		if(!(playerTurn instanceof Bot)) {
			givePiecesOptionFlag();
		}

		// Gibt es eine oder mehrere Möglichkeiten, unter der der Spieler auswählen kann
		if(hasOption()) {
			playerTurn.setPlayerState(PlayerState.NORMAL);

			if((diced == 6) && (gameState != GameState.DETERMINE_ORDER)) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
			}

			isBot_DoAction(BotAction.PERFORM_OPTION);
		} else {
			// Der Spieler hat keine Möglichkeiten

			// Wenn der Spieler 6 gewürfelt hat, dann darf er nochmal und bekommt DICE_AGAIN
			// sonst, Wenn er DICE THREE TIMES hat und 3x gewürfelt oder NORMAL hat, dann nächster Spieler
			// sonst nochmal würfeln

			if(diced == 6) {
				playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
				isBot_DoAction(BotAction.DICE);
			} else if(((playerTurn.getPlayerState() == PlayerState.DICE_THREE_TIMES) && (playerTurnDicedCount >= 3))
					|| (playerTurn.getPlayerState() == PlayerState.NORMAL)) {
				nextPlayer();
			} else {
				isBot_DoAction(BotAction.DICE);
			}
		}

		updateGUI();
	}


	/**
	 * Wird durch Buttonclick auf Option Buttons aufgerufen oder durch Bot
	 * Wählt eine der Optionen aus, die aus dem Graph zur Verfügung gestellt wurden
	 *
	 * @param option 0-3 entspricht der Option und somit auch PieceID
	 */
	public void performOption(int option) {
		board.performOption(playerTurn, playerTurn.getPieces()[option].getPosition(), diced, INITIAL_PLAYERS);

		if(playerTurn.isGoalAchieved()) {
			indexOfRemovedPlayer = players.indexOf(playerTurn);
			players.remove(playerTurn);
			if(players.size() <= 1) {
				players.get(0).setGoalAchieved();
				changeGameState(GameState.END);
			}
		}

		options.clear();
		removePiecesOptionFlag();

		if(gameState != GameState.END) {
			if(playerTurn.getPlayerState() == PlayerState.DICE_AGAIN) {
				isBot_DoAction(BotAction.DICE);
			} else {
				nextPlayer();
			}
		}

		updateGUI();
	}


	/**
	 * @return ob playerTurn Möglichkeiten hat zu handeln
	 */
	public boolean hasOption() {
		return options.size() != 0;
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


	public ArrayList<Integer> getOptions() {
		return options;
	}


	public Random getRandom() {
		return random;
	}


	public ArrayList<Player> getINITIAL_PLAYERS() {
		return INITIAL_PLAYERS;
	}


	public int getPlayerTurnDicedCount() {
		return playerTurnDicedCount;
	}


	/**
	 * Führt den mitgelieferten Admin-Befehl aus oder gibt Fehlermeldung aus.
	 * Wird vom Controller bei Buttonclick auf "Admin-Button" aufgerufen
	 *
	 * @param view    Spiel JFrame
	 * @param command eingegebener Befehl
	 */
	public void performAdminCommand(JFrame view, String command) {
		if(!command.trim().equals("")) {
			command = command.toUpperCase().replaceAll("\\s+", " ");
			String[] args = command.trim().split(" ");
			switch(args[0]) {
				case "DICE":
					if(args.length == 2) {
						if(AdminCommand.checkDice(args[1])) {
							diceRoll(Integer.parseInt(args[1]));
						} else {
							JOptionPane.showMessageDialog(view, "You can't dice this number!");
						}
					} else {
						JOptionPane.showMessageDialog(view, "Too many or too less arguments!");
					}
					return;
				case "MOVE":
					if(args.length == 3) {
						if(gameState != GameState.DETERMINE_ORDER) {
							if(AdminCommand.checkPieceID(args[1]) && AdminCommand.checkVertex(args[2])) {
								if(board.adminMove(playerTurn, Integer.parseInt(args[1]), board.getVertices()
										.get(Integer.parseInt(args[2])), INITIAL_PLAYERS)) {
									if(playerTurn.isGoalAchieved()) {
										indexOfRemovedPlayer = players.indexOf(playerTurn);
										players.remove(playerTurn);
										if(players.size() <= 1) {
											players.get(0).setGoalAchieved();
											changeGameState(GameState.END);
										} else {
											nextPlayer();
										}
									}
								} else {
									JOptionPane.showMessageDialog(view, "You can't move this piece there!");
								}
								updateGUI();
							} else {
								JOptionPane.showMessageDialog(view, "There is no piece or part of the board like this!");
							}
						} else {
							JOptionPane.showMessageDialog(view, "This command does not work yet!");
						}
					} else {
						JOptionPane.showMessageDialog(view, "Too many or too less arguments!");
					}
					return;
				case "SKIP_DETERMINE_BEGINNER":
					if(gameState == GameState.DETERMINE_ORDER) {
						setPlayerOrderINITIAL();
						clearPlayerLastDice();
						changeGameState(GameState.IN_GAME);
						firstPlayer();
						updateGUI();
					} else {
						JOptionPane.showMessageDialog(view, "This command is not possible now!");
					}
					return;
				case "SHOW_ID":
					givePiecesOptionFlagALL();
					updateGUI();
					return;
				case "NEXT_PLAYER":
					if(gameState != GameState.DETERMINE_ORDER) {
						nextPlayer();
						updateGUI();
					} else {
						JOptionPane.showMessageDialog(view, "This command does not work yet!");
					}
					return;
			}
		}

		JOptionPane.showMessageDialog(view, "This command does not exist!");
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
	 * Füllt players mit Spielern im Uhrzeigersinn beginnend bei ROT (INITAL_PLAYERS)
	 */
	private void setPlayerOrderINITIAL() {
		players = new ArrayList<>(INITIAL_PLAYERS);
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

		isBot_DoAction(BotAction.DICE);
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
		// Falls durch Admin angezeigt muss entfernt werden
		removePiecesOptionFlag();

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
			if((indexOfRemovedPlayer != null) && (indexOfRemovedPlayer < players.size())) {
				playerTurn = players.get(indexOfRemovedPlayer);
				indexOfRemovedPlayer = null;
			} else {
				playerTurn = players.get(0);
			}
		}

		playerTurnDicedCount = 0;

		isBot_DoAction(BotAction.DICE);
	}


	/**
	 * Ändert Game Status
	 *
	 * @param newGameState neuer Game Status
	 */
	private void changeGameState(GameState newGameState) {
		gameState = newGameState;
	}


	/**
	 * Gibt allen Spielsteinen des playerTurn, die eine Spielbare Option sind das option Flag (für GUI pieceID anzeige)
	 */
	private void givePiecesOptionFlag() {
		for(Integer integer : getOptions()) {
			getPlayerTurn().getPieces()[integer].setOption(true);
		}
	}


	/**
	 * Gibt allen Spielsteinen des playerTurn das option Flag (für GUI pieceID anzeige)
	 */
	private void givePiecesOptionFlagALL() {
		for(Piece piece : getPlayerTurn().getPieces()) {
			piece.setOption(true);
		}
	}


	/**
	 * Entfernt bei allen Spielsteinen des playerTurn das option Flag (für GUI pieceID anzeige)
	 */
	private void removePiecesOptionFlag() {
		for(Piece piece : getPlayerTurn().getPieces()) {
			piece.setOption(false);
		}
	}


	/**
	 * Wenn playerTurn ist Bot, dann muss der Bot arbeiten
	 *
	 * @param botAction Enum BotAction - Was der Bot machen soll
	 */
	private void isBot_DoAction(BotAction botAction) {
		if(playerTurn instanceof Bot) {
			Thread thread = new Thread(new BotThread(this, botAction));
			thread.start();
		}
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
