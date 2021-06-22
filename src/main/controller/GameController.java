package main.controller;

import main.model.enums.GameState;
import main.model.GameModel;
import main.gui.TestGameGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer, ActionListener {

	private GameModel gameModel;
	private TestGameGUI view;

	public GameController(GameModel gameModel, TestGameGUI view) {
		this.gameModel = gameModel;
		this.view = view;

		// ControllerGUI soll Model beobachten
		gameModel.addObserver(this);



		// set Listener für Buttons
		view.getBtnWuerfel().addActionListener(this);
		view.getBtnOption1().addActionListener(this);
		view.getBtnOption2().addActionListener(this);
		view.getBtnOption3().addActionListener(this);
		view.getBtnOption4().addActionListener(this);


		//TODO setze Inititiale Werte auf der Spieloberfläche, die noch nicht gesetzt wurden



		// mach Fenster sichtbar
		view.setVisible(true);

		initGUI();
	}

	private void initGUI() {
		view.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
		System.out.println("Updated GUI");
	}


	@Override
	public void update(Observable o, Object arg) {
		if(o == gameModel) {
			if(gameModel.getGameState() == GameState.END) {
				view.getText().setText("Gewonnen hat " + gameModel.getPlayerTurn().getPlayerColor().toString());
			} else {
				if(gameModel.hasOption()) {
					//TODO aktiviere OPTION Buttons
					//	deaktiviere Würfel Button
					//	evt. admin Button deaktivieren

					view.getText().setText("Wähle Option");
				} else {
					//TODO deaktiviere OPTION Buttons
					//	aktiviere Würfel Button
					//	evt. admin Button aktivieren

					view.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
				}
			}


			System.out.println("Updated GUI");
			// view set Values aus model
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case TestGameGUI.WUERFELN:
				gameModel.diceRoll();
				break;
			case TestGameGUI.OPTION_1:
				gameModel.performOption(1);
				break;
			case TestGameGUI.OPTION_2:
				gameModel.performOption(2);
				break;
			case TestGameGUI.OPTION_3:
				gameModel.performOption(3);
				break;
			case TestGameGUI.OPTION_4:
				gameModel.performOption(4);
				break;

			default:
				System.out.println("Unknown Action...");
				break;
		}
	}
}
