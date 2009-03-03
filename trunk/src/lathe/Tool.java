
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
		add (new Vertex (1.5, 2));
		add (new Vertex (2, 1));
		add (new Vertex (2.5, 2));
	}

	public void move (double dx, double dy, WorkPiece work) // Chris Becker
	{
		/* move every vertex of the tool surface the specified distance */
		for (Vertex v : this)
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
		for (int j = 0; j < this.size (); j++ ) // Chris Becker
		{
			Vertex v = this.get (j);
			if (work.contains (v))
			{
				/*
				 * the vertices of the work piece surface to the left and
				 * right of the tool vertex contained in the volume of the
				 * work piece. workV is a vertex immediately above the
				 * contained vertex (if such a vertex exists).
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
					Vertex wl = workLeft, wv = workV;
					do
					{
						left = intersection (wl, wv, toolLeft, v);
						wv = wl;
						wl = work.leftOf (wv.getX ());
					} while (left == null);

					Vertex wr = workRight;
					wv = workV;
					do
					{
						right = intersection (wv, wr, v, toolRight);
						wv = wr;
						wr = work.rightOf (wv.getX ());
					} while (right == null);
				}

				/* index of right work piece vertex */
				int i = work.indexOf (workRight);

				/*
				 * inserting new vertices _before_ right work piece vertex.
				 * we make our insertions in reverse order because we as we
				 * insert at index i, everything shifts to the right, so we
				 * need to start with our right-most new vertex and insert
				 * from right to left in order for our vertices to end up
				 * in left to right order
				 */
				work.add (i, right);
				work.add (i, new Vertex (v));
				work.add (i, left);

				cullVertices (work);
			}
		}
	}

	/**
	 * Remove vertices from the work piece that are contained within the
	 * tool
	 * 
	 * TODO this should maybe in the WorkPiece object
	 * 
	 * @param work
	 */
	protected void cullVertices (VertexShape s)
	{
		/*
		 * check for work piece vertices that are within the tool and need
		 * to be removed
		 */
		for (int i = 0; i < s.size (); i++ )
		{
			Vertex v = s.get (i);
			if (this.contains (v))
			{
				if (s.remove (v))
				{
					i-- ;
				}
			}
		}
	}

	/**
	 * Computes the intersection of lines ab and pq
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param q
	 * @return
	 */
	public Vertex intersection (Vertex a, Vertex b, Vertex p, Vertex q)
	{
		/* can't compute an intersect with less than four vertices! */
		if ( (a == null) || (b == null) || (p == null) || (q == null))
		{
			return null;
		}

		/* slope-intercept formula for line ab */
		double m1 = (a.getY () - b.getY ()) / (a.getX () - b.getX ());
		double b1 = a.getY () - (m1 * a.getX ());

		/* slope-intercept formula for line pq */
		double m2 = (p.getY () - q.getY ()) / (p.getX () - q.getX ());
		double b2 = p.getY () - (m2 * p.getX ());

		/*
		 * If the lines are parallel (slopes are equal), there is no
		 * intersection so we return null -- otherwise we compute the
		 * intersection and return that vertex
		 * 
		 * To compare doubles, we have to allow for the fact that they are
		 * imprecise values -- if their difference is really close to zero,
		 * we'll consider them to actually be equal
		 * 
		 * TODO what if intersection is outside the volume of the tool
		 * and/or work piece?
		 */
		if (Math.abs (m1 - m2) < 0.0000001)
		{
			System.out.println ("no point of intersection: " + a + " to " + b + " is parallel to " + p + " to " + q);
			return null;
		}
		else
		{
			/* solve for (x, y) at intersection of pq and ab */
			double x = (b2 - b1) / (m1 - m2);
			double y = (m1 * x) + b1;
			return new Vertex (x, y);
		}
	}

	/**
	 * The vertex v is contained in the volume of the tool
	 * @param v
	 * @return
	 */
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
