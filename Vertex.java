/**
 * Class to represent a vertex of a graph
 * 
 *
 */

/*
 * Group – G13 
 * Diksha Sharma (dxs134530) and Sharol Clerit Pereira (scp140130)
 * Last Modified: 2/21/2016
 */

import java.util.*;

public class Vertex 
{
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public int degree;
    public int iIndex;
    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) 
    {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>();   /* only for directed graphs */
		degree = -1;
		iIndex = n; //Assuming no gaps in the vertex numbers
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() 
    {
    	return Integer.toString(name);
    }
}
