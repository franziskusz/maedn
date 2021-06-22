package main;

import main.gui.TestGameGUI;

public class SleepThread implements Runnable {

	TestGameGUI testGameGUI;

	public SleepThread(TestGameGUI testGameGUI) {
		this.testGameGUI = testGameGUI;
	}

	@Override
	public void run() {
		try {
			// thread to sleep for 1000 milliseconds
			Thread.sleep(2000);
			testGameGUI.getText().setText("???");
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
