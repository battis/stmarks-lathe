package lathe;

import java.util.*;

import simplerjogl.Vertex;

/**
 * Tool
 * 
 * An object to represent the actual tool that is being used to cut the work piece.
 * 
 * 
 * 
 * @author Seth Battis
 */
public class Tool implements Iterable<Vertex>
{
	protected ArrayList<Vertex> surface;

	public Tool()
	{
		surface = new ArrayList<Vertex>();
		
		// these are totally arbitrary values!
		surface.add (new Vertex (1, 2));
		surface.add (new Vertex (1.3, 1.4));
		surface.add (new Vertex (1.71, 3.14159));
	}
	
	/* TODO Things to add
	 * 
	 * Need to be able to create vertices at the intersection of the tool edges and the work piece edges
	 * 
	 * Need to be able to add vertices to the workpiece to represent cuts (this may require updating the workpiece as well)
	 */
	
	public void move(double dx, double dy, WorkPiece work) //Chris Becker
	{
		for (Vertex v: surface)
		{
			v.setX(v.getX()+dx);
			v.setY(v.getY()+dy);
		}
		
		for (Vertex v : surface)
		{
			if (work.contains(v))
			{
				// we're cutting into the workpiece with this vertex
			}
		}
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

