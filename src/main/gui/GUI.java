package main.gui;

import javax.swing.*;

public class GUI extends JFrame {

	public void closeGUI() {
		Object[] options = {"Ja", "Nein"};
		int n = JOptionPane.showOptionDialog(this,
				"Willst du das Spiel wirklich beenden?",
				"Beenden bestätigen",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				options,
				options[0]);

		if(n != JOptionPane.YES_NO_OPTION) {
			return;
		}

		this.dispose();
		System.exit(0);
	}
}
