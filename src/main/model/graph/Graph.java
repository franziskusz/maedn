package main.model.graph;

import main.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph
{
	private Map <Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	private List<Edge> edges = new ArrayList<Edge>();

	/**
	 * Gibt die Möglichleiten für den Spieler mit gewürfelter Zahl zurück (inform der pieceIDs)
	 * Bei keinen Möglichkeiten return null;
	 *
	 * @param player
	 * @param diced
	 * @return
	 */
	public ArrayList<Integer> getOptions(Player player, int diced) {
		return null;
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
	 * Spieler 4: Knoten 67-71
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
	 */
	public Graph initGraph()
	{
		//Initialisierung der insgesamt 72 Felder
		int numberFields=72;
		for (int i=0; i<numberFields; i++)
		{
			Vertex v = new Vertex(this);
			vertices.put(v.getIndex(),v);
		}
		
		//Initialisierung der Rundreise
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
		
		//Initialisierung der Häuser
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
		
		//Initialisierung der Zielfelder
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
		return null;
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
}
