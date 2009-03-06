
package visualizer;

import javax.media.opengl.*;

import lathe.*;

import simplerjogl.*;

public class VisualWorkPiece extends Model
{
	public WorkPiece work;
	protected Material material;
	protected double slices;

	public VisualWorkPiece (GL gl, double length, double radius, int slices, Material material)
	{
		super (gl);

		work = new WorkPiece (length, radius);
		this.slices = slices;
		this.material = new Material (material);
	}

	public VisualWorkPiece (GL gl, WorkPiece w, int slices, Material material)
	{
		super (gl);
		work = w;
		this.slices = slices;
		this.material = new Material (material);
	}
	
	public void draw ()
	{
		/* the size of the arc is determined by the number of slices */
		double angle = 2 * Math.PI / slices;

		/*
		 * we'll draw the workpiece as a series of rings, starting at the
		 * origin and working outward (to the left). Each ring is the space
		 * between a pair of adjacent vertices (p1 and p2) in the work
		 * piece's surface. We'll draw each ring as a series of rectangular
		 * tiles, with each tile's being drawn as a pair of triangles
		 * incorporating all four corners of the rectangle (v1, v2, v3,
		 * v4).
		 */
		material.use ();
		gl.glBegin (GL.GL_TRIANGLES);
		{
			/* working from the origin to the left */
			Vertex p1 = null;
			for (Vertex p2 : work)
			{
				if (p1 != null)
				{
					/*
					 * count off each rectangle in the ring (for a total of
					 * slices rectangles)
					 */
					for (double i = slices; i >= 1; i-- )
					{
						/* compute the corners of the rectangle */
						Vertex v1, v2, v3, v4;
						v1 = new Vertex (p1.getX (), Math.cos (i * angle) * p1.getY (), Math.sin (i * angle) * p1.getY ());
						v2 = new Vertex (p1.getX (), Math.cos ( (i - 1) * angle) * p1.getY (), Math.sin ( (i - 1) * angle) * p1.getY ());
						v3 = new Vertex (p2.getX (), Math.cos (i * angle) * p2.getY (), Math.sin (i * angle) * p2.getY ());
						v4 = new Vertex (p2.getX (), Math.cos ( (i - 1) * angle) * p2.getY (), Math.sin ( (i - 1) * angle) * p2.getY ());

						/*
						 * compute the normal vectors -- the vectors that
						 * are perpendicular to the plane -- for each
						 * triangle. the normal vectors are used for
						 * computing lighting and shadows
						 */
						Vertex n1, n2;
						n1 = Vertex.normalVector (v1, v2, v4);
						n2 = Vertex.normalVector (v4, v3, v1);

						gl.glNormal3dv (n1.getXYZ (), 0);
						gl.glVertex3dv (v1.getXYZ (), 0);
						gl.glVertex3dv (v2.getXYZ (), 0);
						gl.glVertex3dv (v4.getXYZ (), 0);

						gl.glNormal3dv (n2.getXYZ (), 0);
						gl.glVertex3dv (v4.getXYZ (), 0);
						gl.glVertex3dv (v3.getXYZ (), 0);
						gl.glVertex3dv (v1.getXYZ (), 0);
					}
				}
				p1 = p2;
			}
		}
		gl.glEnd ();
	}
}