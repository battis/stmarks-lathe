
package lathe;

import java.util.*;

import simplerjogl.*;

public class VertexShape extends ArrayList<Vertex>
{
	protected final double PRECISION = 0.00000001;
	/**
	 * pre: length and radius are non-negative values
	 * @param length
	 * @param radius
	 */
	public VertexShape ()
	{
		super ();
	}
	/**
	 * pre: 0 <= distance <= length
	 * @param distance
	 * @return the rightmost vertex with an x-coordinate less than or equal
	 *         to distance, null if no such vertex exists
	 */
	public Vertex leftOf (double distance)
	{
		Vertex left = null;
		for (Vertex right : this)
		{
			if (right.getX () < distance)
			{
				left = right;
			}
			else
			{
				return left;
			}
		}
		return null;
	}
	/**
	 * pre: 0 <= distance <= length
	 * @param distance
	 * @return the leftmost vertex v such that v.x > distance, null if no
	 *         such vertex exists
	 */
	public Vertex rightOf (double distance)
	{
		for (Vertex right : this)
		{
			if (right.getX () > distance)
			{
				return right;
			}
		}
		return null;
	}
	/**
	 * TODO There could be more than one vertex at a particular
	 * X-coordinate!
	 * 
	 * @param distance
	 * @return the first vertex with X-coordinate at distance
	 */
	public Vertex atX (double distance)
	{
		for (Vertex v : this)
		{
			if (v.getX () == distance)
			{
				return v;
			}
		}
		return null;
	}
	public double getY (double x)
	{
		/*
		 * make sure that the x is actually within the boundaries of the
		 * shape
		 */
		if (x < this.get (0).getX () || x > this.get (this.size () - 1).getX ())
		{
			return -0.0;
		}
		else
		{
			/*
			 * check to see if there is a vertex at the point that we're
			 * looking at
			 */
			Vertex v = this.atX (x);
			if (v != null)
			{
				return v.getY ();
			}
			else
			{
				/*
				 * draw a line between the vertex to the left of the given
				 * x-coordinate and the vertex to the right and calculate
				 * the y-coordinate at the given x-coordinate on that line
				 */
				Vertex l = this.leftOf (x), r = this.rightOf (x);
				double m = (l.getY () - r.getY ()) / (l.getX () - r.getX ());
				double b = l.getY () - (m * l.getX ());
				return (m * x) + b;
			}
		}
	}
	/**
	 * @return length of shape (assumes that shape starts at x = 0)
	 */
	public double length ()
	{
		return this.get (this.size () - 1).getX ();
	}
}