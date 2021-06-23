package main;

import main.controller.GameController;
import main.gui.GameGUI;
import main.gui.TestGameGUI;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.graph.Graph;
import main.model.player.Gamer;
import main.model.player.Player;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		
		GameGUI gameGui = new GameGUI();


		Graph board = new Graph();
		board.initGraph();
		board.testPrint();

		/*
		// TESTS JOHANNES
		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.YELLOW));

		TestGameGUI testGameGUI = new TestGameGUI();
		GameModel gameModel = new GameModel(INITIAL_PLAYERS);
		GameController gameController = new GameController(gameModel, testGameGUI);
		*/
		
	}
}
