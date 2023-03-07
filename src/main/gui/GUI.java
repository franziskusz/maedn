package main.gui;

//Import
import javax.swing.*;

/*
 * Überklasse für GUI 
 */
public class GUI extends JFrame {

	/**
	 * Erstellt eine Warnmeldung wenn Spiel beendet werden soll und fragt ab
	 * ob man das Spiel wirklich beenden möchte
	 * @Credits Vorlesung Prof. Krämer
	 */
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
