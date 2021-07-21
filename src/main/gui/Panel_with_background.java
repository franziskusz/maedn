package main.gui;

import main.model.enums.PlayerColor;
import main.model.player.Piece;
import main.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;



public class Panel_with_background extends JLayeredPane {

	private Image img;
	private ArrayList<Piece> pieces;

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
		
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		
		//Idee!!!
		//setBorder(BorderFactory.createLoweredBevelBorder());
		
		/* Meine TO-DO's
		 * 
		 * Schriftart+Größe Spielstein ID ändern
		 * 
		 * Spiel GUI grafisch optimieren
		 *		Farbe der Häuser dunkler als Spielsteinfarbe
		 *		eventuell opacity verändern
		 * 
		 * Buttons verändern Schriftfarbe Schriftart, Hintergrund
		 * 
		 * Würfel anzeigen (Bild)
		 * 
		 * Farbe im Hintergrund hinzufügen (des spielers der gerade dran ist)
		 * 
		 * 
		 * Eingabefeld auf dem schirm haben. 
		 * Auch Admin Regeler ob Admin oder nicht in der setupGUI
		 * 
		 * 
		 * Gimmicideen
		 * 		Spielsteine wenn aufgerückt thumbs up oder smileyface
		 * 		Würfelgeräusche
		 * 		Bowling Animation beim Schlagen
		 */
		
		
		
		
		
		//Spielfeld
		g.setColor(new Color (225,221,176,200));
		//g.setColor(Color.black);
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
		g.fillOval( 70, 320, 40, 40);//6,1
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

		g.setColor(new Color (0,0,255,150));
		//g.setColor(Color.blue);
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

		g.setColor(new Color (35, 149, 0,170));
		//g.setColor(Color.green);
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
		
		
		g.setColor(new Color (118, 0, 0,150));
		//g.setColor(Color.red);
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

		g.setColor(new Color (255,215,0,150));
		//g.setColor(Color.yellow);
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

		// NUR SOLANGE PIECES BEIM INITALISIEREN KEIN FELD HABEN
		if(pieces.get(0).getPosition() != null) {
			for(Piece piece : pieces) {
				int x = 20 + (piece.getPosition().getCoordinateX() * 50) + 5;
				int y = 20 + (piece.getPosition().getCoordinateY() * 50) + 5;

				int pieceSize = 30;

				switch(piece.getPlayer().getPlayerColor()) {
					case RED:
						g.setColor(new Color (255,0,0,255));
						g.fillOval(x, y, pieceSize, pieceSize);
						break;
					case BLUE:
						g.setColor(new Color (30,144,255,255));
						g.fillOval(x, y, pieceSize, pieceSize);
						break;
					case GREEN:
						g.setColor(new Color (0,255,0,225));
						g.fillOval(x, y, pieceSize, pieceSize);
						break;
					case YELLOW:
						g.setColor(new Color (255,255,0,225));
						g.fillOval(x, y, pieceSize, pieceSize);
						break;
				}

				// Zeige in den spielbaren Spielfiguren die pieceID an
				if(piece.isOption()) {
					g.setColor(new Color (0,0,0,255));
					g.drawString(String.valueOf(piece.getId()), x+11, y+20);
				}

				// Zeige den Platz an, wenn feritg
				if(piece.getPlayer().isGoalAchieved()) {
					g.setColor(new Color (0,0,0,255));
					g.drawString(String.valueOf(piece.getPlayer().getPlace()), x+11, y+20);
				}
			}
		}

	}

	/**
	 * Setzt die Spielfiguren, damit diese gepaintet werden können
	 * @param pieces
	 */
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
}
