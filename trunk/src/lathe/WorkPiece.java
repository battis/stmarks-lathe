
package lathe;

import java.util.*;

import simplerjogl.*;

public class WorkPiece implements Iterable<Vertex>
{
	protected ArrayList<Vertex> surface, iterableSurface;

	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public WorkPiece (double length, double radius)
	{
		surface = new ArrayList<Vertex> ();
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
		return surface.get (surface.size () - 1).getX ();
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

	/**
	 * @return the largest radius found on the surface
	 * 
	 *         TODO This could be improved by caching the most recently
	 *         found maximum radius and only re-searching if the surface
	 *         has changed -- possibly only if the surface has changed near
	 *         the maximum
	 */
	public double maxRadius ()
	{
		Vertex max = surface.get (0);
		for (Vertex v : surface)
		{
			if (v.getY () > max.getY ())
			{
				max = v;
			}
		}
		return max.getY ();
	}

	public Iterator<Vertex> iterator ()
	{
		final Iterator<Vertex> surfaceIterator = surface.iterator ();
		return new Iterator<Vertex> ()
		{

			public boolean hasNext ()
			{
				return surfaceIterator.hasNext ();
			}

			public Vertex next ()
			{
				return new Vertex (surfaceIterator.next ());
			}

			public void remove ()
			{}

		};
	}
}