package main;

import main.gui.GameGUI;
import main.model.GameModel;
import main.model.enums.BotAction;

public class SleepThread implements Runnable {

	GameModel gameModel;
	BotAction botAction;

	public SleepThread(GameModel gameModel, BotAction botAction) {
		this.gameModel = gameModel;
		this.botAction = botAction;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);

			switch(botAction) {
				case DICE:
					gameModel.diceRoll();
					break;
				case PERFORM_OPTION:
					gameModel.performOption(gameModel.getOptions().get(gameModel.getRandom().nextInt(gameModel.getOptions().size())));
					break;
			}

		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
