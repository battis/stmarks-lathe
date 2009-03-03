
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

		// TODO these are totally arbitrary values!
		surface.add (new Vertex (1.5, 2));
		surface.add (new Vertex (2, 1));
		surface.add (new Vertex (2.5, 2));
	}

	/*
	 * TODO Things to add
	 * 
	 * Need to be able to create vertices at the intersection of the tool
	 * edges and the work piece edges
	 * 
	 * Need to be able to add vertices to the work piece to represent cuts
	 * (this may require updating the work piece as well)
	 */

	public void move (double dx, double dy, WorkPiece work) // Chris Becker
	{
		/* move every vertex of the tool surface the specified distance */
		for (Vertex v : surface)
		{
			v.setX (v.getX () + dx);
			v.setY (v.getY () + dy);
		}

		/*
		 * check to see if any tool vertices are now within the volume of
		 * the work piece. If so, we need to make a cut in the work piece
		 * to reflect this, shaping the work piece around the surface of
		 * the tool.
		 */
		for (int j = 0; j < surface.size (); j++ ) // Chris Becker
		{
			Vertex v = surface.get (j);
			if (work.contains (v))
			{
				/*
				 * the vertices of the work piece surface to the left and
				 * right of the tool vertex contained in the volume of the
				 * work piece
				 * 
				 * TODO what happens if there is a vertex in the work piece
				 * surface at exactly the same x-coordinate as v?
				 */
				Vertex workLeft = work.leftOf (v.getX ());
				Vertex workRight = work.rightOf (v.getX ());
				Vertex workV = work.atX (v.getX ());

				/*
				 * the vertices of the tool surface immediately to the left
				 * and right of the tool vertex contained in the volume of
				 * the work piece
				 * 
				 * TODO (down the road) what happens if there is more than
				 * a single tool vertex enclosed within the volume of the
				 * work piece?
				 */
				Vertex toolLeft = this.leftOf (v.getX ());
				Vertex toolRight = this.rightOf (v.getX ());

				/*
				 * calculate the intersection points of the three lines
				 * involved: the line of the work piece surface, the line
				 * from the left vertex of the tool surface to the enclosed
				 * vertex, and the line from the right vertex of the tool
				 * surface to the enclosed vertex
				 */
				Vertex left, right;
				if (workV == null)
				{
					left = intersection (workLeft, workRight, toolLeft, v);
					right = intersection (workLeft, workRight, v, toolRight);
				}
				else
				{
					left = intersection (workLeft, workV, toolLeft, v);
					right = intersection (workV, workRight, v, toolRight);
				}

				// index of right work piece vertex
				int i = work.surface.indexOf (workRight);

				// inserting new vertices _before_ right work piece vertex
				work.surface.add (i, left);
				work.surface.add (i, new Vertex (v));
				work.surface.add (i, right);

				cullVertices (work);
			}
		}
	}

	/**
	 * TODO this should maybe in the WorkPiece object
	 * 
	 * @param work
	 */
	protected void cullVertices (WorkPiece work)
	{
		/*
		 * check for work piece vertices that are within the tool and need
		 * to be removed
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

	public Vertex intersection (Vertex a, Vertex b, Vertex p, Vertex q)
	{
		double m1 = (a.getY () - b.getY ()) / (a.getX () - b.getX ());
		double b1 = a.getY () - (m1 * a.getX ());

		double m2 = (p.getY () - q.getY ()) / (p.getX () - q.getX ());
		double b2 = p.getY () - (m2 * p.getY ());

		double x = (b2 - b1) / (m1 - m2);
		double y = (m1 * x) + b1;

		/*
		 * TODO what if intersection is outside the volume of the tool
		 * and/or work piece?
		 */
		if (m1 == m2)
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
				// formula for the line at distance v.x
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
