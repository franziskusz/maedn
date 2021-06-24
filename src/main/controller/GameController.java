package main.controller;

import main.gui.GameGUI;
import main.model.enums.GameState;
import main.model.GameModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer, ActionListener {

	private GameModel gameModel;
	private GameGUI view;

	public GameController(GameModel gameModel, GameGUI view) {
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
				//TODO evt Button sichtbar machen, der Wieder das Initiale JFrame aufruft für neue Runde
			} else {
				if(gameModel.hasOption()) {
					//TODO aktiviere OPTION Buttons
					//	deaktiviere Würfel Button
					//	evt. admin Button deaktivieren

					// set Diced Würfelimage gameModel.getDiced();
					view.getText().setText("Wähle Option");
				} else {
					//TODO deaktiviere OPTION Buttons
					//	aktiviere Würfel Button
					//	evt. admin Button aktivieren

					view.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
				}
			}

			view.getBoardLayeredPane().setPieces(gameModel.getPieces());
			view.getBoardLayeredPane().repaint();

			System.out.println("Updated GUI");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case GameGUI.WUERFELN:
				gameModel.diceRoll();
				break;
			case GameGUI.OPTION_1:
				gameModel.performOption(1);
				break;
			case GameGUI.OPTION_2:
				gameModel.performOption(2);
				break;
			case GameGUI.OPTION_3:
				gameModel.performOption(3);
				break;
			case GameGUI.OPTION_4:
				gameModel.performOption(4);
				break;

			default:
				System.out.println("Unknown Action...");
				break;
		}
	}
}
