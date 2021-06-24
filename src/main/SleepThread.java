package main;

import main.gui.GameGUI;

public class SleepThread implements Runnable {

	GameGUI gameGUI;

	public SleepThread(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	@Override
	public void run() {
		try {
			// thread to sleep for 1000 milliseconds
			Thread.sleep(2000);
			gameGUI.getText().setText("???");
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
