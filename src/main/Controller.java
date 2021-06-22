package main;

import main.controlling.GameState;
import main.controlling.PlayerColor;
import main.controlling.PlayerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Controller {

	private GUI gui;
	private Data data;

	private final Random random;

	// evt auslagern in DATA
	private GameState gameState = GameState.NOT;
	private Player playerTurn = null;
	private int playerTurnDicedCount;
	//

	public Controller(GUI gui) {
		this.gui = gui;
		this.data = new Data(this);

		this.random = new Random();

		startGame(); //test
	}


	/**
	 * Wird durch die GUI aufgerufen um das Spiel zu starten
	 */
	public void startGame() {

		initPlayer();

		gameState = GameState.DETERMINE_ORDER;

		playerTurn = data.getINITIAL_PLAYERS().get(0);
		playerTurnDicedCount = 0;

//		if(playerTurn instanceof Bot) {
//			dice();
//		}

		//TODO GUI Aufruf, "Bitte Würfeln Spieler X"
		dice();
	}

	/**
	 * Initialisiert in den Daten die Player
	 * Einmal für Initial
	 * Einmal für den Spielablauf (Wird so umsortiert, sodass Beginner an 0 steht)
	 */
	private void initPlayer() {
		//
		//      Entspricht: RED,   GREEN,   BLUE,  YELLOW
		String[] AUSGUI = {"Bot", "Person", "Bot", "Person"};
		PlayerColor[] AUSGUIFAARBEN = {PlayerColor.RED, PlayerColor.GREEN, PlayerColor.BLUE, PlayerColor.YELLOW};
		//

		ArrayList<Player> players = new ArrayList<>();


		//Ermittle Spieler/Bot Ordnung durch GUI
		for(int i = 0; i < 4; i++) {
			if(AUSGUI[i].equals("Bot")) {
				players.add(new Bot(AUSGUIFAARBEN[i]));
			} else {
				players.add(new Gamer(AUSGUIFAARBEN[i]));
			}
		}

		data.setINITIAL_PLAYERS(players);
		data.setPlayers(new ArrayList<>(players));
	}

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

		if(data.getPlayers().contains(playerTurn)) {
			indexOfPlayerTurn = data.getPlayers().indexOf(playerTurn);

			try {
				playerTurn = data.getPlayers().get(indexOfPlayerTurn + 1);
			} catch(Exception ex) {
				playerTurn = data.getPlayers().get(0);
			}
		} else {
			playerTurn = data.getPlayers().get(0);
		}


		playerTurnDicedCount = 0;

		//TODO GUI Aufruf, "Bitte Würfeln Spieler X"

		//TODO entfernen
		dice();
	}

	/**
	 * Überprüft, wie viele und welche Spieler die höchste Punktzahl gewürfelt haben.
	 * Wird nur bei deim Beginner auswürfeln aufgerufen.
	 * alle Spieler mit zu kleinen Augenzahlen werden aus data.players gelöscht (setPlayerOder() wieder hinzugefügt)
	 *
	 * @return ob es einen Spieler gibt, der die höchste Zahl gewüfelt hat
	 */
	private boolean determineBeginner() {
		int hightDice = 0;
		for(Player player : data.getPlayers()) {
			if(hightDice < player.getLastDiced()) {
				hightDice = player.getLastDiced();
			}
		}

		Iterator<Player> iterator = data.getPlayers().iterator();
		while(iterator.hasNext()) {
			Player player = iterator.next();
			if(player.getLastDiced() < hightDice) {
				iterator.remove();
			}
		}

		return data.getPlayers().size() <= 1;
	}

	/**
	 * Füllt data.player mit Spielern im Uhrzeigersinn
	 * Spieler mit der höchsten gewüfelten Zahl steht noch in data.player,
	 * der Rest wird aus data.INITALPLAYER geholt
	 */
	private void setPlayerOrder() {
		switch(data.getINITIAL_PLAYERS().indexOf(data.getPlayers().get(0))) {
			case 0:
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(1));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(2));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(3));
				break;
			case 1:
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(2));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(3));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(0));
				break;
			case 2:
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(3));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(0));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(1));
				break;
			case 3:
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(0));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(1));
				data.getPlayers().add(data.getINITIAL_PLAYERS().get(2));
				break;
		}
	}

	/**
	 * Setzt alle Letzte Würfel 0
	 */
	public void clearPlayerLastDice() {
		for(Player player : data.getPlayers()) {
			player.setLastDiced(0);
		}
	}


	/**
	 *  Holt sich die Daten aus Data und ruft in der GUI die Optionen Auswahl auf
	 */
	private void displayOptions() {

	}


	/**
	 *  Wird von der GUI aufgerufen
	 */
	public void selectOption(){

		//TODO Bearbeite Model nach Input
		//TODO Überprüfe ob gewonnen

		/*TODO Überprüfe ob 3x Würfeln aktiviert werden muss (evt auch für andere Spieler)
		* PROBLEM: Wenn man mit dieser Wahl im Ziel alle auf dem Spielfeld befindlichen Figuren
		* richtig anordnet müsste PlayerState auf THREE TIMES gesetzt werden. Allerding würde der
		* Spieler dann direkt wieder dran sein, weil nur bei NORMAL der nächste Spieler dran ist.
		*
		* Evt mit if THREE TIMES und dann return (darf die normalen THREE TIMES nicht beeinflussen)
		 */



		// Nächster Spieler wenn PlayerState == NORMAL
		if((playerTurn.getPlayerState() == PlayerState.NORMAL)) {
			nextPlayer();
			return;
		}


		//TODO GUI Aufruf, "Bitte Würfeln Spieler X"

	}


	/**
	 *  Wird von der GUI aufgerufen oder durch Controller wenn Bot
	 */
	public void dice() {
		int diced = random.nextInt(6)+1;
		playerTurnDicedCount += 1;
		//TODO GUI aufruf mit Würfelrgenbis anzeigen

		if((playerTurn.getPlayerState() == PlayerState.DICE_THREE_TIMES) && (playerTurnDicedCount <= 3)) {
			playerTurn.setPlayerState(PlayerState.NORMAL);
		}

		if(diced == 6 && gameState != GameState.DETERMINE_ORDER) {
			playerTurn.setPlayerState(PlayerState.DICE_AGAIN);
		}

		playerTurn.setLastDiced(diced);

		//TODO entfernen
		System.out.println(playerTurn.getPlayerColor().toString() + " hat " + diced + " gewürfelt");

		if(gameState == GameState.DETERMINE_ORDER) {
			if(playerTurn == data.getPlayers().get(data.getPlayers().size() - 1)) { // Ermittlung Beginner, jeder hat 1x gewürfelt
				if(determineBeginner()) {
					setPlayerOrder();
					clearPlayerLastDice();
					gameState = GameState.IN_GAME;

					//TODO entfernen
					System.out.println(data.getPlayers().get(0).getPlayerColor().toString() + " beginnt!\n");

					// Spiel beginnt mit Beginner
//					nextPlayer();
					return;
				}
			}
			nextPlayer();
			return;
		}

		displayOptions();

	}
}
