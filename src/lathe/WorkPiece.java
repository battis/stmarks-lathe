
package lathe;

import java.util.*;

import simplerjogl.*;

public class WorkPiece extends VertexShape implements Iterable<Vertex>
{
	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public WorkPiece (double length, double radius)
	{
		super ();
		surface.add (new Vertex (0, 0));
		surface.add (new Vertex (0, radius));
		surface.add (new Vertex (length, radius));
		surface.add (new Vertex (length, 0));
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
				/*
				 * TODO What if there are two vertices at this
				 * X-coordinate? We should return the one with the greatest
				 * Y-value
				 */
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
		return radius (v.getX ()) > v.getY ();
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
}