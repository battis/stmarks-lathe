
package visualizer;

import javax.media.opengl.*;

import lathe.*;

import simplerjogl.*;

public class VisualWorkPiece extends Model
{
	protected WorkPiece work;
	protected Material material;
	protected double slices;

	public VisualWorkPiece (GL gl, double length, double radius, int slices, Material material)
	{
		super (gl);

		work = new WorkPiece (length, radius);
		this.slices = slices;
		this.material = new Material (material);
	}

	public void draw ()
	{
		/* the size of the arc is determined by the number of slices */
		double angle = 2 * Math.PI / slices;

		gl.glPushMatrix ();
		{
			material.use ();
			gl.glBegin (GL.GL_TRIANGLES);
			{
				Vertex p1 = null;
				for (Vertex p2 : work)
				{
					if (p1 != null)
					{
						for (double i = slices; i >= 1; i--)
						{
							Vertex v1, v2, v3, v4;
							v1 = new Vertex (p1.getX (), Math.cos (i * angle) * p1.getY (), Math.sin (i * angle) * p1.getY ());
							v2 = new Vertex (p1.getX (), Math.cos ((i - 1) * angle) * p1.getY (), Math.sin ((i - 1) * angle) * p1.getY ());
							v3 = new Vertex (p2.getX (), Math.cos (i * angle) * p2.getY (), Math.sin (i * angle) * p2.getY ());
							v4 = new Vertex (p2.getX (), Math.cos ((i - 1) * angle) * p2.getY (), Math.sin ((i - 1) * angle) * p2.getY ());
							
							Vertex n1, n2;
							n1 = Vertex.normalVector (v1, v2, v3);
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
			gl.glEnd();
		}
		gl.glPopMatrix();
	}
}