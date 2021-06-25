package main.gui;

import main.model.enums.PlayerColor;
import main.model.player.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel_with_background extends JLayeredPane {

	private Image img;
	private ArrayList<Piece> pieces;

//	public Panel_with_background(String img) {
//		this(new ImageIcon(img).getImage());
//	}

	public Panel_with_background(Image img, ArrayList<Piece> pieces) {
		this.pieces = pieces;
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("./images/background.png").getImage();
		g.drawImage(img, 0, 0, null);
		// 					(Zeile,Spalte)

		//Spielfeld
		g.setColor(Color.black);
		g.fillOval(220, 20, 40, 40);//0,4
		g.fillOval(270, 20, 40, 40);//0,5
		g.fillOval(20, 270, 40, 40);//5,0
		g.fillOval(20, 320, 40, 40);//6,0
		g.fillOval(520, 220, 40, 40);//4,10
		g.fillOval(520, 270, 40, 40);//5,10
		g.fillOval(270, 520, 40, 40);//10,5
		g.fillOval(320, 520, 40, 40);//10,6
		g.fillOval(70, 220, 40, 40);//4,1
		g.fillOval(120, 220, 40, 40);//4,2
		g.fillOval(170, 220, 40, 40);//4,3
		g.fillOval(220, 220, 40, 40);//4,4
		g.fillOval(320, 220, 40, 40);//4,6
		g.fillOval(370, 220, 40, 40);//4,7
		g.fillOval(420, 220, 40, 40);//4,8
		g.fillOval(470, 220, 40, 40);//4,9
		g.fillOval(70, 320, 40, 40);//6,1
		g.fillOval(120, 320, 40, 40);//6,2
		g.fillOval(170, 320, 40, 40);//6,3
		g.fillOval(220, 320, 40, 40);//6,4
		g.fillOval(320, 320, 40, 40);//6,6
		g.fillOval(370, 320, 40, 40);//6,7
		g.fillOval(420, 320, 40, 40);//6,8
		g.fillOval(470, 320, 40, 40);//6,9
		g.fillOval(220, 70, 40, 40);//1,4
		g.fillOval(220, 120, 40, 40);//2,4
		g.fillOval(220, 170, 40, 40);//3,4
		g.fillOval(320, 70, 40, 40); //1,6
		g.fillOval(320, 120, 40, 40); //2,6
		g.fillOval(320, 170, 40, 40); //3,6
		g.fillOval(220, 470, 40, 40); //9,4
		g.fillOval(220, 420, 40, 40);    //8,4
		g.fillOval(220, 370, 40, 40); //7,4
		g.fillOval(320, 470, 40, 40); //9,6
		g.fillOval(320, 420, 40, 40);    //8,6
		g.fillOval(320, 370, 40, 40); //7,6


		g.setColor(Color.blue);
		//Haus
		g.fillOval(20, 20, 40, 40); //0,0
		g.fillOval(70, 20, 40, 40); //0,1
		g.fillOval(20, 70, 40, 40); //1,0
		g.fillOval(70, 70, 40, 40); //1,1
		//Hotzone
		g.fillOval(20, 220, 40, 40); //4,0
		//Safezone
		g.fillOval(70, 270, 40, 40); //5,1
		g.fillOval(120, 270, 40, 40); //5,2
		g.fillOval(170, 270, 40, 40); //5,3
		g.fillOval(220, 270, 40, 40); //5,4


		g.setColor(Color.green);
		//Haus
		g.fillOval(470, 20, 40, 40);//0,9
		g.fillOval(470, 70, 40, 40);//1,9
		g.fillOval(520, 20, 40, 40);//0,10
		g.fillOval(520, 70, 40, 40);//1,10
		//Hotzone
		g.fillOval(320, 20, 40, 40);//0,6
		//Safezone
		g.fillOval(270, 70, 40, 40); //1,5
		g.fillOval(270, 120, 40, 40); //2,5
		g.fillOval(270, 170, 40, 40); //3,5
		g.fillOval(270, 220, 40, 40); //4,5


		g.setColor(Color.red);
		//Haus
		g.fillOval(20, 470, 40, 40);//9,0
		g.fillOval(20, 520, 40, 40);//10,0
		g.fillOval(70, 470, 40, 40);//9,1
		g.fillOval(70, 520, 40, 40);//10,1
		//Hotzone
		g.fillOval(220, 520, 40, 40);//10,4
		//Safezone
		g.fillOval(270, 470, 40, 40); //9,5
		g.fillOval(270, 420, 40, 40);    //8,5
		g.fillOval(270, 370, 40, 40); //7,5
		g.fillOval(270, 320, 40, 40);    //6,5


		g.setColor(Color.yellow);
		//Haus
		g.fillOval(470, 470, 40, 40); //9,9
		g.fillOval(470, 520, 40, 40); //10,9
		g.fillOval(520, 470, 40, 40); //9,10
		g.fillOval(520, 520, 40, 40); //10,10
		//Hotzone
		g.fillOval(520, 320, 40, 40);//6,10
		//Safezone
		g.fillOval(320, 270, 40, 40);//5,6
		g.fillOval(370, 270, 40, 40);//5,7
		g.fillOval(420, 270, 40, 40);//5,8
		g.fillOval(470, 270, 40, 40);//5,9

		//
		// NEU
		//

		//TODO @Toni Hier die Spielsteine Painten
		// mit den folgenden Befehlen wirst du dir dann wah. die Position holen können. (funktioneirt im Moment noch nciht)
		// pieces.get(i).getPosition().getX();
		// pieces.get(i).getPosition().getY();
		// if(pieces.get(i).getPlayer().getPlayerColor() == PlayerColor.BLUE) {
		//	//male Blauerkreis
		// }

		//Das hier ist ein beispielkreis, der beim erstenmal würfeln gesetzt wird
		g.setColor(Color.MAGENTA);
		g.fillOval(30, 30, 20, 20);

		//
		// NEU ENDE
		//
	}

	//
	// NEU
	//
	/**
	 * Setzt die Spielfiguren, damit diese gepaintet werden können
	 * @param pieces
	 */
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
	//
	// NEU ENDE
	//
}