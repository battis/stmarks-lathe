
package lathe;

import java.util.*;

import simplerjogl.*;

public class WorkPiece
{
	protected ArrayList<Vertex> surface;

	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public WorkPiece (double length, double radius)
	{
		surface = new ArrayList<Vertex> ();
		surface.add (new Vertex (0, radius));
		surface.add (new Vertex (length, radius));
	}

	/**
	 * pre: 0 <= position <= length
	 * 
	 * @param distance
	 *            measured from work piece's zero
	 * @return the radius at position, -1 if distance > length
	 */
	public double radius (double distance)
	{
		Vertex left = null;
		for (Vertex right : surface)
		{
			if (right.getX () == distance)
			{
				return right.getY ();
			}
			else if (right.getX () < distance)
			{
				left = right;
			}
			else
			{
				double m = (left.getY () - right.getY ()) / (left.getX () - right.getX ());
				double b = left.getY () - (m * left.getX ());
				return m * distance + b;
			}
		}
		return -1;
	}

	/**
	 * pre: 0 <= distance <= length
	 * 
	 * @param distance
	 * @return the rightmost vertex with an x-coordinate less than or equal
	 *         to distance, null if no such vertex exists
	 */
	protected Vertex leftOf (double distance)
	{
		Vertex left = null;
		for (Vertex right : surface)
		{
			if (right.getX () <= distance)
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
	protected Vertex rightOf (double distance)
	{
		for (Vertex right : surface)
		{
			if (right.getX () > distance)
			{
				return right;
			}
		}
		return null;
	}

	/**
	 * @return length of work piece
	 */
	public double length ()
	{
		return surface.get (surface.size () - 1).getY ();
	}

	/**
	 * pre: 0 <= distance < length; 0 <= depth < radius
	 * 
	 * @param distance
	 *            from zero of work piece to face plate
	 * @param depth
	 *            of cut
	 * @param tool
	 *            being used to make the cut
	 */
	public void cutFacePlate (double distance, double depth, Tool tool)
	{}

	/**
	 * pre: 0 <= distance < length; 0 <= depth < radius
	 * 
	 * @param distance
	 *            from zero of work piece to cylinder turn
	 * @param depth
	 *            of cut
	 * @param tool
	 *            being used to make the cut
	 */
	public void turnCylinder (double distance, double depth, Tool tool)
	{

	}

	/**
	 * @param v
	 *            a point in coordinates relative to the work piece's zero
	 * @return true if v is within the volume o the workpiece, false
	 *         otherwise
	 */
	public boolean contains (Vertex v)
	{
		if ( (v.getX () < 0) || (v.getX () > length ()))
		{
			return false;
		}
		return radius (v.getX ()) >= v.getY ();
	}
}