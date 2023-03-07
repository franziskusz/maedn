package main.model.player;

import main.model.graph.Vertex;

public class Piece {

	private Player player;
	private int id;
	private Vertex position;
	private boolean isOption;

	public Piece(Player player, int id) {
		this.player = player;
		this.id = id;
		this.isOption = false;
	}

	public Player getPlayer() {
		return player;
	}

	public int getId() {
		return id;
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
