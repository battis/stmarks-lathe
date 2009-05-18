
package lathe;

import java.util.*;

import simplerjogl.*;

public class WorkPiece extends VertexShape
{
	/**
	 * Default constructor, creates an empty workpiece to be populated at a
	 * later time.
	 */
	public WorkPiece ()
	{
		super ();
	}

	/**
	 * pre: length and radius are non-negative values
	 * 
	 * @param length
	 * @param radius
	 */
	public WorkPiece (double length, double radius)
	{
		super ();
		this.add (new Vertex (0, 0));
		this.add (new Vertex (0, radius));
		this.add (new Vertex (length / 2, radius));
		this.add (new Vertex (length, radius));
		this.add (new Vertex (length, 0));
		this.simplify ();
	}

	/**
	 * pre: 0 <= position <= length
	 * 
	 * @param distance
	 *            measured from work piece's zero
	 * @return the radius at position, -1 if distance > length
	 * @deprecated use getY()
	 */
	public double radius (double distance)
	{
		return this.getY (distance);
	}

	/**
	 * TODO What happens if we have an overhang (fail the vertical line
	 * test) in our shape? This does not take that into account!
	 * 
	 * @param v
	 *            a point in coordinates relative to the work piece's zero
	 * @return true if v is within the volume o the workpiece, false
	 *         otherwise
	 */
	public boolean contains (Vertex v)
	{
		double y = this.getY (v.getX ());
		if (y == -0.0)
		{
			return false;
		}
		return RoundToPrecision (y - v.getY ()) > 0;
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
		Vertex max = get (0);
		for (Vertex v : this)
		{
			if (v.getY () > max.getY ())
			{
				max = v;
			}
		}
		return max.getY ();
	}
}