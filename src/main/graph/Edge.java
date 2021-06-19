package main.graph;

public class Edge
{

	private Vertex from;
	private Vertex to;
	private int weight;
	private  Graph graphPt;
	
	public Edge(Graph g, int from, int to, int weight)
	{
		this.graphPt = graphPt;
		this.weight=weight;
		
		this.from = graphPt.getVertices().get(from);
		this.from.getSucc().add(this);
		this.to = graphPt.getVertices().get(to);
		this.to.getPred().add(this);
	}
	
	@Override
	public String toString()
	{
		return "\n Kante von"+from + " | nach: "+to;
	}
}