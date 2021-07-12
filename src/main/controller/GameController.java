package main.controller;

import main.gui.GameGUI;
import main.model.enums.GameState;
import main.model.GameModel;
import main.model.player.Bot;

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
		view.getBtnOption0().addActionListener(this);
		view.getBtnOption1().addActionListener(this);
		view.getBtnOption2().addActionListener(this);
		view.getBtnOption3().addActionListener(this);
		view.getBtnAdmin().addActionListener(this);

		// Buttons disable
		view.getBtnOption0().setEnabled(false);
		view.getBtnOption1().setEnabled(false);
		view.getBtnOption2().setEnabled(false);
		view.getBtnOption3().setEnabled(false);

		// mach Fenster sichtbar
		view.setVisible(true);

		initGUI();
	}

	private void initGUI() {
		if(gameModel.getPlayerTurn() instanceof Bot) {
			view.getBtnWuerfel().setEnabled(false);
			view.getBtnAdmin().setEnabled(false);
			view.getText().setText("Bot ist dran");
		} else {
			view.getBtnWuerfel().setEnabled(true);
			view.getBtnAdmin().setEnabled(true);
			view.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
		}

		System.out.println("Init GUI");
	}


	@Override
	public void update(Observable o, Object arg) {
		if(o == gameModel) {
			if(gameModel.getGameState() == GameState.END) {
				view.getText().setText("Gewonnen hat " + gameModel.getPlayerTurn().getPlayerColor().toString());
				//TODO evt Button sichtbar machen, der Wieder das Initiale JFrame aufruft für neue Runde
			} else {
				if(gameModel.getPlayerTurn() instanceof Bot) {
					view.getBtnWuerfel().setEnabled(false);
					view.getBtnOption0().setEnabled(false);
					view.getBtnOption1().setEnabled(false);
					view.getBtnOption2().setEnabled(false);
					view.getBtnOption3().setEnabled(false);
					view.getBtnAdmin().setEnabled(false);

					view.getText().setText("Bot ist dran");
				} else {
					if(gameModel.hasOption()) {
						//TODO aktiviere OPTION Buttons
						//	deaktiviere Würfel Button
						//	evt. admin Button deaktivieren
						view.getBtnWuerfel().setEnabled(false);
						view.getBtnAdmin().setEnabled(false);
						if(gameModel.getOptions().contains(0)) view.getBtnOption0().setEnabled(true);
						if(gameModel.getOptions().contains(1)) view.getBtnOption1().setEnabled(true);
						if(gameModel.getOptions().contains(2)) view.getBtnOption2().setEnabled(true);
						if(gameModel.getOptions().contains(3)) view.getBtnOption3().setEnabled(true);

						view.getText().setText("Wähle Option");
					} else {
						//TODO deaktiviere OPTION Buttons
						//	aktiviere Würfel Button
						//	evt. admin Button aktivieren
						view.getBtnWuerfel().setEnabled(true);
						view.getBtnAdmin().setEnabled(true);
						view.getBtnOption0().setEnabled(false);
						view.getBtnOption1().setEnabled(false);
						view.getBtnOption2().setEnabled(false);
						view.getBtnOption3().setEnabled(false);

						view.getText().setText("Bitte Würfeln " + gameModel.getPlayerTurn().getPlayerColor().toString());
					}
				}
			}

			// TODO set Diced Würfelimage gameModel.getDiced();
			view.getTfDiced().setText(String.valueOf(gameModel.getDiced()));

			// TODO evt den Hindergrund der Pane wo die Buttons drauf sind in der Farbe des playerTurn Spielers

			view.getBoardLayeredPane().setPieces(gameModel.getPieces());
			view.getBoardLayeredPane().repaint();

			//TODO entfernen
			System.out.println("Updated GUI");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case GameGUI.WUERFELN:
				gameModel.diceRoll();
				break;
			case GameGUI.OPTION_0:
				gameModel.performOption(0);
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
			case GameGUI.ADMIN:
				gameModel.perfromAdminCommand(view, view.getTfAdmin().getText());
				view.getTfAdmin().setText("");
				break;

			default:
				System.out.println("Unknown Action...");
				break;
		}
	}
}
