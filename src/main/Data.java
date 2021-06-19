package main;


import java.util.ArrayList;

public class Data {

	private Controller controller;

	private ArrayList<Player> INITIAL_PLAYERS; // Für initalen Aufruf
	private ArrayList<Player> players; // geordnet nach Uhrzeigersinn und an Stelle 0 ist Beginner


	public Data(Controller controller) {
		this.controller = controller;
	}





	public ArrayList<Player> getINITIAL_PLAYERS() {
		return INITIAL_PLAYERS;
	}

	public void setINITIAL_PLAYERS(ArrayList<Player> INITIAL_PLAYERS) {
		this.INITIAL_PLAYERS = INITIAL_PLAYERS;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
