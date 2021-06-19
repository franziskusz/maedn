package main;

public class Controller {

	private GUI gui;
	private Data data;

	public Controller(GUI gui) {

		this.gui = gui;
		this.data = new Data(this);

	}

}
