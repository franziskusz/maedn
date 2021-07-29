package main.controller;

import main.model.GameModel;
import main.model.enums.BotAction;

public class BotThread implements Runnable {

	private GameModel gameModel;
	private BotAction botAction;
	private static int pace=500;

	public BotThread(GameModel gameModel, BotAction botAction) {
		this.gameModel = gameModel;
		this.botAction = botAction;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(pace);

			switch(botAction) {
				case DICE:
					gameModel.diceRoll();
					break;
				case PERFORM_OPTION:
					gameModel.performOption(gameModel.getOptions().get(gameModel.getRandom().nextInt(gameModel.getOptions().size())));
					break;
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void setPace(int speed) {
		pace = speed;
	}

	public static int getPace() {
		return pace;
	}
}
