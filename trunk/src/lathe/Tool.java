
package lathe;

import simplerjogl.Vertex;

/**
 * Tool An object to represent the actual tool that is being used to cut
 * the work piece.
 * 
 * @author Seth Battis
 */
public class Tool extends VertexShape
{
	public Tool ()
	{
		super ();
		
		// tool shape approximation courtesy of Mr. Wells
		/*add (new Vertex (1.6, 3.2)); 
		add (new Vertex (1.8, 2.2)); 
		add (new Vertex (2, 2)); 
		add (new Vertex (2.0, 3));
		*/
		
		// tool shape approximation courtesy of Inyoung Back
		add (new Vertex (1.2, 2));
		add (new Vertex (2, 1));
		add (new Vertex (2.2, 2));
	}

	public void move (double dx, double dy, WorkPiece work)
	{
		/* move every vertex of the tool surface the specified distance */
		for (Vertex v : this)
		{
			v.setX (v.getX () + dx);
			v.setY (v.getY () + dy);
			if (v.getY() < 0)
			{
				System.err.println ("Tool has cut entirely through the workpiece");
				System.exit (0);
			}
		}
		cut (work);
	}

	/**
	 * Remove vertices from the work piece that are contained within the
	 * tool TODO this should maybe in the WorkPiece object
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
		s.simplify ();
	}

	public void cut (WorkPiece work)
	{
		/*
		 * check to see if any tool vertices are now within the volume of
		 * the work piece. If so, we need to make a cut in the work piece
		 * to reflect this, shaping the work piece around the surface of
		 * the tool.
		 */
		for (int j = 0; j < this.size (); j++ )
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
				
				Vertex toolLeft = this.prev (v);
				Vertex toolRight = this.next (v);
				if ((toolLeft == null) || (toolRight == null))
				{
					System.err.println ("Unsafe tool interaction with workpiece");
					System.exit (0);
				}

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
						/*
						 * we are defining two left-to-right lines (wl-wr
						 * and toolLeft-v, where wl and wr are adjacent
						 * vertices in the workpiece, v is the vertex of
						 * the tool that entered the workpiece and caused
						 * to make this cut and toolLeft is the vertex
						 * immediately to the left of v in the tool) and
						 * computing their intersection
						 */
						left = intersection (wl, wr, toolLeft, v);

						if (left == null)
						{
							/*
							 * sliding wr and wl over one vertex to the
							 * left (wl becomes wr, and wl is the next
							 * vertex to the left), in anticipation of no
							 * intersection between the two lines (i.e.
							 * left -- the left intersection -- is null)
							 */
							wr = wl;
							wl = work.leftOf (wr.getX ());
							if (wl == null)
							{
								System.err.println ("ran off the left end of the workpiece in search of a line that intersects the tool");
								System.exit (0);
							}
						}
					} while (left == null);

					wl = workLeft;
					wr = workRight;
					do
					{
						/* see above, same idea */
						right = intersection (wl, wr, v, toolRight);
						if (right == null)
						{
							wl = wr;
							wr = work.rightOf (wl.getX ());
							if (wr == null)
							{
								System.err.println ("ran off the right end of the workpiece in search of a line to intersect the tool");
								System.exit (0);
							}
						}
					} while (right == null);
				}
				else
				{
					Vertex wl = workLeft, wr = workV;
					do
					{
						/*
						 * we are defining two left-to-right lines (wl-wr
						 * and toolLeft-v, where wl and wr are adjacent
						 * vertices in the workpiece, v is the vertex of
						 * the tool that entered the workpiece and caused
						 * to make this cut and toolLeft is the vertex
						 * immediately to the left of v in the tool) and
						 * computing their intersection
						 */
						left = intersection (wl, wr, toolLeft, v);

						if (left == null)
						{
							/*
							 * sliding wr and wl over one vertex to the
							 * left (wl becomes wr, and wl is the next
							 * vertex to the left), in anticipation of no
							 * intersection between the two lines (i.e.
							 * left -- the left intersection -- is null)
							 */
							wr = wl;
							wl = work.leftOf (wr.getX ());
							if (wl == null)
							{
								System.err.println ("ran off the left end of the workpiece in search of a line that intersects the tool");
								System.exit (0);
							}
						}
					} while (left == null);

					wl = workV;
					wr = workRight;
					do
					{
						/* see above, same idea */
						right = intersection (wl, wr, v, toolRight);
						if (right == null)
						{
							wl = wr;
							wr = work.rightOf (wl.getX ());
							if (wr == null)
							{
								System.err.println ("ran off the right end of the workpiece in search of a line to intersect the tool");
								System.exit (0);
							}
						}
					} while (right == null);				}

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
		double m1 = RoundToPrecision ( (a.getY () - b.getY ()) / (a.getX () - b.getX ()));
		double b1 = RoundToPrecision (a.getY () - (m1 * a.getX ()));

		/* slope-intercept formula for line pq */
		double m2 = RoundToPrecision ( (p.getY () - q.getY ()) / (p.getX () - q.getX ()));
		double b2 = RoundToPrecision (p.getY () - (m2 * p.getX ()));
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
		if (RoundToPrecision (m1 - m2) == 0)
		{
			return null;
		}
		else
		{
			/* solve for (x, y) at intersection of pq and ab */
			double x = RoundToPrecision ( (b2 - b1) / (m1 - m2));
			double y = RoundToPrecision ( (m1 * x) + b1);
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
		return RoundToPrecision(v.getY () - y) == 0;
	}
}
