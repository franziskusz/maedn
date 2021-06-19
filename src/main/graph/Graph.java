package main.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph
{
	private Map <Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	private List<Edge> edges = new ArrayList<Edge>();
	
	
	//TODO ändern in Initialisierung des  Spielfeldes
	/*
	 * kein Scanner nötig
	 * Auf der Basis des MÄDN Spielbretts wird ein Graph angelegt
	 * Alle Felder haben einen Nachfolger und Vorgänger
	 * Ausnahme 1: Das Startfeld jedes Spielers hat 5 Vorgänger (Rundreise+Haus)
	 * Ausnahme 2: Das finale Feld hat zwei Nachfolger (Startfeld+Zielfeld 1)
	 * 
	 * Es gibt 40 Rundreise Felder
	 * 4x4 Haus Felder
	 * 4x4 Zielfelder
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
			if (i<39)
			{
				to = i+1;
			}
			else // das letzte Feld wird mit dem ersten verbunden
			{
				to = i-39;
			}
			w = 0;
			Edge e = new Edge(this, from, to, w);
			edges.add(e);
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
			w = 0;
			Edge e = new Edge(this, from, to, w);
			edges.add(e);
			
			
		}
		
		//Initialisierung der Zielfelder
		for (int i=56; i<72; i++)
		{
			int from, to, w;
			to = i;
			if (i<=59) //Zielfelder Spieler 1
			{
				from = 39;
			}
			else if (i<=63) //Zielfelder Spieler 2
			{
				from = 9;
			}
			else if (i<=66) //Zielfelder Spieler 3
			{
				from = 19;
			}
			else  //Zielfelder Spieler 4
			{
				from = 29;
			}
			w = 0;
			Edge e = new Edge(this, from, to, w);
			edges.add(e);
			
		}
		return null;
	}

	
	public Map<Integer, Vertex> getVertices()
	{
		return vertices;
	}
	
	public void testPrint()
	{
		System.out.println(vertices);
		System.out.println(edges);
	}
}
