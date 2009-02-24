
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
				/*
				 * left vertex of workpiece
				 */
				Vertex w = work.leftOf (v.getX ());
				/*
				 * right vertex of workpiece
				 */
				Vertex z = work.rightOf (v.getX ());

				/*
				 * left vertex from tool tip
				 */
				Vertex u = this.leftOf (v.getX ());
				/*
				 * right vertex from tool tip
				 */
				Vertex q = this.rightOf (v.getX ());

				double m1 = (v.getY () - u.getY ()) / (v.getX () - u.getX ());
				double b1 = v.getY () - (m1 * v.getX ());

				// formula for workpiece surface line
				double m2 = (w.getY () - z.getY ()) / (w.getX () - z.getX ());
				double b2 = w.getY () - (m2 * w.getY ());

				// formula for the right side of tool line
				double m3 = (v.getY () - q.getY ()) / (v.getX () - q.getX ());
				double b3 = v.getY () - (m3 * v.getX ());

				/*
				 * left point of intersection (left tool side and workpiece
				 * lines)
				 * 
				 * x value for first point of intersection
				 */
				double x1 = (b2 - b1) / (m1 - m2);
				/*
				 * y value of first point of intersection
				 */
				double y1 = (m1 * x1) + b1;

				/*
				 * right point of intersection (right tool side and
				 * workpiece lines)
				 * 
				 * x value for second point of intersection
				 */
				double x2 = (b2 - b3) / (m3 - m2);
				/*
				 * y value for second point of intersection
				 */
				double y2 = (m3 * x2) + b3;

				// index of right workpiece vertex
				int i = work.surface.indexOf (z);

				// inserting new vertices _before_ right workpiece vertex
				work.surface.add (i, new Vertex (x2, y2));
				work.surface.add (i, new Vertex (v));
				work.surface.add (i, new Vertex (x1, y1));

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
