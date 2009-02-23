package lathe;

import java.util.*;

import simplerjogl.Vertex;

/**
 * Tool
 * 
 * An object to represent the actual tool that is being used to cut the work piece.
 * 
 * 
 * 
 * @author Seth Battis
 */
public class Tool extends VertexShape
{
	public Tool()
	{
		super();
		
		// these are totally arbitrary values!
		surface.add (new Vertex (1, 2));
		surface.add (new Vertex (2, 1));
		surface.add (new Vertex (3, 2));
	}
	
	/* TODO Things to add
	 * 
	 * Need to be able to create vertices at the intersection of the tool edges and the work piece edges
	 * 
	 * Need to be able to add vertices to the workpiece to represent cuts (this may require updating the workpiece as well)
	 */
	
	public void move(double dx, double dy, WorkPiece work) //Chris Becker
	{
		for (Vertex v: surface)
		{
			v.setX(v.getX()+dx);
			v.setY(v.getY()+dy);
		}
		
		for (int j = 0; j < surface.size(); j++) // Chris Becker
		{
			Vertex v = surface.get(j);
			if (work.contains(v))
			{
				Vertex w= work.leftOf(v.getX());
				Vertex z= work.rightOf(v.getX());
				Vertex u= this.leftOf(v.getX());
				double m1= (v.getY()-u.getY())/(v.getX()-u.getX());// slope of left side of tool
				
				double m2= (w.getY()-z.getY())/(w.getX()-z.getX());// slope of Work Piece
				
				Vertex q= this.rightOf(v.getX());
				double m3= (v.getY()-q.getY())/(v.getX()-q.getX());// slope of right side of tool
				
				double b1= v.getY() - (m1 * v.getX());// b value for left side of tool
				
				double b2= w.getY() - (m2 * w.getY());// b value for work piece
				
				double x1= (b2 - b1)/(m1 - m2);// x value for first point of intersection
				
				double y1= (m1 * x1) + b1;// y value of first point of intersection
				
				
				double b3= v.getY() - (m3 * v.getX());// b value for right side of tool
				
				double x2= (b2 - b3)/(m3 - m2);// x value for second point of intersection
				
				double y2= (m3 * x2) + b3;// y value for second poi9nt of intersection
				
				
				int i= surface.indexOf(v);
				
				surface.add(i, new Vertex(x2,y2));
				surface.add(i, v);
				surface.add(i, new Vertex(x1,y1));
			}
		}
	}
}

