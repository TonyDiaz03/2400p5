import java.util.Queue;
import java.util.Stack;

public interface GraphAlgorithmsInterface<T>
{
 	/** Task: Finds the least-cost path between two given vertices.
    		@param begin  an object that labels the path origin vertex
     		@param end    an object that labels the path destination vertex
     		@param path   a stack of labels that is empty initially;
		at the completion of the method, this stack contains
              the labels of the vertices along the cheapest path;     
     		@return the cost of the cheapest path */
  	public double getCheapestPath(T begin, T end, Stack<T> path);
}//end GraphAlgorithmsInterface