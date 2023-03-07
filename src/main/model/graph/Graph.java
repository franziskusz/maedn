package main.model.graph;

import main.model.enums.PlayerState;
import main.model.player.Bot;
import main.model.player.Piece;
import main.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Gerichteter und gewichteter Graph als Repräsentation des Mensch ärgere dich nicht Spielfeldes.
 * Für jeden möglichen Spielzug gibt es eine Kante, dessen Gewicht das Würfelergebnis darstellt.
 * 
 * Basiert grundlegend auf der entsprechenden Übung im Seminar bei Prof. Dr. Krämer.
 */

public class Graph
{
	private Map <Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	private List<Edge> edges = new ArrayList<Edge>();
	private boolean superSpecialCase=false;

	public Graph(ArrayList<Player> INITIAL_PLAYERS)
	{
		initGraph();
	
		// Setzen der Spielsteine auf ihre Startpositionen
		initPiecePositions(INITIAL_PLAYERS);
		
		/*
		 * Debug und Testausgaben
		 */
		printGUIRaster(); //Ausgabe des 11x11 GUI Rasters
		System.out.println();
		//testPrint(); //Ausgabe aller 352 Kanten
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INHALTSVERZEICHNIS
	//
	
	/*
	 * 1. Initialiserung + Hilfsfunktionen + Ausgabefunktionen
	 * (ca. Zeilen 107-375)
	 * 
	 * 2. Public Funktionen, die vom Model aufgerufen werden
	 * 	2.1 getOptions()
	 *  2.2 performOption()
	 *  2.3 adminMove()
	 * (ca. Zeilen 377-650)
	 * 
	 * 3. Private Hilfsfunktionen für die Funktionen aus 2.
	 * (ca. Zeilen 650-1760)
	 * 	
	 */
	
	//
	// IINHALTSVERZEICHNIS END
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INIT
	//
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
	 */
	
	static final int[][] guiRaster = {
			{44,45,-1,-1,18,19,20,-1,-1,48,49,},
			{46,47,-1,-1,17,64,21,-1,-1,50,51,},
			{-1,-1,-1,-1,16,65,22,-1,-1,-1,-1,},
			{-1,-1,-1,-1,15,66,23,-1,-1,-1,-1,},
			{10,11,12,13,14,67,24,25,26,27,28,},
			{ 9,60,61,62,63,-1,71,70,69,68,29,},
			{ 8, 7, 6, 5, 4,59,34,33,32,31,30,},
			{-1,-1,-1,-1, 3,58,35,-1,-1,-1,-1,},
			{-1,-1,-1,-1, 2,57,36,-1,-1,-1,-1,},
			{40,41,-1,-1, 1,56,37,-1,-1,52,53,},
			{42,43,-1,-1, 0,39,38,-1,-1,54,55,}};
	
	private void initGraph()
	{
		initVertices();
		
		initTour();
		initHome();
		initGoal();
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
	
	/*
	 * Hilfsfunktionen für initVertices() 
	 * @param vertextIndex/KnotenIndex
	 * @return X bzw. Y Koordinate des Rasters
	 * TODO eventuell in eine Funktion zusammenfassen
	 */
	private int getRasterX(int vertexIndex)
	{
		int x = -1;
		out:
		for (int i=0; i<11; i++)
		{
			for (int j=0; j<11; j++)
			{
				if (guiRaster[i][j]==vertexIndex)
				{
					x = j;
					break out;
				}
			}
		}
		return x;
	}
		
	private int getRasterY(int vertexIndex)
	{
		int y = -1;
		out:
		for (int i=0; i<11; i++)
		{
			for (int j=0; j<11; j++)
			{
				if (guiRaster[i][j]==vertexIndex)
				{
					y = i;
					break out;
				}
			}
		}
		return y;
	}	
	
	/**
	 * Verbindet die Knoten der allgemeinen Rundreise durch Kanten mit den jeweiligen Würfelgewichten
	 */
	private void initTour() 
	{
		// i = Knotenindex in Rundreise, j = mögliches Würfelergebnis
		// @Edit Zusammengestaucht, da es mit Mod (%) 40 einfach bei 0 weitergeht
		for(int i = Fields.START_RED; i < 40; i++) 
		{
			int to;

			for(int j = 1; j <= 6; j++) 
			{
				to = (i + j) % 40; // Kanten entsprechend der möglichen Würfelergebnisse
				newEdge(i, to, j);
			}
		}
	}
	
	/**
	 * Verbindet die Knoten der der Häuser mit dem Knoten des Startfeldes
	 */
	private void initHome() 
	{
		int to;
		for(int i = Fields.RED_HOME_1; i < Fields.RED_GOAL_1; i++) 
		{
			if(i <= Fields.RED_HOME_4) 
			{
				to = Fields.START_RED; //Startfeld Spieler 1
			} 
			else if(i <= Fields.BLUE_HOME_4) 
			{
				to = Fields.START_BLUE; //Startfeld Spieler 2
			} 
			else if(i <= Fields.GREEN_HOME_4) 
			{
				to = Fields.START_GREEN; //Startfeld Spieler 3
			} 
			else 
			{
				to = Fields.START_YELLOW; //Startfeld Spieler 4
			}
			newEdge(i, to, 6);
		}
	}
	
	/**
	 * Setzt die Verbindungen zu den Zielknoten
	 */
	private void initGoal() {

		// @Edit, hier ist es wah. etwas schwer nachvollziehbar, was passiert. aber es ist
		// Zusammengestaucht und hat nicht so viele temporäre variablen

		int[] lastVertexPlayerHome = {Fields.RED_GOAL_4, Fields.BLUE_GOAL_4, Fields.GREEN_GOAL_4, Fields.YELLOW_GOAL_4};
		int[] lastVertexPlayerTour = {Fields.LAST_RED, Fields.LAST_BLUE, Fields.LAST_GREEN, Fields.LAST_YELLOW};

		for(int k = 0; k < 4; k++) {
			// Ziehen im Haus
			for(int i = lastVertexPlayerHome[k]; i > lastVertexPlayerHome[k] - 3; i--) {
				for(int j = (i - (lastVertexPlayerHome[k] - 3)); j > 0; j--) {
					newEdge(i-j, i, j);
				}
			}

			// Ziehen ins Haus
			for(int i = lastVertexPlayerTour[k]; i > lastVertexPlayerTour[k] - 6; i--) {
				for(int j = 6; j > 0; j--) {
					if(((i + j) > lastVertexPlayerTour[k])) {
						int x = i + j + ((lastVertexPlayerHome[k] - 3) - lastVertexPlayerTour[k] - 1);
						if(x <= lastVertexPlayerHome[k]) {
							newEdge(i, x, j);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Erzeugt neue Kante im Spielgraph
	 *
	 * @param from vonKnotenIndex
	 * @param to   zumKnotenIndex
	 * @param weight mitWürfelzahl
	 */
	private void newEdge(int from, int to, int weight) 
	{
		Edge e = new Edge(this, from, to, weight);
		edges.add(e);
	}
	
	/**
	 * Weise den Figuren initial ihren Knoten zu und setze die Figur im Knoten.
	 *
	 * @param INITIAL_PLAYERS Spieler in initialreihnfolge
	 */
	private void initPiecePositions(ArrayList<Player> INITIAL_PLAYERS) 
	{
		// @Edit Variable players in INITIAL_PLAYERS, weil es für die Bewertung wichtig ist das zu
		// unterscheiden. Außerdem count weg gemacht und durch k-i ersetzt (spart nur variable und ist wah. unüberischtlicher)
		for(int i = Fields.RED_HOME_1; i < Fields.RED_GOAL_1; i=i+4)
		{
			if(i == Fields.RED_HOME_1) {
				for(int k = Fields.RED_HOME_1; k < Fields.BLUE_HOME_1; k++) 
				{
					vertices.get(k).setPiece(INITIAL_PLAYERS.get(0).getPieces()[k-i]);
					INITIAL_PLAYERS.get(0).getPieces()[k-i].setPosition(vertices.get(k));
				}
			} 
			else if(i == Fields.BLUE_HOME_1) 
			{
				for(int k = Fields.BLUE_HOME_1; k < Fields.GREEN_HOME_1; k++) 
				{
					vertices.get(k).setPiece(INITIAL_PLAYERS.get(1).getPieces()[k-i]);
					INITIAL_PLAYERS.get(1).getPieces()[k-i].setPosition(vertices.get(k));
				}
			}
			else if(i == Fields.GREEN_HOME_1) 
			{
				for(int k = Fields.GREEN_HOME_1; k < Fields.YELLOW_HOME_1; k++) 
				{
					vertices.get(k).setPiece(INITIAL_PLAYERS.get(2).getPieces()[k-i]);
					INITIAL_PLAYERS.get(2).getPieces()[k-i].setPosition(vertices.get(k));
				}
			}
			else 
			{
				for(int k = Fields.YELLOW_HOME_1; k < Fields.RED_GOAL_1; k++) 
				{
					vertices.get(k).setPiece(INITIAL_PLAYERS.get(3).getPieces()[k-i]);
					INITIAL_PLAYERS.get(3).getPieces()[k-i].setPosition(vertices.get(k));
				}
			}
		}
	}
	

	/**
	 * Ausgabefunktionen
	 */
	public void printGUIRaster()
	{
		for (int j = 0; j<11; j++)
		{
			System.out.print("{");
			
			for (int i=0; i<11; i++)
			{
				if (guiRaster[j][i]<10&&guiRaster[j][i]>=0)
				{
					System.out.print(" "+guiRaster[j][i]+",");
				}
				else if (guiRaster[j][i]<0)
				{
					System.out.print("   "); //aus -1 werden leere Felder
				}
				else
				{
					System.out.print(""+guiRaster[j][i]+",");
				}
			}
			System.out.print("},\n");
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
	
	
	//
	// INIT END
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// AUFRUF AUS MODEL
	//
	/**
	 * Gibt die Möglichleiten für den Spieler mit gewürfelter Zahl zurück (inform der pieceIDs).
	 * Bei keinen Möglichkeiten return leere Liste.
	 *
	 * Prüft ob den Zielposition ein eigener Stein steht.
	 * Prüft ob Rundreise für den Spieler bereits beendet ist.
	 * Prüft ob im Zielfeld eine eigene Figur übersprungen werden müsste.
	 * Prüft ob die Figur in ein fremdes Zielfeld einlaufen würde.
	 * Sortiert Optionen aus die nicht den folgenden Pflichten entsprechen:
	 * 1. Verlassen der Heimatfelder 2. Verlassen des Startfeldes 3. Schlagpflicht.
	 *
	 * @param player
	 * @param diced
	 * @param players
	 * @return ArrayList
	 */
	public ArrayList<Integer> getOptions(Player player, int diced, ArrayList<Player> players)
	{
		superSpecialCase=false; //Reinitialiserung vor jedem Zug.
		ArrayList<Integer> options=new ArrayList<Integer>();
		Vertex[] optionVertices=new Vertex[4];
		
		//System.out.println(options.toString()); //debug
		
		//Erst werden alle Ausgangspositionen in einem Array abgelegt
		for (int i = 0; i<4; i++) 
		{
			optionVertices[i]=player.getPieces()[i].getPosition(); 
		}
		//System.out.println(Arrays.toString(optionVertices)); //debug
		
		
		//Für alle Ausgangspositionen wird geschaut, wie viele Ziele (Kanten) es von dort aus gibt.
		for (int j = 0; j<4; j++)
		{
			int sizeOptions=optionVertices[j].getSucc().size();
			
			//Für jede dieser Kanten wird dann geprüft, ob sie mit dem Würfelergebnis begehbar sind –
			//Und ob das Ziel für den jeweiligen Spielenden legal ist.
			//Wenn ja, werden die Indexe der Zielknoten in der ArrayList options abgelegt
			for (int k=0; k<sizeOptions; k++)
			{	
				//Prüfen, welche Felder erreichbar sind
				if((optionVertices[j].getSucc().get(k).getWeight()==diced)
						&&(checkTargetOccupation(player, optionVertices[j], diced, players) //NEU verwendet getTarget()
						&&(checkJourneyEnd(player, optionVertices[j], players, diced))
						&&(somethingInTheWay(player, optionVertices[j], optionVertices[j].getSucc().get(k).getTo(), players))
						&&(excludeOpponentsGoal(player, optionVertices[j].getSucc().get(k).getTo(), players))))
				{
					//System.out.println(optionVertices[j]+" Successors: "+optionVertices[j].getSucc()); //debug
					//System.out.println(options.toString()); //debug
					options.add(optionVertices[j].getPiece().getId());
					//System.out.println(options.toString()); //debug
				}					
			}
		}			

		//Hausverlasspflicht, Startverlasspflicht und Schlagpflicht prüfen
		ArrayList<Integer> leaveHomeRuleOptions = new ArrayList<Integer>();
		ArrayList<Integer> mustLeaveStartOptions = new ArrayList<Integer>();
		ArrayList<Integer> mustHitOptions = new ArrayList<Integer>();
		
		//TODO nur an einer Stelle überschreiben
		leaveHomeRuleOptions = leaveHomeRule(options, diced, player);
		options = leaveHomeRuleOptions;
		//System.out.println(options.toString());//Testausgabe
		
		mustLeaveStartOptions = leaveStart(options, diced, player, players);
		options = mustLeaveStartOptions;
		//System.out.println(options.toString());//Testausgabe
		
		mustHitOptions = mustHit(options, diced, player, players);
		options = mustHitOptions;
		
		//Handelt es sich um einen Bot, wird jene Option mit der kürzesten Entfernung zum Ziel gewählt.
		if ((player instanceof Bot)&&(options.size()>1))
		{
			options=sortBotOptions(options, player, players);
		}
		
		//System.out.println(options.toString());//Testausgabe
		return options;	
	}

	/**
	 * 
	 * Führt für den gewünschten Spielstein die Option aus.
	 *
	 * @param player
	 * @param diced
	 * @param option
	 * @param players
	 * @return
	 */
	public void performOption(Player player, Vertex option, int diced, ArrayList<Player> players)
	{
		Vertex target = getTarget(player, option, diced, players);
		int targetIndex = target.getIndex();
		Piece movingPiece = option.getPiece();
		Piece targetPiece = target.getPiece();

		if (target.getPiece() == null) // Laufen
		{
			vertices.get(targetIndex).setPiece(movingPiece);
			vertices.get(targetIndex).getPiece().setPosition(target);
			option.setPiece(null);
			
			//Prüfen, ob der sich bewegende Spieler nun dreimal Würfeln darf.
			if (targetIndex>Fields.YELLOW_HOME_4)
			{
				superSpecialCase=checkforSuperSpecialCase(player, players);
				//debug
				if(superSpecialCase == true)
				{
					System.out.println("DICE_THREE_TIMES "+player.getPlayerColor()); //debug
					player.setPlayerState(PlayerState.DICE_THREE_TIMES);
				}
			}

			if(checkGoal(player, players))
			{
				player.setGoalAchieved();
			}
			if (player.isGoalAchieved())
			{
				superSpecialCase=false;
			}
		}
		
		else //Schlagen
		{
			int targetPieceID = targetPiece.getId();
			Player targetPlayer = target.getPiece().getPlayer();
			boolean newPositionfound = false;
			boolean diceThreeTimes = false;
			
			//Geschlagene Figuren gehen nach Hause.
			sendTargetHome(target, players, newPositionfound, targetPieceID);
			
			vertices.get(targetIndex).setPiece(movingPiece);
			vertices.get(targetIndex).getPiece().setPosition(target);
			option.setPiece(null);
			
			//Prüfen, ob der geschlagene Spieler nun dreimal Würfeln darf.
			diceThreeTimes=checkforSuperSpecialCase(targetPlayer, players);
			//debug
			
			if(diceThreeTimes == true)
			{
				System.out.println("DICE_THREE_TIMES "+targetPlayer.getPlayerColor()); //debug
				targetPlayer.setPlayerState(PlayerState.DICE_THREE_TIMES);
			}
		}
	}
	
	
	//
	
	/**
	 * 
	 * Abgeleitet von performOption(), nur dass statt Vertex option und diced Vertex target und int PieceID übergeben werden. 
	 * Ermöglich fast beliebige Züge im Admin Modus, bis auf das Betreten gegnerischer Heimat- und Zielfelder und das Schlagen eigener Figuren.
	 *
	 * @param player
	 * @param pieceID
	 * @param target
	 * @param players
	 * @return
	 */
	public boolean adminMove(Player player, int pieceID, Vertex target, ArrayList<Player> players)
	{
		int targetIndex = target.getIndex();
		Piece movingPiece = player.getPieces()[pieceID];
		Vertex option = player.getPieces()[pieceID].getPosition();
		Piece targetPiece = target.getPiece();
		boolean legalMove = false;
		superSpecialCase = false;
		
		//Prüfen, dass der Zug nicht im gegnerischen Zielfeld landet, nicht in einem Heimatfeld landet und keine eigene Figur schlägt.
		if ((excludeOpponentsGoal(player, target, players))&&(checkAdminTarget(player, target, players)))
		{
			legalMove = true;
		}
		
		if (legalMove)
		{
			if (target.getPiece() == null) // Laufen
			{
				vertices.get(targetIndex).setPiece(movingPiece);
				vertices.get(targetIndex).getPiece().setPosition(target);
				option.setPiece(null);
				
				if (targetIndex>Fields.LAST_RED) //Prüfen ob der sich bewegende Spieler danach dreimal Würfeln darf.
				{
					superSpecialCase = checkforSuperSpecialCase(player, players);
					if(superSpecialCase == true)
					{
						System.out.println("DICE_THREE_TIMES "+player.getPlayerColor()); //debug
						player.setPlayerState(PlayerState.DICE_THREE_TIMES);
					}
					else 
					{
						System.out.println("Player State NORMAL "+player.getPlayerColor()); //debug
						player.setPlayerState(PlayerState.NORMAL);
					}
				}
				else
				{
					System.out.println("Player State NORMAL "+player.getPlayerColor()); //debug
					player.setPlayerState(PlayerState.NORMAL);
				}
				
				if(checkGoal(player, players))
				{
					player.setGoalAchieved();
					superSpecialCase = false;
				}
			}
			
			else //Schlagen
			{
				int targetPieceID=targetPiece.getId();
				Player targetPlayer = target.getPiece().getPlayer();
				boolean newPositionfound = false;
				boolean diceThreeTimes = false;
				
				//Geschlagene Pieces gehen nach Hause.
				sendTargetHome(target, players, newPositionfound, targetPieceID);
				
				vertices.get(targetIndex).setPiece(movingPiece);
				vertices.get(targetIndex).getPiece().setPosition(target);
				option.setPiece(null);
				
				//Prüfen ob der geschlagene Spieler danach dreimal Würfeln darf.
				diceThreeTimes=checkforSuperSpecialCase(targetPlayer, players);
				if(diceThreeTimes==true)
				{
					System.out.println("DICE_THREE_TIMES "+targetPlayer.getPlayerColor()); //debug
					targetPlayer.setPlayerState(PlayerState.DICE_THREE_TIMES);
				}
				else
				{
					System.out.println("Player State NORMAL "+targetPlayer.getPlayerColor());//debug
					targetPlayer.setPlayerState(PlayerState.NORMAL);
				}
				player.setPlayerState(PlayerState.NORMAL);
				
			}
		}
		if (legalMove)
		{
			System.out.println("Zug war ok"); //debug
		}
		if (!legalMove)
		{
			System.out.println("Zug war nicht ok"); //debug
		}
		return legalMove;
	}
	

	public boolean isSuperSpecialCase()
	{
		return superSpecialCase;
	}
	
	//
	// AUFRUF AUS MODEL END
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INTERNE METHODEN
	//
	
	
	/*
	 * Zentrale Hilfsfunktion für getOptions(), performOption() und adminMove()
	 * Ermittelt anhand des gegebenen Spielers und seiner Option den eindeutigen Zielknoten
	 * @param player
	 * @param option
	 * @param diced
	 * @param players (INITAL PLAYERS)
	 * @return Vertex target
	 */
	private Vertex getTarget(Player player, Vertex option, int diced, ArrayList<Player> players)
	{
		Vertex target=null;
		
		//Spieler 1
		if (player==players.get(0))
		{
			if(option.getIndex()<Fields.ROUNDABOUT_34)
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.RED_GOAL_1);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.RED_GOAL_1+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.RED_GOAL_1+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.RED_GOAL_1+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.RED_GOAL_1+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_RED)
			{
					target=vertices.get(Fields.RED_GOAL_1+diced-1);
			}
			else if ((option.getIndex()==Fields.RED_HOME_1)||(option.getIndex()==Fields.RED_HOME_2)||(option.getIndex()==Fields.RED_HOME_3)||(option.getIndex()==Fields.RED_HOME_4))
			{
					target=vertices.get(0);
			}
			else if ((option.getIndex()==Fields.RED_GOAL_1)||(option.getIndex()==Fields.RED_GOAL_2)||(option.getIndex()==Fields.RED_GOAL_3))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		//Spieler 2
		if (player==players.get(1))
		{
			if(((option.getIndex()<Fields.ROUNDABOUT_34)&&(option.getIndex()>Fields.LAST_BLUE))||(option.getIndex()<Fields.ROUNDABOUT_4))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_RED)
			{
					target=vertices.get(Fields.START_RED+diced-1);
			}
			
			else if (option.getIndex()==Fields.ROUNDABOUT_4)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.BLUE_GOAL_1);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_5)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.BLUE_GOAL_1+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_6)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.BLUE_GOAL_1+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_7)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.BLUE_GOAL_1+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_8)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.BLUE_GOAL_1+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_BLUE)
			{
					target=vertices.get(Fields.BLUE_GOAL_1+diced-1);
			}
			else if ((option.getIndex()==Fields.BLUE_HOME_1)||(option.getIndex()==Fields.BLUE_HOME_2)||(option.getIndex()==Fields.BLUE_HOME_3)||(option.getIndex()==Fields.BLUE_HOME_4))
			{
					target=vertices.get(Fields.START_BLUE);
			}
			else if ((option.getIndex()==Fields.BLUE_GOAL_1)||(option.getIndex()==Fields.BLUE_GOAL_2)||(option.getIndex()==Fields.BLUE_GOAL_3))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		
		//Spieler 3
		if (player==players.get(2))
		{
			if(((option.getIndex()<Fields.ROUNDABOUT_34)&&(option.getIndex()>Fields.LAST_GREEN))||(option.getIndex()<Fields.ROUNDABOUT_14))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_RED)
			{
					target=vertices.get(Fields.START_RED+diced-1);
			}
			
			else if (option.getIndex()==Fields.ROUNDABOUT_14)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.GREEN_GOAL_1);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_15)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.GREEN_GOAL_1+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_16)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.GREEN_GOAL_1+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_17)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.GREEN_GOAL_1+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_18)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.GREEN_GOAL_1+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_GREEN)
			{
					target=vertices.get(Fields.GREEN_GOAL_1+diced-1);
			}
			else if ((option.getIndex()==Fields.GREEN_HOME_1)||(option.getIndex()==Fields.GREEN_HOME_2)||(option.getIndex()==Fields.GREEN_HOME_3)||(option.getIndex()==Fields.GREEN_HOME_4))
			{
					target=vertices.get(Fields.START_GREEN);
			}
			else if ((option.getIndex()==Fields.GREEN_GOAL_1)||(option.getIndex()==Fields.GREEN_GOAL_2)||(option.getIndex()==Fields.GREEN_GOAL_3))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		
		//Spieler 4
		if (player==players.get(3))
		{
			if(((option.getIndex()<Fields.ROUNDABOUT_34)&&(option.getIndex()>Fields.LAST_YELLOW))||(option.getIndex()<Fields.ROUNDABOUT_24))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.START_RED+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_RED)
			{
					target=vertices.get(0+diced-1);
			}
			
			else if (option.getIndex()==Fields.ROUNDABOUT_24)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.YELLOW_GOAL_1);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_25)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.YELLOW_GOAL_1+diced-5);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_26)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.YELLOW_GOAL_1+diced-4);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_27)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.YELLOW_GOAL_1+diced-3);
				}
			}
			else if (option.getIndex()==Fields.ROUNDABOUT_28)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(Fields.YELLOW_GOAL_1+diced-2);
				}
			}
			else if (option.getIndex()==Fields.LAST_YELLOW)
			{
					target=vertices.get(Fields.YELLOW_GOAL_1+diced-1);
			}
			else if ((option.getIndex()==Fields.YELLOW_HOME_1)||(option.getIndex()==Fields.YELLOW_HOME_2)||(option.getIndex()==Fields.YELLOW_HOME_3)||(option.getIndex()==Fields.YELLOW_HOME_4))
			{
					target=vertices.get(Fields.START_YELLOW);
			}
			else if ((option.getIndex()==Fields.YELLOW_GOAL_1)||(option.getIndex()==Fields.YELLOW_GOAL_2)||(option.getIndex()==Fields.YELLOW_GOAL_3))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}		
		return target;
	}
	
	
	/*
	 * Die nächsten sieben Funktionen sind Hilfsfunktionen für getOptions()
	 */
	
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Prüft, ob das Haus verlassen werden kann bzw. muss, wenn ja, bleibt nur die Option.
	 * @param player
	 * @param option
	 * @param diced
	 * @param players (INITAL PLAYERS)
	 * @return options
	 */
	private ArrayList<Integer> leaveStart(ArrayList<Integer> options, int diced, Player player, ArrayList<Player> players)
	{
		ArrayList<Integer> selectedOptions = new ArrayList<Integer>();
		int sizeOptions=options.size();
		int add=0;
		int add2=0;
		
		for (int i = 0; i<4; i++)
		{
			if (player==players.get(i))
			{
				add=i*4;
				add2=i*10;
				break;
			}
		}
		
		for (int i = 0; i<sizeOptions; i++)
		{
			if((player.getPieces()[options.get(i)].getPosition().getIndex()==Fields.START_RED+add2)
					&&((vertices.get(Fields.RED_HOME_1+add).getPiece()!=null)
					||(vertices.get(Fields.RED_HOME_2+add).getPiece()!=null)
					||(vertices.get(Fields.RED_HOME_3+add).getPiece()!=null)
					||(vertices.get(Fields.RED_HOME_4+add).getPiece()!=null)))
			{
				selectedOptions.add(options.get(i));
			}
		}
		if (selectedOptions.size()>0)
		{
			options=selectedOptions;
		}
		
		return options;
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Prüfen ob geschlagen werden kann – wenn ja, wird eine veränderte Liste zurückgegeben.
	 * @param player
	 * @param option
	 * @param diced
	 * @param players (INITAL PLAYERS)
	 * @return options
	 */
	private ArrayList<Integer> mustHit(ArrayList<Integer> options, int diced, Player player, ArrayList<Player> players)
	{
		ArrayList<Integer> selectedOptions=new ArrayList<Integer>();
		int sizeOptions=options.size();
		//System.out.println(options.toString()); //debug
		//System.out.println(sizeOptions); //debug
		for (int i=0; i<sizeOptions; i++)
		{
			//System.out.println(options.toString());//debug
			Vertex option = player.getPieces()[options.get(i)].getPosition();
			Vertex target = getTarget(player, option, diced, players);
			
			if(target!=null)
			{
				if(target.getPiece()!=null)
				{
					selectedOptions.add(options.get(i));
				}
			}
			
		}
		
		if (selectedOptions.size()>0)
		{
			options=selectedOptions;
		}
		return options;
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Prüfen ob mit gewürfelter 6 das Haus verlassen werden kann, wenn ja, wird eine veränderte Options Liste zurückgegeben.
	 * @param player
	 * @param option
	 * @param diced
	 * @return options
	 */
	private ArrayList<Integer> leaveHomeRule(ArrayList<Integer> options, int diced, Player player)
	{
		ArrayList<Integer> selectedOptions=new ArrayList<Integer>();
		int sizeOptions=options.size();
		if (diced == 6)
		{
			for (int i=0; i<sizeOptions; i++)
			{
				for(int j=Fields.RED_HOME_1; j<Fields.RED_GOAL_1; j++)
				{
					if(player.getPieces()[options.get(i)].getPosition().getIndex()==j)
					{
						selectedOptions.add(options.get(i));
						break;
					}
				}
			}
			if (selectedOptions.size()>0)
			{
				options=selectedOptions;
			}
		}
		return options;
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Wenn auf dem Zielfeld eine Figur des aktiven Spielers steht, wird der zu bewegende Stein nicht als Option gezählt
	 * @param movingPlayer
	 * @param option
	 * @param diced
	 * @param players
	 * @return boolean
	 */
	private boolean checkTargetOccupation(Player movingPlayer, Vertex option, int diced, ArrayList<Player> players)
	{
		Piece targetPiece;
		if (getTarget(movingPlayer, option, diced, players)!=null)
		{
			targetPiece=getTarget(movingPlayer, option, diced, players).getPiece();
		}
		else
		{
			targetPiece=null;
		}
		
		
		if(targetPiece==null)
		{
			return true;
		}
		else if (movingPlayer==targetPiece.getPlayer())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Wenn der Würfelnde Spieler auf einem der letzten beiden Feldern der Rundreise steht,
	 * aber eine Zahl würfelt, die größer als die Kantengewichte der Zielfelder ist,
	 * wird der zu bewegende Stein nicht als Option gezählt.
	 * @param movingPlayer
	 * @param option
	 * @param diced
	 * @param players
	 * @return boolean
	 */
	private boolean checkJourneyEnd(Player movingPlayer, Vertex option, ArrayList<Player> players, int diced)
	{
		boolean re = true;
		int sub = 0;
		
		if (movingPlayer==players.get(1))
		{
			sub=30;
		}
		else if (movingPlayer==players.get(2))
		{
			sub=20;
		}
		else if (movingPlayer==players.get(3))
		{
			sub=10;
		}
		if ((option.getIndex()==Fields.LAST_RED-sub)&&diced>4)
		{
			re = false;
		}
		else if ((option.getIndex()==Fields.ROUNDABOUT_38-sub)&&diced>5)
		{
			re = false;
		}
		return re;
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Prüfen ob auf den Zielfeldern eine Figur übersprungen werden müsste.
	 * Wird ein Zielfeld geprüft, dass über die Rundreise des jeweiligen Spielers hinausgehen würde,
	 * wird das wie das entfernungsmäßig äquivalente Zielfeld behandelt.
	 * @param movingPlayer
	 * @param option
	 * @param diced
	 * @param players
	 * @return boolean
	 */
	private boolean somethingInTheWay(Player movingPlayer, Vertex option, Vertex target, ArrayList<Player> players)
	{
		boolean re = true;
		int add = 0;
		int add2 = 0;
		int sub = 0;
		
		if (movingPlayer==players.get(1))
		{
			add=4;
			add2=10;
			sub=30;
		}
		else if (movingPlayer==players.get(2))
		{
			add=8;
			add2=20;
			sub=20;
		}
		else if (movingPlayer==players.get(3))
		{
			add=12;
			add2=30;
			sub=10;
		}
		
		if ((option.getIndex()==(Fields.LAST_RED-sub))||(option.getIndex()==(Fields.ROUNDABOUT_38-sub))||(option.getIndex()==(Fields.ROUNDABOUT_37-sub))
				||(option.getIndex()==(Fields.ROUNDABOUT_36-sub))||(option.getIndex()==(Fields.ROUNDABOUT_35-sub)))
		{
			if (((target.getIndex()==Fields.RED_GOAL_2+add)||(target.getIndex()==Fields.ROUNDABOUT_1+add2))
					&&((vertices.get(Fields.RED_GOAL_1+add).getPiece()!=null) // Ist das zu überspingende Feld frei?
					||(vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null))) //Ist das Zielfeld frei?
			{
				re = false;
			}
			else if (((target.getIndex()==Fields.RED_GOAL_3+add)||(target.getIndex()==Fields.ROUNDABOUT_2+add2))
					&&((vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_1+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_3+add).getPiece()!=null)))
			{
				re = false;
			}
			else if (((target.getIndex()==Fields.RED_GOAL_4+add)||(target.getIndex()==Fields.ROUNDABOUT_3+add2))
					&&((vertices.get(Fields.RED_GOAL_3+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_1+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_4+add).getPiece()!=null)))
			{
				re = false;
			}
		}
		else if (option.getIndex()==(Fields.RED_GOAL_1+add))
		{
			if ((target.getIndex()==Fields.RED_GOAL_3+add)
					&&((vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null)))
			{
				re = false;
			}
			else if ((target.getIndex()==Fields.RED_GOAL_4+add)
					&&((vertices.get(Fields.RED_GOAL_3+add).getPiece()!=null)
					||(vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null)))
			{
				re = false;
			}
		}
		else if (option.getIndex()==(Fields.RED_GOAL_2+add))
		{
			if ((target.getIndex()==Fields.RED_GOAL_4+add)&&((vertices.get(Fields.RED_GOAL_3+add).getPiece()!=null)))
			{
				re = false;
			}
		}
	return re;
	}
	
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Schließt gegnerische Zielfelder als Option aus.
	 * @param movingPlayer
	 * @param option
	 * @param players
	 * @return boolean
	 */
	public boolean excludeOpponentsGoal(Player movingPlayer, Vertex target, ArrayList<Player> players)
	{
		boolean re = true;
		if (movingPlayer==players.get(0))
		{
			for(int i = Fields.BLUE_GOAL_1; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(1))
		{
			for(int i = Fields.GREEN_GOAL_1; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
			for(int i = Fields.RED_GOAL_1; i<Fields.BLUE_GOAL_1; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(2))
		{
			for(int i = Fields.YELLOW_GOAL_1; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
			for(int i = Fields.RED_GOAL_1; i<Fields.GREEN_GOAL_1; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(3))
		{
			for(int i = Fields.RED_GOAL_1; i<Fields.YELLOW_GOAL_1; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		
		return re;
	}
	
	/*
	 * Hilfsfunktion für getOptions()
	 * Gibt für Bots nur die Option aus, welche den kürzesten Abstand zum Ziel hat.
	 * Simulation einer sinnvollen Strategie.
	 * @param player
	 * @param option
	 * @param players
	 * @return shortOptions
	 */
	private ArrayList<Integer> sortBotOptions(ArrayList<Integer> options, Player player, ArrayList<Player> players)
	{
		int sizeOptions = options.size();
		int shortestPath = 1000;
		Vertex finalVertex = null;
		ArrayList<Integer> shortOptions = new ArrayList<Integer>();
		
		for (int i = 0; i<sizeOptions; i++)
		{
			Vertex optionVertex = player.getPieces()[options.get(i)].getPosition();
			Vertex betweenVertex = null;
			int way = 0;
			
			for (int k = 0; k<4; k++)
			{
				if (player == players.get(k))
				{
					finalVertex = vertices.get(Fields.RED_GOAL_4+(k*4));
					break;
				}
			}
			
			if ((optionVertex.getIndex()>Fields.LAST_RED)&&(optionVertex.getIndex()<Fields.RED_GOAL_1))
			{
				way = 44; // Vom Start aus sind die Entfernungen gleich und es muss nicht sortiert werden.
				//System.out.println("Der kürzere Weg zum Ziel ist "+way+" von "+optionVertex); //debug
				shortestPath = way;
				shortOptions.add(options.get(i));
			}
			else //Ansonsten wird die Entfernung von der Option aus zum letzten Zielfeld bestimmt.
			{
				while (finalVertex!=optionVertex)
				{
					int length=finalVertex.getPred().size();
					for(int j = 0; j < length; j++)
					{
						if (finalVertex.getPred().get(j).getWeight() == 1)
						{
							betweenVertex = finalVertex.getPred().get(j).getFrom();
							finalVertex = betweenVertex;
							way++;
							break;
						}
					}
				}
				if (way < shortestPath) //Wenn ein kürzerer Weg gefunden ist, wird die bisherige Liste gelöscht und durch die kürzere Option ersetzt.
				{
					//System.out.println(player.getPlayerColor()+" Der kürzere Weg zum Ziel ist "+way+" von "+optionVertex); //debug
					shortestPath = way;
					shortOptions.clear();
					shortOptions.add(options.get(i));
				}
			}
		}
		//System.out.println(options.toString());//Testausgabe
		//System.out.println(shortOptions.toString());//Testausgabe
		return shortOptions;
	}
	
	
	
	
	/*
	 * Die nächsten drei Funktionen sind Hilfsfunktionen für performOption()
	 */
	
	/*
	 * Hilfsfunktion für performOption() und adminMove()
	 * Prüft ob ein Spieler fertig ist
	 * @param player
	 * @param players
	 * @return boolean
	 */
	private boolean checkGoal(Player player, ArrayList<Player> players)
	{
		boolean re = false;
		int add=0;
		
		for (int i = 0; i<4; i++)
		{
			if (player==players.get(i))
			{
				add=i*4;
				break;
			}
		}
		//Dafür müssen alle Zielfelder des Spielenden belegt sein.
		if ((vertices.get(Fields.RED_GOAL_1+add).getPiece()!=null)&&(vertices.get(Fields.RED_GOAL_2+add).getPiece()!=null)
				&&(vertices.get(Fields.RED_GOAL_3+add).getPiece()!=null)&&(vertices.get(Fields.RED_GOAL_4+add).getPiece()!=null))
		{
			re = true;
		}
		
		return re;
	}
	
	/*
	 * Hilfsfunktion für performOption() adminMove()
	 * Prüft, ob ein Spieler nun dreimal Würfeln dar. 
	 * @param specialPlayer
	 * @param players
	 * @return boolean
	 */
	private boolean checkforSuperSpecialCase(Player specialPlayer, ArrayList<Player> players)
	{
		boolean re=false;
		int add=0;
		
		for (int i = 0; i<4; i++)
		{
			if (specialPlayer==players.get(i))
			{
				add=i*4;
			}
		}
		if ((vertices.get(Fields.RED_HOME_1+add).getPiece()!=null)&&(vertices.get(Fields.RED_HOME_2+add).getPiece()!=null)
			&&(vertices.get(Fields.RED_HOME_3+add).getPiece()!=null)&&(vertices.get(Fields.RED_HOME_4+add).getPiece()!=null))
			{
				re=true;
			}
		else
		{
			int count = 0;
			for(int i=0; i<4; i++)
			{
				if (vertices.get(Fields.RED_HOME_1+add+i).getPiece()==null)
				{
					count=count+1;
				}
			}
			for (int j=0; j<count; j++)
			{
				if(vertices.get(Fields.RED_GOAL_4+add-j).getPiece()!=null)
				{
					re=true;
				}
				else
				{
					re=false;
					break;
				}
			}
		}	
		return re;
	}
	
	/*
	 * Hilfsfunktion für performOption() admindMove().
	 * Schickt geschlagene Figuren auf ein freies Heimatfeld.
	 * @param target
	 * @param players
	 * @param newPositionfound
	 * @param targetPieceID
	 */
	private void sendTargetHome(Vertex target, ArrayList<Player> players, boolean newPositionfound, int targetPieceID)
	{
		int add=0;
		int playerIndex=0;
		
		for (int i = 0; i<4; i++)
		{
			if (target.getPiece().getPlayer()==players.get(i))
			{
				add=i*4;
				playerIndex=i;
				break;
			}
		}
		
		for (int i = 0; i<4; i++)
		{
			if (newPositionfound == false)
			{
				if (vertices.get(Fields.RED_HOME_1+add+i).getPiece()==null)
				{
					vertices.get(Fields.RED_HOME_1+add+i).setPiece(target.getPiece());
					players.get(playerIndex).getPieces()[targetPieceID].setPosition(vertices.get(Fields.RED_HOME_1+add+i));
					newPositionfound = true;
				}
			}	
		}
	}
	
	/*
	 * Hilfsfunktion für adminMove()
	 * Prüft zwei Bedingungen:
	 * 1. Keine eigene Figur darf geschlagen werden
	 * 2. Gegenerische Heimatfelder dürfen nicht betreten werden.
	 * @param player
	 * @param target
	 * @param players
	 * @return boolean
	 */
	boolean checkAdminTarget(Player player, Vertex target, ArrayList<Player> players)
	{
		boolean re=true;
		
		//Ausschließen, dass eigene Figuren geschlagen werden
		if (target.getPiece()!=null)
		{
			if (target.getPiece().getPlayer()==player)
			{
				re=false;
			}
		}
		// gegnerische Heimatfelder ausschließen
		if (re==true)
		{
			if ((target.getIndex()>Fields.LAST_RED)&&(target.getIndex()<Fields.RED_GOAL_1))
			{	
				for(int i=0; i<4; i++)
				{
					int add=(1+i)*4;
					if (player==players.get(i))
					{
						for (int j=Fields.ROUNDABOUT_36+add; j<Fields.RED_HOME_1+add; j++)
						{
							if (target.getIndex()==j)
							{
								re=true;
								break;
							}
							else 
							{
								re = false;
							}
						}
					}
				}
			}
		}
		return re;
	}
	
	//
	// INTERNE METHODEN END
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			
//ENDE DER KLASSENDEFINITION
}
