package main;

import main.gui.SetupGUI;
import main.model.graph.Vertex;
import main.model.player.Player;

public class Main {
	private static SetupGUI setupGUI;

	public static void main(String[] args) {
		startSetUp();
	}

	public static void startSetUp() {
		Player.resetNextPlayer();
		Vertex.resetIndexCount();
		setupGUI = (setupGUI == null) ? new SetupGUI() : setupGUI;
		setupGUI.showGUI();
	}
}

