package main.model.player;

import main.model.enums.PlayerColor;
import main.model.enums.PlayerState;

public class Player {

	private int lastDiced;
	private PlayerState playerState;
	private Piece[] pieces = {new Piece(this, 0), new Piece(this, 1), new Piece(this, 2),new Piece(this, 3)};
	private PlayerColor playerColor;


	public Player() {
		playerState = PlayerState.DICE_THREE_TIMES;
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

	public Piece[] getPieces() {
		return pieces;
	}
}
