
package lathe;

import java.math.*;
import java.util.*;

import simplerjogl.*;

public class VertexShape extends ArrayList<Vertex>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final int PRECISION = 7; // power of 10

	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public VertexShape ()
	{
		super ();
	}

	/**
	 * pre: 0 <= distance <= length
	 * 
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
	 * 
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
	 * Find the vertex (if any) immediately prior to the vertex v in the
	 * shape (if v is in the shape)
	 * 
	 * @param v
	 *            - the vertex to find in the VertexShape
	 * @return the vertex prior to v in this VertexShape if v is in the
	 *         shape and there is a vertex prior to it, null otherwise
	 */
	public Vertex prev (Vertex v)
	{
		int i = this.indexOf (v);
		if (i <= 0)
		{
			return null;
		}
		return this.get (i - 1);
	}

	/**
	 * Find the vertex (if any) immediately after to the vertex v in the
	 * shape (if v is in the shape)
	 * 
	 * @param v
	 *            - the vertex to find in the VertexShape
	 * @return the vertex after to v in this VertexShape if v is in the
	 *         shape and there is a vertex after to it, null otherwise
	 */
	public Vertex next (Vertex v)
	{
		int i = this.lastIndexOf (v);
		if ( (i < 0) || (i == this.size () - 1))
		{
			return null;
		}
		return this.get (i + 1);
	}

	public void simplify ()
	{
		Vertex a = null, b = null;
		for (int i = 0; i < this.size (); i++ )
		{
			Vertex c = this.get (i);
			boolean bRemoved = false;
			if ( (a != null) && (b != null))
			{
				double slope = RoundToPrecision ( (a.getY () - c.getY ()) / (a.getX () - c.getX ()));
				double intercept = RoundToPrecision (a.getY () - (slope * a.getX ()));

				// calculate value of y = slope * Bx + intercept
				double y = RoundToPrecision (slope * b.getX () + intercept);
				// if y == By, then B is on the line AC,
				if (RoundToPrecision (y - b.getY ()) == 0)
				{
					// then delete B from the list

					this.remove (b);
					bRemoved = true;
					i-- ;
				}
			}
			if (!bRemoved)
			{
				a = b;
			}
			b = c;
		}
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

	public double RoundToPrecision (double n)
	{
		BigDecimal big = new BigDecimal (n);
		big = big.setScale (PRECISION, RoundingMode.HALF_UP);
		return big.doubleValue ();
	}

	/**
	 * @return length of shape (assumes that shape starts at x = 0)
	 */
	public double length ()
	{
		return this.get (this.size () - 1).getX ();
	}
	
	public boolean add (Vertex v)
	{
		Vertex w = new Vertex (RoundToPrecision (v.getX()), RoundToPrecision (v.getY()));
		return super.add (w);
	}
}