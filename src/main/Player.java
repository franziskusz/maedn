package main;

import main.controlling.PlayerColor;
import main.controlling.PlayerState;

public class Player {

	private int lastDiced;
	private PlayerState playerState;

	private PlayerColor playerColor;


	public Player() {
		playerState = PlayerState.NORMAL;
	}


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
}
