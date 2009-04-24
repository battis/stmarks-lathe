
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
		add (new Vertex (1, 2));
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

		cut (work);
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

	public void cut (WorkPiece work)
	{

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
				 * 
				 * It is possible that our first intersection we get is
				 * null (as the first line we look at in the work piece may
				 * be parallel to the face of the tool -- hence: no
				 * intercept). In this case, we check the next line outward
				 * from our point of intersection to see if _it_ intersects
				 * the face of the tool, until we find a line that provides
				 * a point of intersection.
				 * 
				 * TODO we're doing the same thing (more or less) four
				 * times. This should really be abstracted into a method
				 * that's called four times instead.
				 */
				Vertex left, right;
				if (workV == null)
				{
					Vertex wl = workLeft, wr = workRight;
					do
					{
						left = intersection (wl, wr, toolLeft, v);
						wr = wl;
						wl = work.leftOf (wr.getX ());
					} while (left == null);

					wl = workLeft;
					wr = workRight;
					do
					{
						right = intersection (wl, wr, v, toolRight);
						wl = wr;
						wr = work.rightOf (wl.getX ());
					} while (right == null);
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
	 * Computes the intersection of lines ab and pq
	 * 
	 * TODO what class should this be in?
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
		if (Math.abs (m1 - m2) < PRECISION)
		{
			System.out.println ("no point of intersection: " + a.toString2D () + " to " + b.toString2D () + " is parallel to " + p.toString2D () + " to " + q.toString2D ());
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
	 * 
	 * @param v
	 * @return
	 */
	public boolean contains (Vertex v)
	{
		double y = this.getY (v.getX ());
		if (y == -0.0)
		{
			return false;
		}
		return (v.getY () - y) > PRECISION;
	}
}