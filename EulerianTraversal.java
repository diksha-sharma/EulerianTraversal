/*
 * Group – G13 
 * Diksha Sharma (dxs134530) and Sharol Clerit Pereira (scp140130)
 * Last Modified: 2/21/2016
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class EulerianTraversal 
{
	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner in;
		if (args.length > 0) 
		{
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} 
		else 
		{
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in, false);
		List<Edge> tour = new ArrayList<Edge>();
		timer();
		tour = findEulerTour(g);
		timer();
	}
		
	//Returns Euler path or tour
	public static List<Edge> findEulerTour(Graph g)
	{
		int iOddDegreeCount = 0;
		Vertex v = g.verts.get(1);
		
		for(int i=1; i< g.verts.size(); i++)
		{
			if(g.verts.get(i).degree%2 != 0)
	    	{
				iOddDegreeCount++;	
				if(iOddDegreeCount == 1)
				{
					v = g.verts.get(i);
				}
				else if(iOddDegreeCount == 2)
				{
					if(v.name > g.verts.get(i).name)
					{
						v = g.verts.get(i);
					}
				}
				else if(iOddDegreeCount > 2)
				{
					System.out.println("Graph is not Eulerian");
					return null;
				}
	    	}	
		}
	
		List<Edge> eTour = new ArrayList<Edge>();
		int iVIndex1 = 1;
		int iVIndex2 = 0;
		int iVertexCount = g.numNodes;
		Vertex vCurrent;
		Vertex vOther;
		boolean bFound = false;
		while(verifyTour(g, eTour) != true)
		{
			//if the next index to visit is in non decreasing order of indexes
			if(iVIndex1 < iVertexCount)
			{
				iVIndex2 = iVIndex1 + 1;
			}
			else if(iVIndex1 == iVertexCount) //Last Node then
			{
				iVIndex2 = 1; //Start from beginning again					
			}
			bFound = false;
			while(bFound == false)
			{
				for(Edge e: g.verts.get(iVIndex1).Adj)
				{			
					vCurrent = g.verts.get(iVIndex1);
					vOther = e.otherEnd(vCurrent);					
					if(vOther.name == iVIndex2 && e.seen == true) 
					{
						//There is an edge to the next index vertex but has been visited already
						if(iVIndex2 <= iVertexCount)
						{
							iVIndex2++;//Look for next higher index
						}
						else
						{
							iVIndex2 = 1; //Start from beginning vertex
						}						
						bFound = false;
						break;					
					}
					else if(vOther.name == iVIndex2 && e.seen == false)
					{
						eTour.add(e);
						e.seen = true;
						System.out.println(e.toString());
						iVIndex1 = vOther.name;
						bFound = true;
						break;
					}
					else if(e.seen == false)
					{
						Vertex vNext;
						boolean bNoEdge = true;
						for(Edge e3: g.verts.get(iVIndex1).Adj)
						{
							vNext = e3.otherEnd(vCurrent);
							if (vNext.name != iVIndex2)
							{
								continue;
							}
							else
							{
								bNoEdge = false;
							}
						}
						if(bNoEdge == true)
						{
							if(iVIndex2 <= iVertexCount)
							{
								iVIndex2++;//Look for next higher index
							}
							else
							{
								iVIndex2 = 1; //Start from beginning vertex
							}
						}
					}
				}//End of for(Edge e: g.verts.get(iVIndex1).Adj)
			}//End of while(bFound == false)	
		}//End of while loop		
		return eTour;
	}
	
	public static boolean verifyTour(Graph g, List<Edge> tour)  // verify tour is a valid Euler tour/path
	{
		
		//Check for all edges have been visited
		for(int i=1; i< g.verts.size(); i++)
		{
			for(Edge e: g.verts.get(i).Adj)
			{
				if(e.seen == false)
				{
					return false;
				}
			}
		}
		//Check for adjacent vertices in the tour
		for(int i=0; i< tour.size()-1; i++)
		{
			if((tour.get(i).From == tour.get(i+1).From) || (tour.get(i).From == tour.get(i+1).To) || (tour.get(i).To == tour.get(i+1).To) || (tour.get(i).To == tour.get(i+1).From))
			{
				continue;
			}
			else
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static void timer() 
	{
		if (phase == 0) 
		{
			startTime = System.currentTimeMillis();
			phase = 1;
		} 
		else 
		{
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			System.out.println("Time: " + elapsedTime + " msec.");
			memory();
			phase = 0;
		}
	}

	public static void memory() 
	{
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		System.out.println("Memory: " + memUsed / 1000000 + " MB / " + memAvailable / 1000000 + " MB.");
	}
}
