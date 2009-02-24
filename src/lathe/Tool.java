
package lathe;

import java.util.*;

import simplerjogl.Vertex;

/**
 * Tool
 * 
 * An object to represent the actual tool that is being used to cut the
 * work piece.
 * 
 * 
 * 
 * @author Seth Battis
 */
public class Tool extends VertexShape
{
	public Tool ()
	{
		super ();

		// these are totally arbitrary values!
		surface.add (new Vertex (1, 2));
		surface.add (new Vertex (2, 1));
		surface.add (new Vertex (3, 2));
	}

	/*
	 * TODO Things to add
	 * 
	 * Need to be able to create vertices at the intersection of the tool
	 * edges and the work piece edges
	 * 
	 * Need to be able to add vertices to the workpiece to represent cuts
	 * (this may require updating the workpiece as well)
	 */

	public void move (double dx, double dy, WorkPiece work) // Chris Becker
	{
		for (Vertex v : surface)
		{
			v.setX (v.getX () + dx);
			v.setY (v.getY () + dy);
		}

		for (int j = 0; j < surface.size (); j++ ) // Chris Becker
		{
			Vertex v = surface.get (j);
			if (work.contains (v))
			{
 				Vertex workLeft = work.leftOf (v.getX());
 				Vertex workRight = work.rightOf (v.getX());
 				
 				Vertex toolLeft = this.leftOf (v.getX());
 				Vertex toolRight = this.rightOf (v.getX());
				
				Vertex left = Intersection (workLeft, workRight, toolLeft, v);
				Vertex right = null;
				
				do {
					right = Intersection (workLeft, workRight, v, toolRight);
					if (right == null)
					{
						workLeft = workRight;
						workRight = work.rightOf (workRight.getX());
					}
				} while (right == null);
				
				// index of right workpiece vertex
				int i = work.surface.indexOf (workRight);

				// inserting new vertices _before_ right workpiece vertex
				work.surface.add (i, left);
				work.surface.add (i, new Vertex (v));
				work.surface.add (i, right);

				/*
				 * check for workpiece vertices that are within the tool
				 * and need to be removed
				 */
				for (int k = 0; k < work.surface.size (); k++ )
				{
					Vertex v1 = work.surface.get (k);
					if (this.contains (v1))
					{
						work.surface.remove (v1);
						k-- ;
					}
				}
			}
		}
	}
	
	public Vertex Intersection(Vertex a, Vertex b, Vertex p, Vertex q)
	{
		double m1 = (a.getY () - b.getY ()) / (a.getX () - b.getX ());
		double b1 = a.getY () - (m1 * a.getX ());
		
		double m2 = (p.getY () - q.getY ()) / (p.getX () - q.getX ());
		double b2 = p.getY () - (m2 * p.getY ());
				
		double x = (b2 - b1) / (m1 - m2);
		double y = (m1 * x) + b1;
				
		/* TODO what if intersection is outside the volume of the tool and/or work piece? */
		if(m1==m2)
		{
			return null;
		}
		else
		{
			return new Vertex (x, y);
		}
	}

	public boolean contains (Vertex v)
	{
		Vertex at = this.atX (v.getX ());

		if (at != null)
		{
			return (at.getY () < v.getY ());
		}
		else
		{
			Vertex left = this.leftOf (v.getX ());
			Vertex right = this.rightOf (v.getX ());

			if ( (left != null) && (right != null))
			{
				// formula for the ine at distance v.x
				double m = (left.getY () - right.getY ()) / (left.getX () - right.getX ());
				double b = left.getY () - (m * left.getX ());

				// true if the line is below v, false otherwise
				return ( (m * v.getX ()) + b) < v.getY ();
			}
			else
			{
				return false;
			}
		}
	}
}
