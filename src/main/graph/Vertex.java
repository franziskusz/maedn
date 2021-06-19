package main.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Vertex
{

	private static int indexCount =0;
	private int index = indexCount++;
	private List<Edge> predecessors = new ArrayList<>();
	private List<Edge> successors = new ArrayList<>();
	private Graph graphPtr;
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
		return predecessors;
	}
	
	@Override
	public String toString()
	{
		return "Vertex ("+index+")";
	}
}
