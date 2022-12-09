import java.util.Iterator;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.HashMap;

public class DirectedGraph<T> implements GraphInterface<T>
{
	private HashMap<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public DirectedGraph()
	{
		vertices = new HashMap<>();
		edgeCount = 0;
	}

	public boolean addVertex(T vertexLabel)
	{
		VertexInterface<T> duplicate = vertices.put(vertexLabel, new Vertex(vertexLabel));
		return duplicate == null; // was add to dictionary successful?
	}

	public boolean addEdge(T begin, T end, double edgeWeight)
	{
	  boolean result = false;
	  
	  VertexInterface<T> beginVertex = vertices.get(begin);
	  VertexInterface<T> endVertex = vertices.get(end);
	  
	  if ( (beginVertex != null) && (endVertex != null) )
	    result = beginVertex.connect(endVertex, edgeWeight);
	    
	  if (result)
	    edgeCount++;
	    
	  return result;
	} // end addEdge

	public boolean addEdge(T begin, T end)
	{
	  return addEdge(begin, end, 0);
	} //end addEdge

	public boolean hasEdge(T begin, T end)
	{
	  boolean found = false;
	  
	  VertexInterface<T> beginVertex = vertices.get(begin);
	  VertexInterface<T> endVertex = vertices.get(end);
	  
	  if ( (beginVertex != null) && (endVertex != null) )
	  {
	    Iterator<VertexInterface<T>> neighbors = 
	                                 beginVertex.getNeighborIterator();
	    while (!found && neighbors.hasNext())
	    {
	      VertexInterface<T> nextNeighbor = neighbors.next();
	      if (endVertex.equals(nextNeighbor))
	        found = true;
	    } // end while
	  } // end if
	  
	  return found;
	} //end hasEdge

	public boolean isEmpty()
	{
	  return vertices.isEmpty();
	} //end isEmpty

	public void clear()
	{
	  vertices.clear();
	  edgeCount = 0;
	} //end clear

	public int getNumberOfVertices()
	{
	  return vertices.size();
	} //end getNumberOfVertices

	public int getNumberOfEdges()
	{
	  return edgeCount;
	} //end getNumberOfEdges

	protected void resetVertices()
	{
	  Iterator<VertexInterface<T>> vertexIterator = vertices.values().iterator();
	  while (vertexIterator.hasNext())
	  {
	    VertexInterface<T> nextVertex = vertexIterator.next();
	    nextVertex.unvisit();
	    nextVertex.setCost(0);
	    nextVertex.setPredecessor(null);
	  } // end while
	} // end resetVertices

	public double getCheapestPath(T begin, T end, Stack<T> path) // STUDENT EXERCISE
	{
		resetVertices();
		boolean done = false;

		// use EntryPQ instead of Vertex because multiple entries contain 
		// the same vertex but different costs - cost of path to vertex is EntryPQ's priority value
		PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<>();
		
		VertexInterface<T> originVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);

		priorityQueue.add(new EntryPQ(originVertex, 0, null));
	
		while (!done && !priorityQueue.isEmpty())
		{
			EntryPQ frontEntry = priorityQueue.remove();
			VertexInterface<T> frontVertex = frontEntry.getVertex();
			
			if (!frontVertex.isVisited())
			{
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());
				
				if (frontVertex.equals(endVertex))
					done = true;
				else 
				{
					Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
					Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
					while (neighbors.hasNext())
					{
						VertexInterface<T> nextNeighbor = neighbors.next();
						Double weightOfEdgeToNeighbor = edgeWeights.next();
						
						if (!nextNeighbor.isVisited())
						{
							double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
							priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						} // end if
					} // end while
				} // end if
			} // end if
		} // end while

		// traversal ends, construct cheapest path
		double pathCost = endVertex.getCost();
		path.push(endVertex.getLabel());
		
		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor())
		{
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		} // end while

		return pathCost;
	} // end getCheapestPath

	private class EntryPQ implements Comparable<EntryPQ>
	{
		private VertexInterface<T> vertex; 	
		private VertexInterface<T> previousVertex; 
		private double cost; // cost to nextVertex
		
		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
		{
			this.vertex = vertex;
			this.cost = cost;
			this.previousVertex = previousVertex;
			
		} // end constructor
		
		public VertexInterface<T> getVertex()
		{
			return vertex;
		} // end getVertex
		
		public VertexInterface<T> getPredecessor()
		{
			return previousVertex;
		} // end getPredecessor

		public double getCost()
		{
			return cost;
		} // end getCost
		
		public int compareTo(EntryPQ otherEntry)
		{
			return 0;
		} // end compareTo
		
		
	} // end EntryPQ
}