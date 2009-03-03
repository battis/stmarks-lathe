
package lathe;

import java.util.*;

import simplerjogl.*;

public class VertexShape extends ArrayList<Vertex>
{
	//protected ArrayList<Vertex> surface;

	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public VertexShape ()
	{
		super();
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
	 * TODO There could be more than one vertex at a particular X-coordinate!
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
	
	/**
	 * @return length of work piece
	 */
	public double length ()
	{
		return this.get (this.size () - 1).getX ();
	}
}