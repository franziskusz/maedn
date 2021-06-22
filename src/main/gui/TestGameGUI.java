package main.gui;

import javax.swing.*;
import java.awt.*;

public class TestGameGUI extends JFrame {

	public static final String WUERFELN = "WUERFELN";
	public static final String OPTION_1 = "OPTION_1";
	public static final String OPTION_2 = "OPTION_2";
	public static final String OPTION_3 = "OPTION_3";
	public static final String OPTION_4 = "OPTION_4";

	private JButton btnWuerfel = new JButton();
	private JButton btnOption1 = new JButton();
	private JButton btnOption2 = new JButton();
	private JButton btnOption3 = new JButton();
	private JButton btnOption4 = new JButton();

	private JTextField text = new JTextField();
	private JTextField wZahl = new JTextField();

	public TestGameGUI() {
		setTitle("?");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		text.setEditable(false);
		text.setText("?");
		text.setPreferredSize( new Dimension( 200, 24 ) );
		wZahl.setSize(400, 10);
		add(text);

		wZahl.setEditable(false);
		wZahl.setText("0");
		wZahl.setSize(10, 10);
		add(wZahl);

		btnWuerfel.setText("Würfeln");
		btnWuerfel.setActionCommand(WUERFELN);
		add(btnWuerfel);

		btnOption1.setText("Option 1");
		btnOption1.setActionCommand(OPTION_1);
		add(btnOption1);

		btnOption2.setText("Option 2");
		btnOption2.setActionCommand(OPTION_2);
		add(btnOption2);

		btnOption3.setText("Option 3");
		btnOption3.setActionCommand(OPTION_3);
		add(btnOption3);

		btnOption4.setText("Option 3");
		btnOption4.setActionCommand(OPTION_3);
		add(btnOption4);


		pack();
	}

	public JButton getBtnWuerfel() {
		return btnWuerfel;
	}

	public JButton getBtnOption1() {
		return btnOption1;
	}

	public JButton getBtnOption2() {
		return btnOption2;
	}

	public JButton getBtnOption3() {
		return btnOption3;
	}

	public JButton getBtnOption4() {
		return btnOption4;
	}

	public JTextField getText() {
		return text;
	}
}
