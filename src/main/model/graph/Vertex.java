package main.model.graph;

import java.util.ArrayList;
import java.util.List;

import main.model.player.Piece;


public class Vertex
{

	private static int indexCount=0;
	private int index = indexCount++;
	private List<Edge> predecessors = new ArrayList<>();
	private List<Edge> successors = new ArrayList<>();
	private Graph graphPtr;
	private int coordinateX;
	private int coordinateY;
	private Piece piece;
	
	
	public void printPiece() //debug
	{
		if (piece==null)
		{
			System.out.println("Kein Spielstein");
		}
		else
		{
			System.out.println("Spielstein vorhanden");
		}
	}
	
	public Piece getPiece()
	{
		return piece;
	}

	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}

	public int getCoordinateX()
	{
		return coordinateX;
	}
	
	public void setCoordinateX(int coordinateX)
	{
		this.coordinateX = coordinateX;
	}
	
	public int getCoordinateY()
	{
		return coordinateY;
	}
	
	public void setCoordinateY(int coordniateY)
	{
		this.coordinateY = coordniateY;
	}
	
	public Vertex(Graph graphPtr)
	{
		this.graphPtr=graphPtr;
	}
	public int getIndex()
	{
		return index;
	}
	
	public List<Edge> getPred()
	{
		return predecessors;
	}
	
	public List<Edge> getSucc()
	{
		return successors;
	}

	public static void resetIndexCount() {
		Vertex.indexCount = 0;
	}

	@Override
	public String toString()
	{
		return "Vertex ("+index+")";
	}
}
