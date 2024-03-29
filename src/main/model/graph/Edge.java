package main.model.graph;
/*
 * Hilfsklasse für den Graphen: Verwaltung der Kanten. 
 */
public class Edge
{

	private Vertex from;
	private Vertex to;
	private int weight;
	private  Graph graphPt;
	
	public Edge(Graph graphPt, int from, int to, int weight)
	{
		this.graphPt = graphPt;
		this.weight=weight;
		
		this.from = graphPt.getVertices().get(from);
		this.from.getSucc().add(this);
		this.to = graphPt.getVertices().get(to);
		this.to.getPred().add(this);
	}

	public Vertex getFrom() 
	{
		return from;
	}

	public Vertex getTo() 
	{
		return to;
	}
	
	public int getWeight()
	{
		return weight;
	}

	
	@Override
	public String toString() {
		return " [from: " + this.from + ", to: " + this.to + ", weight = "+this.weight+"]";
	}

	
}