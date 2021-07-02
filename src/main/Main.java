package main;

import main.controller.GameController;
import main.gui.GameGUI;
import main.model.GameModel;
import main.model.enums.PlayerColor;
import main.model.graph.Graph;
import main.model.player.Gamer;
import main.model.player.Player;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {


		Graph board = new Graph();
		board.initGraph();
		//board.testPrint();
		
		board.printGUIRaster();
		System.out.println();
		
		board.getCoordinateXofVertex(0);
		board.getCoordinateYofVertex(0);
		
		board.getVertexIndexbyPiece(null);
		
		


		ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.RED));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.BLUE));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.GREEN));
		INITIAL_PLAYERS.add(new Gamer(PlayerColor.YELLOW));

		
		GameModel gameModel = new GameModel(INITIAL_PLAYERS);
		GameGUI gameGui = new GameGUI(gameModel.getPieces());
		GameController gameController = new GameController(gameModel, gameGui);
		

		
	}
}
