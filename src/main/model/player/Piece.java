package main.model.player;

import main.model.enums.PieceState;
import main.model.graph.Vertex;

public class Piece {

	Player player;
	int id;
	PieceState pieceState;
	Vertex position;
	private boolean isOption;

	public Piece(Player player, int id) {
		this.player = player;
		this.id = id;
		this.isOption = false;
		this.pieceState = PieceState.HOME;
	}

	public Player getPlayer() {
		return player;
	}

	public int getId() {
		return id;
	}

	public PieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PieceState pieceState) {
		this.pieceState = pieceState;
	}

	public Vertex getPosition() {
		return position;
	}

	public void setPosition(Vertex position) {
		this.position = position;
	}

	public boolean isOption() {
		return isOption;
	}

	public void setOption(boolean option) {
		isOption = option;
	}
}
