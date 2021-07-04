package main.model.graph;

import main.model.player.Piece;
import main.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph
{
	private Map <Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	private List<Edge> edges = new ArrayList<Edge>();
	private int [][] guiRaster = new int [11][11];

	public Graph(ArrayList<Player> players)
	{

		// TODO @Franziskus
		// 	mit players.get(i).getPieces()[j]; bekomst du die Pieces, die du dann auf die Home Felder setzen kannst
		//  ist glaube ich auch nach der Logik unten Links zuerst, dann oben links, oben rechts unten rechts
		//  pieces sollten auch schon angezeigt werden, wenn sie einen Vertex zugeordnet bekommen

		initGraph();
		
		// Setzen der Spielsteine auf ihre Startpositionen
		initPiecePositions(players);
		
		
		//debug und Testausgaben
		printGUIRaster();
		System.out.println();
		getCoordinateXofVertex(40); 
		getCoordinateYofVertex(40);
		
		//testPrint(); //alle Kanten
		/*
		for (int i = 0; i<16; i++)
		{
			System.out.print(vertices.get(i+40)+" ");
			vertices.get(i+40).printPiece();
		}
		System.out.print(vertices.get(0)+" ");
		vertices.get(0).printPiece();
		*/
		
		System.out.println("Stein 1 von Spieler 1 steht auf Vertex "+getVertexIndexbyPiece(players.get(0).getPieces()[0])); //debug
		System.out.println("Stein 2 von Spieler 3 steht auf Vertex "+players.get(2).getPieces()[1].getPosition().getIndex()); //debug
		
		System.out.println("Testausgabe Player 1 Options für Dice 6:");
		getOptions(players.get(0), 6); //Debug
	}
	
	/**
	 * Gibt die Möglichleiten für den Spieler mit gewürfelter Zahl zurück (inform der pieceIDs)
	 * Bei keinen Möglichkeiten return null;
	 *
	 * @param player
	 * @param diced
	 * @return
	 */
	
	//erster Ansatz, funktioniert noch nicht
	public ArrayList<Integer> getOptions(Player player, int diced)
	{
		ArrayList<Integer> options=new ArrayList<Integer>();
		Vertex[] optionVertices=new Vertex[4];
		//System.out.println(options.toString()); //debug
		
		//Erst werden alle Ausgangspositionen in einem Array abgelegt
		for (int i = 0; i<4; i++) 
		{
			optionVertices[i]=player.getPieces()[i].getPosition(); 
		}
		System.out.println(Arrays.toString(optionVertices));
		
		
		//Für alle Ausgangspositionen wird geschaut, wie viele Ziele (Kanten) es von dort aus gibt
		for (int j = 0; j<4; j++)
		{
			int sizeOptions=optionVertices[j].getSucc().size();
			
			//Für jede dieser Kanten wird dann geprüft, ob sie mit dem Würfelergebnis begehbar sind.
			//Wenn ja, werden die Indexe der Zielknoten in der ArrayList options abgelegt
			for (int k=0; k<sizeOptions; k++)
			{	
				//Prüfen, welche Felder erreichbar sind
				if((optionVertices[j].getSucc().get(k).getWeight()==diced))
						//prüfen ob auf dem Zielfeld ein Stein des aktiven Spielers steht
						//&&(optionVertices[j].getSucc().get(k).getTo().getPiece().getPlayer()!=player)) //nullpointer exception, wenn kein Spieler vorhanden ist
				{
					//options.add(optionVertices[j].getSucc().get(k).getTo().getIndex());
					options.add(optionVertices[j].getPiece().getId());
				}					
			}
		}			
		
		//System.out.println(options.listIterator());
		System.out.println(options.toString());//Testausgabe
		
		if (options.size()==0)
		{
			return null;
		}
		else
		{
			return options;
		}	
	}
	
	/*
	 *
	 * Auf der Basis des MÄDN Spielbretts wird ein Graph angelegt
	 * 
	 * Es gibt 40 Rundreise Felder (Knoten 0-39)
	 * 
	 * 4x4 Haus Felder
	 * Spieler 1: Knoten 40-43
	 * Spieler 2: Knoten 44-47
	 * Spieler 3: Knoten 48-51
	 * Spieler 4: Knoten 52-55
	 * 
	 * 4x4 Zielfelder
	 * Spieler 1: Knoten 56-59
	 * Spieler 2: Knoten 60-63
	 * Spieler 3: Knoten 64-67
	 * Spieler 4: Knoten 68-71
	 * 
	 * Spieler 1: Starfeld = 0, letztes Feld = 39
	 * Spieler 2: Starfeld = 10, letztes Feld = 9
	 * Spieler 3: Startfeld = 20, letztes Feld = 19
	 * Spieler 4: Startfeld = 30, letztes Feld = 29
	 * 
	 * Jedes Feld auf dem Rundweg ist mit seinen Vorgängern und Nachfolgern entsprechend der
	 * möglichen Würfelergebnisse verbunden
	 * 
	 * Das Kantengewicht entspricht der Entfernung
	 * 
	 * Die Hausfelder sind mit dem Kantengewicht 6 mit dem Startfeld des jeweiligen Spielers verbunden
	 * 
	 * Die Zielfelder sind mit ihren Vorgängern auf dem Rundweg und mit ihren Vorgängern im Ziel
	 * und dem Kantengewicht entsprechend der Entfernung verbunden
	 * 
	 * Falls hier in der Zählung und den Kanten noch Fehler vorhanden sind
	 * bitte ich um Entschuldigung, aber bei mittlerweile 352 Kanten den 
	 * Überblick zu behalten ist nicht ganz einfach :D
	 * 
	 * TODO: Abfragefunktion, die anhand der Positionen der Spielerfiguren und des
	 * Würfelergebnisses mögliche Züge vorschlägt
	 * 
	 * 
	 */
	
	public Graph initGraph()
	{
		//Initialisierung der insgesamt 72 Knoten/Felder
		initGUIRaster();
		
		initVertices();
		
		initTour();
		initHome();
		initGoal();
		
		return null;
	}
	
	
	private void initPiecePositions(ArrayList<Player> players)
	{
		for (int i = 40; i<56; i++)
		{
			if (i==40)
			{
				int count=0;
				for (int k = 40; k<44; k++)
				{	
					vertices.get(k).setPiece(players.get(0).getPieces()[count]);
					players.get(0).getPieces()[count].setPosition(vertices.get(k));
					count++;
				}
			}
			else if (i==44)
			{
				int count=0;
				for (int k = 44; k<48; k++)
				{	
					vertices.get(k).setPiece(players.get(1).getPieces()[count]);
					players.get(1).getPieces()[count].setPosition(vertices.get(k));
					count++;
				}
			}
			else if (i==48)
			{
				int count=0;
				for (int k = 48; k<52; k++)
				{	
					vertices.get(k).setPiece(players.get(2).getPieces()[count]);
					players.get(2).getPieces()[count].setPosition(vertices.get(k));
					count++;
				}
			}
			else
			{
				int count=0;
				for (int k = 52; k<56; k++)
				{	
					vertices.get(k).setPiece(players.get(3).getPieces()[count]);
					players.get(3).getPieces()[count].setPosition(vertices.get(k));
					count++;
				}
			}	
		}
	}
	
	
	
	// optional (kann evtl. weg) Getter für die im Knoten gespeicherten Koordinaten
	public int getCoordinateXofVertex(int vertexIndex)
	{
		System.out.println("Knoten ["+vertexIndex+"] X = "+vertices.get(vertexIndex).getCoordinateX()); //debug
	    return vertices.get(vertexIndex).getCoordinateX();
	}
	
	public int getCoordinateYofVertex(int vertexIndex)
	{
		System.out.println("Knoten ["+vertexIndex+"] Y = "+vertices.get(vertexIndex).getCoordinateY());
		return vertices.get(vertexIndex).getCoordinateY();
	}
	
	// optional (kann evtl weg) Getter der für einen Spielstein überprüft, auf welchem Feld er vorhanden ist und dessen Index zurückgibt
	public int getVertexIndexbyPiece(Piece piece)
	{
		int vertexIndex=-1;
		for (int i = 0; i<72; i++)
		{
			if (vertices.get(i).getPiece()==piece)
			{
				vertexIndex=i;
				break;
			}
		}
		return vertexIndex;
	}
	
	
	
	// Klassenintern getter für die Koordinaten mit dem Index des Knoten als Parameter
	private int getRasterX(int vertexIndex)
	{
		int x = -1;
		for (int i=0; i<11; i++)
		{
			for (int j=0; j<11; j++)
			{
				if (guiRaster[j][i]==vertexIndex)
				{
					x = j;
					break;
				}
			}
		}
		return x;
	}
		
	private int getRasterY(int vertexIndex)
	{
		int y = -1;
		for (int i=0; i<11; i++)
		{
			for (int j=0; j<11; j++)
			{
				if (guiRaster[j][i]==vertexIndex)
				{
					y = i;
					break;
				}
			}
		}
		return y;
	}		

	public void printGUIRaster()
	{
		for (int i = 0; i<11; i++)
		{
			System.out.print("\n");
			for (int j=0; j<11; j++)
				if (guiRaster[j][i]<10&&guiRaster[j][i]>=0)
				{
					System.out.print("["+0+guiRaster[j][i]+"]");
				}
				else if (guiRaster[j][i]<0)
				{
					System.out.print("    "); //aus -1 werden leere Felder
				}
				else
				{
					System.out.print("["+guiRaster[j][i]+"]");
				}
		}	
	}
	
	public Map<Integer, Vertex> getVertices()
	{
		return vertices;
	}
	
	public void testPrint()
	{
		
		int count = 0;
		for (Edge edge : edges)
		{
			++count;
			System.out.println("Edge #"+count+" "+edge);	
		}
	}
	
	
	
	/*
	 * Teilfunktionen der Grapheninitialisierung 
	 * ganz oben die Rasterkoordinaten der einzelnen Knoten
	 * danach folgen die Kanten
	 */
	private void initVertices()
	{
		int numberFields=72;
		for (int i=0; i<numberFields; i++)
		{
			Vertex v = new Vertex(this);
			vertices.put(v.getIndex(),v);
			v.setCoordinateX(getRasterX(i));
			v.setCoordinateY(getRasterY(i));
		}
	}
	
	
	private void initGUIRaster()
	{	//zunächst mit -1 füllen, weil -1 für alle weiteren Funktionen, die auf das Raster zugreifen 'neutral' ist
		for (int i = 0; i<11; i++)
		{
			for (int j=0; j<11; j++)
				guiRaster[i][j]=-1;
		}
		/*
		 * ab hier wird es wieder unübesichtlich, aber ich sehe keine Alternative dazu, jeder Koordinate ihren Knoten einzeln zuzuweisen
		 */
		
		//Spieler 1 links unten
		//Home
		guiRaster[0][9]=40;
		guiRaster[1][9]=41;
		guiRaster[0][10]=42;
		guiRaster[1][10]=43;
		//Zielfelder
		guiRaster[5][9]=56;
		guiRaster[5][8]=57;
		guiRaster[5][7]=58;
		guiRaster[5][6]=59;
		
		//Spieler 2 links oben
		//Home
		guiRaster[0][0]=44;
		guiRaster[1][0]=45;
		guiRaster[0][1]=46;
		guiRaster[1][1]=47;
		//Zielfelder
		guiRaster[1][5]=60;
		guiRaster[2][5]=61;
		guiRaster[3][5]=62;
		guiRaster[4][5]=63;
		
		//Spieler 3 rechts oben
		//Home
		guiRaster[9][0]=48;
		guiRaster[10][0]=49;
		guiRaster[9][1]=50;
		guiRaster[10][1]=51;
		//Zielfelder
		guiRaster[5][1]=64;
		guiRaster[5][2]=65;
		guiRaster[5][3]=66;
		guiRaster[5][4]=67;
		
		//Spieler 4 rechts unten
		//Home
		guiRaster[9][9]=52;
		guiRaster[10][9]=53;
		guiRaster[9][10]=54;
		guiRaster[10][10]=55;
		//Zielfelder
		guiRaster[9][5]=68;
		guiRaster[8][5]=69;
		guiRaster[7][5]=70;
		guiRaster[6][5]=71;
		
		//Rundreise
		//Spieler 1 Start->Spieler 2 Ende
		guiRaster[4][10]=0;
		guiRaster[4][9]=1;
		guiRaster[4][8]=2;
		guiRaster[4][7]=3;
		guiRaster[4][6]=4;//Kurve
		guiRaster[3][6]=5; 
		guiRaster[2][6]=6;
		guiRaster[1][6]=7;
		guiRaster[0][6]=8;//Kurve
		guiRaster[0][5]=9; 
		
		//Spieler 2 Start->Spieler 3 Ende
		guiRaster[0][4]=10;
		guiRaster[1][4]=11;
		guiRaster[2][4]=12;
		guiRaster[3][4]=13;
		guiRaster[4][4]=14;//Kurve
		guiRaster[4][3]=15; 
		guiRaster[4][2]=16;
		guiRaster[4][1]=17;
		guiRaster[4][0]=18;//Kurve
		guiRaster[5][0]=19; 
		
		//Spieler 3 Start->Spieler 4 Ende
		guiRaster[6][0]=20;
		guiRaster[6][1]=21;
		guiRaster[6][2]=22;
		guiRaster[6][3]=23;
		guiRaster[6][4]=24;//Kurve
		guiRaster[7][4]=25; 
		guiRaster[8][4]=26;
		guiRaster[9][4]=27;
		guiRaster[10][4]=28;//Kurve
		guiRaster[10][5]=29; 
		
		//Spieler 4 Start->Spieler 1 Ende
		guiRaster[10][6]=30;
		guiRaster[9][6]=31;
		guiRaster[8][6]=32;
		guiRaster[7][6]=33;
		guiRaster[6][6]=34;//Kurve
		guiRaster[6][7]=35; 
		guiRaster[6][8]=36;
		guiRaster[6][9]=37;
		guiRaster[6][10]=38;//Kurve
		guiRaster[5][10]=39; 	
	}
	
	/*
	* ab hier werden nur noch die Kanten angelegt
	*/
	private void initTour()
	{
		for (int i=0; i<40; i++)
		{
			int from, to, w;
			from = i;
			to=0;
			w=0;
			
			if (i<39)
			{
				for (int j=1; j<=6; j++)
				{
					to = i+j; // Kanten entsprechend der möglichen Würfelergebnisse
					w=j; // Gewicht gleich der Entfernung
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
			}
			else // das letzte Feld wird mit den ersten verbunden
			{
				for (int j=39; j>=34; j--)
				{	
					w = 0;
					to = i-j;
					w = i-j+1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
			}
		}
	}
	
	private void initHome()
	{
		for (int i=40; i<56; i++)
		{
			int from, to, w;
			from = i;
			if (i<=43)
			{
				to=0; //Startfeld Spieler 1
			}
			else if (i<=47)
			{
				to=10; //Startfeld Spieler 2
			}
			else if (i<=51)
			{
				to=20; //Startfeld Spieler 3
			}
			else
			{
				to=30; //Startfeld Spieler 4
			}
			w = 6;
			Edge e = new Edge(this, from, to, w);
			edges.add(e);	
		}
	}
	
	//TODO eventuell hier so loopen, dass ein Funktionsprototyp reicht, der für jeden Spieler aufgerufen werden kann.
	private void initGoal()
	{
		for (int i=56; i<72; i++)
		{
			int from, to, w;
			from=0;
			to = i;
			
			//Zielfelder Spieler 1
			if (i<=59) 
			{
				if (i==56)
				{
					for (int j = 0; j<6; j++)
					{
						from = 39-j;
						w=j+1;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==57)
				{
					for (int j = 0; j<5; j++)
					{
						from = 39-j;
						w=j+2;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					from = 56;
					w=1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
				else if (i==58)
				{
					for (int j = 0; j<4; j++)
					{
						from = 39-j;
						w=j+3;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<3; k++)
					{
						from = 58-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
				}
				
				else
				{
					for (int j = 0; j<3; j++)
					{
						from = 39-j;
						w=j+4;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<4; k++)
					{
						from = 59-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
			}
			
			//Zielfelder Spieler 2
			else if (i<=63) 
			{
				if (i==60)
				{
					for (int j = 0; j<6; j++)
					{
						from = 9-j;
						w=j+1;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==61)
				{
					for (int j = 0; j<5; j++)
					{
						from = 9-j;
						w=j+2;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					from = 60;
					w=1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
				else if (i==62)
				{
					for (int j = 0; j<4; j++)
					{
						from = 9-j;
						w=j+3;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<3; k++)
					{
						from = 62-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
				}
				
				else
				{
					for (int j = 0; j<3; j++)
					{
						from = 9-j;
						w=j+4;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<4; k++)
					{
						from = 63-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
			}
			
			//Zielfelder Spieler 3
			else if (i<=67) 
			{
				if (i==64)
				{
					for (int j = 0; j<6; j++)
					{
						from = 19-j;
						w=j+1;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==65)
				{
					for (int j = 0; j<5; j++)
					{
						from = 19-j;
						w=j+2;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					from = 64;
					w=1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
				else if (i==66)
				{
					for (int j = 0; j<4; j++)
					{
						from = 19-j;
						w=j+3;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<3; k++)
					{
						from = 66-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
				}
				
				else
				{
					for (int j = 0; j<3; j++)
					{
						from = 19-j;
						w=j+4;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<4; k++)
					{
						from = 67-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
			}
			
			//Zielfelder Spieler 4
			else 
			{
				if (i==68)
				{
					for (int j = 0; j<6; j++)
					{
						from = 29-j;
						w=j+1;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==69)
				{
					for (int j = 0; j<5; j++)
					{
						from = 29-j;
						w=j+2;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					from = 68;
					w=1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
				else if (i==70)
				{
					for (int j = 0; j<4; j++)
					{
						from = 29-j;
						w=j+3;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<3; k++)
					{
						from = 70-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
				}
				
				else
				{
					for (int j = 0; j<3; j++)
					{
						from = 29-j;
						w=j+4;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k = 1; k<4; k++)
					{
						from = 71-k;
						w=k;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
			}	
		}
	}
			
//Ende der Klassendef.
}
