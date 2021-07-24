package main.model.player;

import main.model.enums.PlayerColor;
import main.model.enums.PlayerState;

import java.util.Comparator;

public class Player {

	private int lastDiced;
	private PlayerState playerState;
	private Piece[] pieces = {new Piece(this, 0), new Piece(this, 1), new Piece(this, 2),new Piece(this, 3)};
	private PlayerColor playerColor;

	private static int nextPlace = 1;

	private boolean goalAchieved;
	private Integer place;


	public Player() {
		playerState = PlayerState.DICE_THREE_TIMES;
		goalAchieved = false;
		place = null;
	}

	public static Comparator<Player> sortByPlace = new Comparator<Player>() {
		@Override
		public int compare(Player player1, Player player2) {
			return player1.getPlace().compareTo(player2.getPlace());
		}
	};


	public int getLastDiced() {
		return lastDiced;
	}

	public void setLastDiced(int lastDiced) {
		this.lastDiced = lastDiced;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public boolean isGoalAchieved() {
		return goalAchieved;
	}

	public void setGoalAchieved() {
		this.goalAchieved = true;
		place = nextPlace;
		nextPlace++;
	}

	public static void resetNextPlayer() {
		nextPlace = 1;
	}

	public Integer getPlace() {
		return place;
	}
}
