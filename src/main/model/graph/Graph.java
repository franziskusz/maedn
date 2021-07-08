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
		getOptions(players.get(1), 6, players); //Debug
		//performOption(players.get(1), vertices.get(47), 6, players); //debug
	}
	
	/**
	 * 
	 * führt für den gwünschten Spielstein eine Option aus 
	 *
	 * @param player
	 * @param diced
	 * @param Vertex/Piece/PieceID
	 * @return
	 */
	
	public void performOption(Player player, Vertex option, int diced, ArrayList<Player> players)
	{
		Vertex target = getTarget(player, option, diced, players);
		int targetIndex = target.getIndex();
		
		Piece movingPiece = option.getPiece();
		//int movingPieceID=movingPiece.getId();
		
		Piece targetPiece = target.getPiece();
		
		
		
		//TODO: Anhand des Zielknotens ermitteln, ob Laufen und Schlagen ausgeführt wird
		//TODO: Laufen oder Schlagen ausführen
		if (target.getPiece()==null) // Laufen
		{
			vertices.get(targetIndex).setPiece(movingPiece);
			vertices.get(targetIndex).getPiece().setPosition(target);
			option.setPiece(null);
		}
		
		else //Schlagen
		{
			int targetPieceID=targetPiece.getId();
			Player targetPlayer = target.getPiece().getPlayer();
			boolean newPositionfound = false;
			
			//geschlagene Pieces gehen nach Hause
			if (target.getPiece().getPlayer()==players.get(0))
			{
				for (int i = 0; i<4; i++)
				{
					if (newPositionfound == false)
					{
						if (vertices.get(40+i).getPiece()==null)
						{
							vertices.get(40+i).setPiece(target.getPiece());
							players.get(0).getPieces()[targetPieceID].setPosition(vertices.get(40+i));
							newPositionfound = true;
						}
					}	
				}
			}
			else if (target.getPiece().getPlayer()==players.get(1))
			{
				for (int i = 0; i<4; i++)
				{
					if (newPositionfound == false)
					{
						if (vertices.get(44+i).getPiece()==null)
						{
							vertices.get(44+i).setPiece(target.getPiece());
							players.get(1).getPieces()[targetPieceID].setPosition(vertices.get(44+i));
							newPositionfound = true;
						}
					}
					
				}
			}
			else if (target.getPiece().getPlayer()==players.get(2))
			{
				for (int i = 0; i<4; i++)
				{
					if (newPositionfound == false)
					{
						if (vertices.get(48+i).getPiece()==null)
						{
							vertices.get(48+i).setPiece(target.getPiece());
							players.get(2).getPieces()[targetPieceID].setPosition(vertices.get(48+i));
							newPositionfound = true;
						}
					}
				}
			}
			else if (target.getPiece().getPlayer()==players.get(3))
			{
				for (int i = 0; i<4; i++)
				{
					if (newPositionfound == false)
					{
						if (vertices.get(52+i).getPiece()==null)
						{
							vertices.get(52+i).setPiece(target.getPiece());
							players.get(3).getPieces()[targetPieceID].setPosition(vertices.get(52+i));
							newPositionfound = true;
						}
					}
				}
			}
			vertices.get(targetIndex).setPiece(movingPiece);
			vertices.get(targetIndex).getPiece().setPosition(target);
			option.setPiece(null);
			
		}
		
		
		//TODO:   - Wurde Geschlagen?
		/*
        - Muss geschlagener Spieler THREE TIMES bekommen (gar keine Spielfiguren mehr
                oder restlos aufgerückt)
        - Ist der Spieler restlos aufgerückt und hast sonst keine Spielsteine auf dem Feld?
            - Dann bekommt er THREE TIMES (evt noch nicht setzen, sondern nur als FLAG)
            
            
           - in performOption() eine Methode im Graph aufrufen, die überprüft, ob der
    	mitgegebene Spieler (playerTrun) gewonnen hat
    	- Falls ja -> GameState END setzen
        */	
	}
	
	
	/*
	 * Die nächsten x Funktionen sind Hilfsfunktionen für performOption()
	 */
	
	private void sendTargetHome(Vertex target, ArrayList<Player> players, boolean newPositionfound)
	{
		
	}
	
	private Vertex getTarget(Player player, Vertex option, int diced, ArrayList<Player> players)
	{
		Vertex target=null;
		
		//Spieler 1
		if (player==players.get(0))
		{
			if(option.getIndex()<34)
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(56);
				}
			}
			else if (option.getIndex()==35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(56+diced-5);
				}
			}
			else if (option.getIndex()==36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(56+diced-4);
				}
			}
			else if (option.getIndex()==37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(56+diced-3);
				}
			}
			else if (option.getIndex()==38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(56+diced-2);
				}
			}
			else if (option.getIndex()==39)
			{
					target=vertices.get(56+diced-1);
			}
			else if ((option.getIndex()==40)||(option.getIndex()==41)||(option.getIndex()==42)||(option.getIndex()==43))
			{
					target=vertices.get(0);
			}
			else if ((option.getIndex()==56)||(option.getIndex()==57)||(option.getIndex()==58))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		//Spieler 2
		if (player==players.get(1))
		{
			if(((option.getIndex()<34)&&(option.getIndex()>9))||(option.getIndex()<4))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0);
				}
			}
			else if (option.getIndex()==35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-5);
				}
			}
			else if (option.getIndex()==36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-4);
				}
			}
			else if (option.getIndex()==37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-3);
				}
			}
			else if (option.getIndex()==38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-2);
				}
			}
			else if (option.getIndex()==39)
			{
					target=vertices.get(0+diced-1);
			}
			
			else if (option.getIndex()==4)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(60);
				}
			}
			else if (option.getIndex()==5)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(60+diced-5);
				}
			}
			else if (option.getIndex()==6)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(60+diced-4);
				}
			}
			else if (option.getIndex()==7)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(60+diced-3);
				}
			}
			else if (option.getIndex()==8)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(60+diced-2);
				}
			}
			else if (option.getIndex()==9)
			{
					target=vertices.get(60+diced-1);
			}
			else if ((option.getIndex()==44)||(option.getIndex()==45)||(option.getIndex()==46)||(option.getIndex()==47))
			{
					target=vertices.get(10);
			}
			else if ((option.getIndex()==60)||(option.getIndex()==61)||(option.getIndex()==62))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		
		//Spieler 3
		if (player==players.get(2))
		{
			if(((option.getIndex()<34)&&(option.getIndex()>19))||(option.getIndex()<14))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0);
				}
			}
			else if (option.getIndex()==35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-5);
				}
			}
			else if (option.getIndex()==36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-4);
				}
			}
			else if (option.getIndex()==37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-3);
				}
			}
			else if (option.getIndex()==38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-2);
				}
			}
			else if (option.getIndex()==39)
			{
					target=vertices.get(0+diced-1);
			}
			
			else if (option.getIndex()==14)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(64);
				}
			}
			else if (option.getIndex()==15)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(64+diced-5);
				}
			}
			else if (option.getIndex()==16)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(64+diced-4);
				}
			}
			else if (option.getIndex()==17)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(64+diced-3);
				}
			}
			else if (option.getIndex()==18)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(64+diced-2);
				}
			}
			else if (option.getIndex()==19)
			{
					target=vertices.get(64+diced-1);
			}
			else if ((option.getIndex()==48)||(option.getIndex()==49)||(option.getIndex()==50)||(option.getIndex()==51))
			{
					target=vertices.get(20);
			}
			else if ((option.getIndex()==64)||(option.getIndex()==65)||(option.getIndex()==66))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}
		
		//Spieler 4
		if (player==players.get(3))
		{
			if(((option.getIndex()<34)&&(option.getIndex()>29))||(option.getIndex()<24))
			{
				target=vertices.get(option.getIndex()+diced);
			}
			else if (option.getIndex()==34)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0);
				}
			}
			else if (option.getIndex()==35)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-5);
				}
			}
			else if (option.getIndex()==36)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-4);
				}
			}
			else if (option.getIndex()==37)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-3);
				}
			}
			else if (option.getIndex()==38)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(0+diced-2);
				}
			}
			else if (option.getIndex()==39)
			{
					target=vertices.get(0+diced-1);
			}
			
			else if (option.getIndex()==24)
			{
				if(diced < 6)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(68);
				}
			}
			else if (option.getIndex()==25)
			{
				if(diced < 5)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(68+diced-5);
				}
			}
			else if (option.getIndex()==26)
			{
				if(diced < 4)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(68+diced-4);
				}
			}
			else if (option.getIndex()==27)
			{
				if(diced < 3)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(68+diced-3);
				}
			}
			else if (option.getIndex()==28)
			{
				if(diced < 2)
				{
					target=vertices.get(option.getIndex()+diced);
				}
				else
				{
					target=vertices.get(68+diced-2);
				}
			}
			else if (option.getIndex()==29)
			{
					target=vertices.get(68+diced-1);
			}
			else if ((option.getIndex()==52)||(option.getIndex()==53)||(option.getIndex()==54)||(option.getIndex()==55))
			{
					target=vertices.get(30);
			}
			else if ((option.getIndex()==68)||(option.getIndex()==69)||(option.getIndex()==70))
			{
				target=vertices.get(option.getIndex()+diced);
			}
		}		
		return target;
	}
	
	
	
	
	/**
	 * Gibt die Möglichleiten für den Spieler mit gewürfelter Zahl zurück (inform der pieceIDs)
	 * Bei keinen Möglichkeiten return null;
	 *
	 * @param player
	 * @param diced
	 * @param players
	 * @return ArrayList
	 */
	
	//erster Ansatz sollte funktionieren
	//Prüft ob den Zielposition ein eigener Stein steht
	//Prüft ob Rundreise für den Spieler bereits beendet ist
	//Prüft ob im Zielfeld eine eigene Figur übersprungen werden müsste
	//Prüft ob die Figur in ein fremdes Zielfeld einlaufen würde
	
	public ArrayList<Integer> getOptions(Player player, int diced, ArrayList<Player> players)
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
				if((optionVertices[j].getSucc().get(k).getWeight()==diced)
						&&(checkTargetOccupation(player, optionVertices[j].getSucc().get(k).getTo().getPiece()))
						&&(checkJourneyEnd(player, optionVertices[j], players, diced))
						&&(somethingInTheWay(player, optionVertices[j], optionVertices[j].getSucc().get(k).getTo(), players))
						&&(excludeOpponentsGoal(player, optionVertices[j].getSucc().get(k).getTo(), players)))
				{
					System.out.println(optionVertices[j]+" Successors: "+optionVertices[j].getSucc()); //debug
					//options.add(optionVertices[j].getSucc().get(k).getTo().getIndex());
					System.out.println(options.toString()); //debug
				
					options.add(optionVertices[j].getPiece().getId());
					
					System.out.println(options.toString()); //debug	
				}					
			}
		}			
	
		System.out.println(options.toString());//Testausgabe	
		return options;	
	}
	
	
	/*
	 * Die nächsten vier Funktionen sind Hilfsfunktionen für checkOptions()
	 */
	
	//Wenn auf dem Zielfeld eine Figur des aktiven Spielers steht, wird der zu bewegende Stein nicht als Option gezählt
	private boolean checkTargetOccupation(Player movingPlayer, Piece targetPiece)
	{
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
	
	//Wenn der Würfelnde Spieler auf einem der letzten beiden Feldern der Rundreise steht
	// aber eine Zahl würfelt, die größer als die Kantengewichte der Zielfelder ist
	// wird der zu bewegende Stein nicht als Option gezählt.
	private boolean checkJourneyEnd(Player movingPlayer, Vertex option, ArrayList<Player> players, int diced)
	{
		boolean re = true;
		if (movingPlayer==players.get(0))
		{
			if ((option.getIndex()==39)&&diced>4)
			{
				re = false;
			}
			else if ((option.getIndex()==38)&&diced>5)
			{
				re = false;
			}	
		}
		else if (movingPlayer==players.get(1))
		{
			if ((option.getIndex()==9)&&diced>4)
			{
				re = false;
			}
			else if ((option.getIndex()==8)&&diced>5)
			{
				re = false;
			}	
		}
		else if (movingPlayer==players.get(2))
		{
			if ((option.getIndex()==19)&&diced>4)
			{
				re = false;
			}
			else if ((option.getIndex()==18)&&diced>5)
			{
				re = false;
			}	
		}
		else if (movingPlayer==players.get(3))
		{
			if ((option.getIndex()==29)&&diced>4)
			{
				re = false;
			}
			else if ((option.getIndex()==28)&&diced>5)
			{
				re = false;
			}	
		}
		return re;
	}
	
	//Prüfen ob auf den Zielfeldern eine Figur übersprungen werden müsste
	//Wird ein Zielfeld geprüft, dass über die Rundreise des jeweiligen Spielers hinausgehen würde,
	//wird das wie das Entfernungsmäßig äquivalente Zielfeld behandelt
	private boolean somethingInTheWay(Player movingPlayer, Vertex option, Vertex target, ArrayList<Player> players)
	{
		boolean re = true;
		if (movingPlayer==players.get(0))
		{
			if ((option.getIndex()==(39))||(option.getIndex()==(38))||(option.getIndex()==(37))
					||(option.getIndex()==(36))||(option.getIndex()==(35)))
			{
				if (((target.getIndex()==57)||(target.getIndex()==1))
						&&((vertices.get(56).getPiece()!=null) // Ist das zu überspingende Feld frei?
						||(vertices.get(57).getPiece()!=null))) //Ist das Zielfeld frei?
				{
					re = false;
				}
				else if (((target.getIndex()==58)||(target.getIndex()==2))
						&&((vertices.get(57).getPiece()!=null)
						||(vertices.get(56).getPiece()!=null)
						||(vertices.get(58).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==59)||(target.getIndex()==3))
						&&((vertices.get(58).getPiece()!=null)
						||(vertices.get(57).getPiece()!=null)
						||(vertices.get(56).getPiece()!=null)
						||(vertices.get(59).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(56))
			{
				if ((target.getIndex()==58)
						&&((vertices.get(57).getPiece()!=null)))
				{
					re = false;
				}
				else if ((target.getIndex()==59)
						&&((vertices.get(58).getPiece()!=null)
						||(vertices.get(57).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(57))
			{
				if ((target.getIndex()==59)&&((vertices.get(58).getPiece()!=null)))
				{
					re = false;
				}
			}	
		}
		if (movingPlayer==players.get(1))
		{
			if ((option.getIndex()==(9))||(option.getIndex()==(8))||(option.getIndex()==(7))
					||(option.getIndex()==(6))||(option.getIndex()==(5)))
			{
				if (((target.getIndex()==61)||(target.getIndex()==11))
						&&((vertices.get(60).getPiece()!=null)
						||(vertices.get(61).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==62)||(target.getIndex()==12))
						&&((vertices.get(61).getPiece()!=null)
						||(vertices.get(60).getPiece()!=null)
						||(vertices.get(62).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==63)||(target.getIndex()==13))
						&&((vertices.get(62).getPiece()!=null)
						||(vertices.get(61).getPiece()!=null)
						||(vertices.get(60).getPiece()!=null)
						||(vertices.get(63).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(60))
			{
				if ((target.getIndex()==62)
						&&((vertices.get(61).getPiece()!=null)))
				{
					re = false;
				}
				else if ((target.getIndex()==63)
						&&((vertices.get(62).getPiece()!=null)
						||(vertices.get(61).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(61))
			{
				if ((target.getIndex()==63)&&((vertices.get(62).getPiece()!=null)))
				{
					re = false;
				}
			}	
		}
		if (movingPlayer==players.get(2))
		{
			if ((option.getIndex()==(19))||(option.getIndex()==(18))||(option.getIndex()==(17))
					||(option.getIndex()==(16))||(option.getIndex()==(15)))
			{
				if (((target.getIndex()==65)||(target.getIndex()==21))
						&&((vertices.get(64).getPiece()!=null)
						||(vertices.get(65).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==66)||(target.getIndex()==22))
						&&((vertices.get(65).getPiece()!=null)
						||(vertices.get(64).getPiece()!=null)
						||(vertices.get(66).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==67)||(target.getIndex()==23))
						&&((vertices.get(66).getPiece()!=null)
						||(vertices.get(65).getPiece()!=null)
						||(vertices.get(64).getPiece()!=null)
						||(vertices.get(67).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(64))
			{
				if ((target.getIndex()==66)
						&&((vertices.get(65).getPiece()!=null)))
				{
					re = false;
				}
				else if ((target.getIndex()==67)
						&&((vertices.get(66).getPiece()!=null)
						||(vertices.get(65).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(65))
			{
				if ((target.getIndex()==67)&&((vertices.get(66).getPiece()!=null)))
				{
					re = false;
				}
			}	
		}
		if (movingPlayer==players.get(3))
		{
			if ((option.getIndex()==(29))||(option.getIndex()==(28))||(option.getIndex()==(27))
					||(option.getIndex()==(26))||(option.getIndex()==(25)))
			{
				if (((target.getIndex()==69)||(target.getIndex()==31))
						&&((vertices.get(68).getPiece()!=null)
						||(vertices.get(69).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==70)||(target.getIndex()==32))
						&&((vertices.get(69).getPiece()!=null)
						||(vertices.get(68).getPiece()!=null)
						||(vertices.get(70).getPiece()!=null)))
				{
					re = false;
				}
				else if (((target.getIndex()==71)||(target.getIndex()==33))
						&&((vertices.get(70).getPiece()!=null)
						||(vertices.get(69).getPiece()!=null)
						||(vertices.get(68).getPiece()!=null)
						||(vertices.get(71).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(68))
			{
				if ((target.getIndex()==70)
						&&((vertices.get(69).getPiece()!=null)))
				{
					re = false;
				}
				else if ((target.getIndex()==71)
						&&((vertices.get(70).getPiece()!=null)
						||(vertices.get(69).getPiece()!=null)))
				{
					re = false;
				}
			}
			else if (option.getIndex()==(69))
			{
				if ((target.getIndex()==71)&&((vertices.get(70).getPiece()!=null)))
				{
					re = false;
				}
			}	
		}
		return re;
	}
	
	//Schließt gegnerische Zielfelder als Option aus
	public boolean excludeOpponentsGoal(Player movingPlayer, Vertex target, ArrayList<Player> players)
	{
		boolean re = true;
		if (movingPlayer==players.get(0))
		{
			for(int i = 60; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(1))
		{
			for(int i = 64; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
			for(int i = 56; i<60; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(2))
		{
			for(int i = 68; i<72; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
			for(int i = 56; i<64; i++)
			{
				if (target==vertices.get(i))
				{
					re = false;
				}
			}
		}
		else if (movingPlayer==players.get(3))
		{
			for(int i = 56; i<68; i++)
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
			
			if (i<34)
			{
				for (int j=1; j<=6; j++)
				{
					to = i+j; // Kanten entsprechend der möglichen Würfelergebnisse
					w=j; // Gewicht gleich der Entfernung
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
			}
			
			// Zielfelder ab Knoten 34 sind etwas komplizierter, da die vorige Schleife bei den Heimatfeldern landen würde
			else
			{
				if (i==34)
				{
					for (int j=5; j>0; j--)
					{	
						to=i+j;
						w=j;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					to=0;
					w=6;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);
				}
				
				else if (i==35)
				{
					for (int j=4; j>0; j--)
					{	
						to=i+j;
						w=j;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k=0; k<2; k++)
					{
						to=k;
						w=k+5;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==36)
				{
					for (int j=3; j>0; j--)
					{	
						to=i+j;
						w=j;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k=0; k<3; k++)
					{
						to=k;
						w=k+4;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
				}
				
				else if (i==37)
				{
					for (int j=2; j>0; j--)
					{	
						to=i+j;
						w=j;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					for (int k=0; k<4; k++)
					{
						to=k;
						w=k+3;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}	
				}
				else if (i==38)
				{
					for (int k=0; k<5; k++)
					{
						to=k;
						w=k+2;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}
					
					to=i+1;
					w=1;
					Edge e = new Edge(this, from, to, w);
					edges.add(e);	
				}
				
				else // i==39
				{	
					for (int k=0; k<6; k++)
					{
						to=k;
						w=k+1;
						Edge e = new Edge(this, from, to, w);
						edges.add(e);
					}	
				}
			}	

			//Falsche Implementierung
			/*
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
			*/
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
